package com.esite.framework.organize.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esite.framework.organize.entity.OrganizeEntity;
import com.esite.framework.organize.entity.OrganizeViewEntity;

public interface OrganizeService {

	public List<OrganizeViewEntity> loadTreeByParentId(String parentId);
	
	public List<OrganizeEntity> loadTreeByParentIdEntity(String parentId);
	
	public OrganizeViewEntity getOrganizeById(String id);
	public OrganizeEntity getOrganizeByIdForEntity(String id);
	
	public OrganizeViewEntity getRootOrganize();

	public List<OrganizeViewEntity> loadAllTreeById(String id);
	
	public OrganizeViewEntity getCode(String parentId,String name);

	public Page<OrganizeViewEntity> findOrganizeLikeName(Pageable instance, String name);

	public OrganizeViewEntity getOrganizeByName(String areaName);
	
	public void addNewOrganize(OrganizeEntity organize);

	public void remove(String id);

	public List<OrganizeViewEntity> getOrganizeByIdArray(String[] idArray);
	
	public List<OrganizeEntity> getOrganizeEntityByIdArray(String[] idArray);
	
	public Iterable<OrganizeEntity> getAllOrganizeEntity();
	

	/**
	 * 还原
	 * @param id
	 */
	public void restore(String id);

	public Iterable<OrganizeViewEntity> loadAllTreeByIdArray(String[] idArray);

	public void edit(OrganizeEntity organize);
}
