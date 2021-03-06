package com.sobey.cmop.mvc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmop.mvc.entity.NetworkElbItem;

/**
 * 网络资源 ELB 对象 NetworkElbItem 的Dao interface.
 * 
 * @author liukai
 * 
 */
public interface NetworkElbItemDao extends PagingAndSortingRepository<NetworkElbItem, Integer>,
		JpaSpecificationExecutor<NetworkElbItem> {

	List<NetworkElbItem> findByApplyUserId(Integer userId);

	List<NetworkElbItem> findByApplyId(Integer applyId);

}
