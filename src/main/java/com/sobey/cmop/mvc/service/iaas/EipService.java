package com.sobey.cmop.mvc.service.iaas;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmop.mvc.comm.BaseSevcie;
import com.sobey.cmop.mvc.constant.ResourcesConstant;
import com.sobey.cmop.mvc.dao.NetworkEipItemDao;
import com.sobey.cmop.mvc.dao.custom.BasicUnitDaoCustom;
import com.sobey.cmop.mvc.entity.Apply;
import com.sobey.cmop.mvc.entity.NetworkEipItem;
import com.sobey.cmop.mvc.entity.StorageItem;

/**
 * ES3相关的管理类.
 * 
 * @author liukai
 */
@Service
@Transactional(readOnly = true)
public class EipService extends BaseSevcie {

	private static Logger logger = LoggerFactory.getLogger(EipService.class);

	@Resource
	private NetworkEipItemDao networkEipItemDao;

	@Resource
	private BasicUnitDaoCustom basicUnitDao;

	public NetworkEipItem getNetworkEipItem(Integer id) {
		return networkEipItemDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public NetworkEipItem saveOrUpdate(NetworkEipItem networkEipItem) {
		return networkEipItemDao.save(networkEipItem);
	}

	@Transactional(readOnly = false)
	public void deleteNetworkEipItem(Integer id) {
		networkEipItemDao.delete(id);
	}

	/**
	 * 保存EIP的服务申请.(在服务申请时调用)
	 * 
	 * @param applyId
	 *            服务申请单ID
	 */
	@Transactional(readOnly = false)
	public void saveEIPToApply(Integer applyId, String[] spaces, String[] storageTypes, String[] computeIds) {

		Apply apply = comm.applyService.getApply(applyId);

		for (int i = 0; i < storageTypes.length; i++) {
			StorageItem storageItem = new StorageItem();

			String identifier = comm.applyService.generateIdentifier(ResourcesConstant.ServiceType.ES3.toInteger());
			storageItem.setIdentifier(identifier);
			storageItem.setSpace(Integer.parseInt(spaces[i]));// 存储空间大小
			storageItem.setApply(apply);
			storageItem.setStorageType(Integer.parseInt(storageTypes[i]));

			// this.saveOrUpdate(storageItem);

		}

	}

}