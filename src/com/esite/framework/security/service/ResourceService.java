package com.esite.framework.security.service;

import java.util.List;

import com.esite.framework.security.entity.Role;

public interface ResourceService {
	
	/**
	 * 将角色和可访问的资源进行关联
	 * @param roleList
	 */
	public void relevancyUrlWithRole(Role role);
	
	/**
	 * 获得系统中的公共资源
	 * @return
	 */
	public List<String> getGlobalResourceURL();

	/**
	 * 初始化公共资源
	 */
	public void initGlobalResource();
}
