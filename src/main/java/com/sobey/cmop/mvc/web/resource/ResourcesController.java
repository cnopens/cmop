package com.sobey.cmop.mvc.web.resource;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sobey.cmop.mvc.comm.BaseController;
import com.sobey.cmop.mvc.constant.ResourcesConstant;
import com.sobey.cmop.mvc.entity.Resources;
import com.sobey.framework.utils.Servlets;

/**
 * ResourcesController负责资源Resources的管理
 * 
 * @author liukai
 * 
 */
@Controller
@RequestMapping(value = "/resources")
public class ResourcesController extends BaseController {

	private static final String REDIRECT_SUCCESS_URL = "redirect:/resources/";

	/**
	 * 显示资源Resources的List
	 */
	@RequestMapping(value = { "list", "" })
	public String assigned(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize, Model model,
			ServletRequest request) {

		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, REQUEST_PREFIX);

		model.addAttribute("page", comm.resourcesService.getResourcesPageable(searchParams, pageNumber, pageSize));

		// 将搜索条件编码成字符串,分页的URL

		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, REQUEST_PREFIX));

		/**
		 * 返回不同服务类型的资源统计.页面参数为:服务类型名+COUNT. eg: PCSCOUNT,ECSCOUNT.
		 * 
		 * 服务类型注意是从ResourcesConstant.ServiceType中迭代出来的.<br>
		 * 所以枚举中修改了名称的话, 页面的参数名和链接后的查询参数也需要修改.
		 */
		for (Entry<Integer, String> entry : ResourcesConstant.ServiceType.map.entrySet()) {

			model.addAttribute(entry.getValue() + "COUNT", comm.resourcesService.getResourcesStatistics(entry.getKey()));

		}

		return "resource/resourceList";
	}

	/**
	 * 跳转到变更页面
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {

		// TODO 获得审批历史

		Resources resources = comm.resourcesService.getResources(id);

		model.addAttribute("resources", resources);

		model.addAttribute("change", comm.changeServcie.findChangeByResourcesId(id));

		String updateUrl = "";

		this.returnUrlAndModel(model, resources, updateUrl, "");

		return updateUrl;
	}

	/**
	 * 资源回收.
	 * 
	 * <pre>
	 * 1.根据ID查询该资源属于哪种类型(PCS,ECS,ES3...)并获得各个单元的对象.
	 * 2.查询该资源对象关联的所有资源(根据不同的资源关联的资源对象也不同),拼接成邮件,直接发送至redmine第一接收人处.
	 * 3.审批->工单处理完成后,再物理删除cmop本地的数据以及oneCMDB的数据(通过API实现).
	 * </pre>
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

		boolean result = comm.resourcesService.recycleResources(id);

		redirectAttributes.addFlashAttribute("message", result ? "资源回收中..." : "资源回收失败,请稍后重试");

		return REDIRECT_SUCCESS_URL;

	}

	/**
	 * 跳转到详情页面
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Integer id, Model model) {

		Resources resources = comm.resourcesService.getResources(id);

		model.addAttribute("resources", resources);

		model.addAttribute("change", comm.changeServcie.findChangeByResourcesId(id));

		String detailUrl = "";

		this.returnUrlAndModel(model, resources, "", detailUrl);

		return detailUrl;
	}

	/**
	 * 根据不同的服务类型返回不同的对象和页面.<br>
	 * updateUrl和detailUrl必须且只能同时声明一个.
	 * 
	 * @param model
	 * @param resources
	 * @param updateUrl
	 *            跳转到变更页面的URL
	 * @param detailUrl
	 *            跳转到变更详情的URL
	 */
	private void returnUrlAndModel(Model model, Resources resources, String updateUrl, String detailUrl) {

		// 资源单项服务的ID

		Integer serviceId = resources.getServiceId();

		// 服务类型

		Integer serviceType = resources.getServiceType();

		if (serviceType.equals(ResourcesConstant.ServiceType.PCS.toInteger()) || serviceType.equals(ResourcesConstant.ServiceType.ECS.toInteger())) {

			model.addAttribute("compute", comm.computeService.getComputeItem(serviceId));

			updateUrl = "resource/form/compute";
			detailUrl = "resource/detail/computeDetail";

		} else if (serviceType.equals(ResourcesConstant.ServiceType.ES3.toInteger())) {

			model.addAttribute("storage", comm.es3Service.getStorageItem(serviceId));

			updateUrl = "resource/form/storage";
			detailUrl = "resource/detail/storageDetail";

		} else if (serviceType.equals(ResourcesConstant.ServiceType.ELB.toInteger())) {

			model.addAttribute("elb", comm.elbService.getNetworkElbItem(serviceId));

			updateUrl = "resource/form/elb";
			detailUrl = "resource/detail/elbDetail";

		} else if (serviceType.equals(ResourcesConstant.ServiceType.EIP.toInteger())) {

			model.addAttribute("eip", comm.eipService.getNetworkEipItem(serviceId));

			updateUrl = "resource/form/eip";
			detailUrl = "resource/detail/eipDetail";

		} else if (serviceType.equals(ResourcesConstant.ServiceType.DNS.toInteger())) {

			model.addAttribute("dns", comm.dnsService.getNetworkDnsItem(serviceId));

			updateUrl = "resource/form/dns";
			detailUrl = "resource/detail/dnsDetail";

		} else {

			updateUrl = "resource/resourceList";
			detailUrl = "resource/resourceList";
		}

	}

}
