package com.esite.framework.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.security.dao.RequestResourceDAO;
import com.esite.framework.security.entity.RequestResource;
import com.esite.framework.security.entity.Role;
import com.esite.framework.security.service.ResourceService;

public class ResourceServiceImpl implements ResourceService {
	private Logger logger = Logger.getLogger(ResourceServiceImpl.class);
	
	@Autowired
	private RequestResourceDAO requestResourceDAO;

	private static final String GLOBAL_RESOURCE_KEY = "GAB.RESOURCE.CATALOG.GLOBAL_RESOURCE";
	private Cache globalResourceCache;
	public void setGlobalResourceCache(Cache globalResourceCache) {
		this.globalResourceCache = globalResourceCache;
	}
	
	@Override
	public void relevancyUrlWithRole(Role role) {
		role.setRequestUrlCollection(getUrlByRoleId(role.getId()));
	}
	
	private List<String> getUrlByRoleId(String roleId) {
		List<RequestResource> requestResourceList = requestResourceDAO.getResourceByRoleId(roleId);
		List<String> url = new ArrayList<String>(requestResourceList.size());
		for(RequestResource requestResource : requestResourceList) {
			url.add(requestResource.getUrl());
		}
		return url;
	}


	@Override
	public List<String> getGlobalResourceURL() {
		Element element = (Element)globalResourceCache.get(GLOBAL_RESOURCE_KEY);
		if(null == element) {
			initGlobalResource();
			element = (Element)globalResourceCache.get(GLOBAL_RESOURCE_KEY);
		}
		return (List<String>) element.getObjectValue();
	}

	@Override
	public void initGlobalResource() {
		globalResourceCache.removeAll();
		Element element = globalResourceCache.get(GLOBAL_RESOURCE_KEY);
		if(null == element) {
			List<RequestResource> globalResource = requestResourceDAO.getGlobalResource();
			List<String> globalResourceUrl = new ArrayList<String>(globalResource.size());
			for(RequestResource resource : globalResource) {
				globalResourceUrl.add(resource.getUrl());
			}
			globalResourceCache.put(new Element(GLOBAL_RESOURCE_KEY,globalResourceUrl));
			logger.debug("已初始化" + globalResourceUrl.size() + "个公共资源");
		}
	}
	
}
