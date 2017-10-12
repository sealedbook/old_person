package com.esite.framework.organize.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.framework.organize.entity.OrganizeViewEntity;

public interface OrganizeViewDao extends CrudRepository<OrganizeViewEntity, String> {
	
	@Query("from OrganizeViewEntity t where t.parent.id is null or t.parent.id=''")
	public List<OrganizeViewEntity> queryByParentIdIsNull();
	
	@Query("from OrganizeViewEntity t where t.parent.id=?1")
	public List<OrganizeViewEntity> queryByParentId(String parentId);
	
	@Query("from OrganizeViewEntity t where t.parent.id =?1 and t.name like ?2")
	public OrganizeViewEntity getCode(String parentId,String name);

	@Query("from OrganizeViewEntity t where t.isLeaf=1 AND t.name LIKE %?1% order by t.name")
	public Page<OrganizeViewEntity> findByNameLike(String name,Pageable instance);

	public OrganizeViewEntity queryByName(String name);
}
