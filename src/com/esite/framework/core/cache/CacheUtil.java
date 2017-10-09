package com.esite.framework.core.cache;

import org.apache.log4j.Logger;

public class CacheUtil {
	
	private static Logger logger = Logger.getLogger(CacheUtil.class);
	
	public static Object getObjectFromCache(CachePool cache, String key, Provider provider, int refreshPeriod){
		Object om = null;
		if (null != cache) {			
			om = cache.get(key, refreshPeriod);
			if (om == null) {

				try {
					om = provider.findObject(key);
					if (om != null) {
						cache.put(key, om);
					} else {
						cache.cancleUpdate(key);
					}

				} catch (Exception e) {
					logger.error("cache error," + key);
					logger.error(e.getMessage());
					cache.cancleUpdate(key);
				}

			}
		} else {
			return provider.findObject(key);
		}
		return om;
	}
	
	public static Object getObjectFromCache(CachePool cache, String key, Provider provider){
		return getObjectFromCache(cache, key, provider, 3600);
	}
	
	public static void put(CachePool cache, Object key, Object value){
		try {
			cache.put(key, value);
		} catch (Exception e) {
			logger.error("cache error," + key);
			logger.error(e.getMessage());
			cache.cancleUpdate(key);
		}
	}
	
}
