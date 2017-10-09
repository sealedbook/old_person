package com.esite.framework.organize.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.framework.organize.entity.OrganizeEntity;
import com.esite.framework.organize.entity.OrganizeViewEntity;

public interface OrganizeDao extends CrudRepository<OrganizeEntity, java.lang.String> {
	
	public OrganizeEntity queryByName(String name);
	
	@Query("from OrganizeEntity t where t.parentId is null or t.parentId=''")
	public List<OrganizeEntity> queryByParentIdIsNull();

	@Query("from OrganizeEntity t where t.parentId=?1")
	public List<OrganizeEntity> queryByParentId(String parentId);
}
