package com.sobey.cmop.mvc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmop.mvc.entity.Vlan;

/**
 * Vlan对象(对应OneCMDB中的Vlans) 的Dao interface.
 * 
 * @author liukai
 * 
 */
public interface VlanDao extends PagingAndSortingRepository<Vlan, Integer>, JpaSpecificationExecutor<Vlan> {

	List<Vlan> findByLocationId(Integer locationId);

	Vlan findByName(String name);

}
