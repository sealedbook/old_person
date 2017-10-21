package com.esite.ops.operator.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.esite.ops.operator.entity.AreaConfigEntity;

public interface AreaConfigDao extends CrudRepository<AreaConfigEntity, java.lang.String> {
	
	public AreaConfigEntity findByAreaIdAndOperatorIdNot(String areaId,String operatorId);
	
	@Modifying
	@Query("delete from AreaConfigEntity t where t.operatorId=?1")
	@Transactional
	public void removeConfigByOperatorId(String operatorId);
	
	@Query("select conf.areaId from AreaConfigEntity conf where conf.operatorId=?1")
	public List<String> findAreaIdByOperatorId(String operatorId);
	
}
