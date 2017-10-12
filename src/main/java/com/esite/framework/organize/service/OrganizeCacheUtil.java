package com.esite.framework.organize.service;

import java.util.HashMap;
import java.util.Map;

import com.esite.framework.organize.entity.OrganizeEntity;

public final class OrganizeCacheUtil {
	private static Map<String, OrganizeEntity> cache = new HashMap<String, OrganizeEntity>();
	private static OrganizeCacheUtil organizeCacheUtil = new OrganizeCacheUtil();
	private OrganizeCacheUtil() {
		
	}
	
	public static OrganizeCacheUtil getInstance() {
		return organizeCacheUtil;
	}
	
	public void setOrganize(String id, OrganizeEntity organizeEntity) {
		if(!cache.containsKey(id)) {
			cache.put(id, organizeEntity);
		}
	}
	
	public OrganizeEntity getOrganize(String id) {
		OrganizeEntity organize = cache.get(id);
		return organize;
	}
}
