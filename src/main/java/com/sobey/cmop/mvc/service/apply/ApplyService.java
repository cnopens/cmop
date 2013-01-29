package com.sobey.cmop.mvc.service.apply;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmop.mvc.comm.BaseSevcie;
import com.sobey.cmop.mvc.constant.ApplyConstant;
import com.sobey.cmop.mvc.dao.ApplyDao;
import com.sobey.cmop.mvc.entity.Apply;
import com.sobey.framework.utils.DynamicSpecifications;
import com.sobey.framework.utils.SearchFilter;

/**
 * 服务申请单相关的管理类.
 * 
 * @author liukai
 */
@Component
@Transactional(readOnly = true)
public class ApplyService extends BaseSevcie {

	private static Logger logger = LoggerFactory.getLogger(ApplyService.class);

	@Resource
	private ApplyDao applyDao;

	// -- Apply Manager --//

	/**
	 * 生成服务申请Apply的 Title.拼装格式为: 登录名+申请服务类型+申请时间<br>
	 * eg:
	 * <p>
	 * liukai-基础设施-20130122102155<br>
	 * admin-MDN-20130212102311
	 * </p>
	 * 
	 * @param serviceType
	 *            服务申请的服务类型字符串
	 * @return
	 */
	public String generateApplyTitle(String serviceType) {

		DateTime dateTime = new DateTime();

		return comm.accountService.getCurrentUser().getLoginName() + "-" + serviceType + "-" + dateTime.toString("yyyyMMddHHmmss");

	}

	public Apply getApply(Integer id) {
		return applyDao.findOne(id);
	}

	/**
	 * Apply的分页查询.
	 * 
	 * @param searchParams
	 *            页面传递过来的参数
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Apply> getApplyPageable(Map<String, Object> searchParams, int pageNumber, int pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		// User创建动态查询条件组合.

		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		Specification<Apply> spec = DynamicSpecifications.bySearchFilter(filters.values(), Apply.class);

		return applyDao.findAll(spec, pageRequest);
	}

	/**
	 * 新增,更新Apply
	 * 
	 * @param apply
	 * @return
	 */
	@Transactional(readOnly = true)
	public Apply saveOrUpateApply(Apply apply) {
		return applyDao.save(apply);
	}

	/**
	 * 删除服务申请Apply
	 * 
	 * @param id
	 */
	@Transactional(readOnly = true)
	public void deleteApply(Integer id) {
		applyDao.delete(id);
	}

	/**
	 * 获得登录用户所有的申请单 Apply.用于基础设施申请<br>
	 * 1.申请单必须是服务类型ServiceType为 1.基础设施 的Apply<br>
	 * 2.申请单状态为 0.已申请 和 3.已退回 ,以满足服务申请只有在"已申请"和"已退回"这两个状态下才能修改的业务要求.<br>
	 * 
	 * @return
	 */
	public List<Apply> getBaseStationApplyList() {

		Integer serviceType = ApplyConstant.ServiceType.基础设施.toInteger();

		List<Integer> status = new ArrayList<Integer>();
		status.add(ApplyConstant.ApplyStatus.已申请.toInteger());
		status.add(ApplyConstant.ApplyStatus.已退回.toInteger());

		return applyDao.findByUserIdAndServiceTypeAndStatusIn(getCurrentUserId(), serviceType, status);
	}

}
