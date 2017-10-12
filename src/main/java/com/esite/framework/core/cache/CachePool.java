package com.esite.framework.core.cache;


import org.apache.log4j.Logger;

import com.esite.framework.core.exception.CacheException;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;


public class CachePool {

	
	private static Logger logger = Logger.getLogger(CachePool.class);
	
	private GeneralCacheAdministrator cache = new GeneralCacheAdministrator();

	/**
	 * 缓存时间
	 */
	private final int refreshPeriod;
	/*
	 * 
	 */
	private final String regionName;
	
	private String toString(Object key) {
		return String.valueOf(key) + '.' + regionName;
	}

	public CachePool(int refreshPeriod, String region) {
		this.refreshPeriod = refreshPeriod;
		this.regionName = region;
	}

	/**
	 * 
	 * 功能描述:设置缓存容量
	 * @param cacheCapacity
	 */
	public void setCacheCapacity(int cacheCapacity) {
		cache.setCacheCapacity(cacheCapacity);
	}
	
	/**
	 * 
	 * 功能描述:取消更新,更新缓存失败时由更新缓存的线程调用
	 * @param key
	 */
	public void cancleUpdate(Object key){
		cache.cancelUpdate( toString(key) );
	}

	/**
	 * 
	 * 功能描述:根据key查询缓存数据,过期时间采用默认值时间
	 * 如不存在缓存数据,返回null
	 * 如果存在,则返回缓存数据
	 * 如果存在但是缓存过期,首次查询线程返回null,查询线程负责更新缓存;其他线程根据配置决定返回历史值或者被阻塞
	 * @param key
	 * @return
	 * @throws CacheException
	 */
	public Object get(Object key) throws CacheException {
		try {
			return cache.getFromCache( toString(key), refreshPeriod );
		}
		catch (NeedsRefreshException e) {
			logger.info(key+",缓存需要更新");
			return null;
		}
	}
	
	/**
	 * 功能描述:根据key查询缓存数据
	 * 如不存在缓存数据,返回null
	 * 如果存在,则返回缓存数据
	 * 如果存在但是缓存过期,首次查询线程返回null,查询线程负责更新缓存;其他线程根据配置决定返回历史值或者被阻塞
	 * @param key 缓存key
	 * @param refreshPeriod 过期时间
	 * @return
	 * @throws CacheException
	 */
	public Object get(Object key, int refreshPeriod) throws CacheException {
		try {
			return cache.getFromCache( toString(key), refreshPeriod );
		}
		catch (NeedsRefreshException e) {
			logger.info(key+",缓存需要更新");
			return null;
		}
	}

	/*
	 * 同get(java.lang.Object)
	 * (non-Javadoc)
	 * @see #get(java.lang.Object)
	 */
	public Object read(Object key) throws CacheException {
		return get(key);
	}
	
	/*
	 * 同get(java.lang.Object,int)
	 * (non-Javadoc)
	 * @see #get(java.lang.Object,int)
	 */
	public Object read(Object key,int refreshPeriod) throws CacheException {
		return get(key,refreshPeriod);
	}
	
	/*
	 * (non-Javadoc)
	 * @see #put(java.lang.Object,java.lang.Object)
	 */
	public void update(Object key, Object value) throws CacheException {
		put(key, value);
	}
	/**
	 * 
	 * 功能描述:更新缓存
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	public void put(Object key, Object value) throws CacheException {
		if(value==null){
			cache.cancelUpdate(toString(key));
		}else{
			cache.putInCache( toString(key), value );
		}		
	}

	/**
	 * 
	 * 功能描述:删除缓存
	 * 
	 * @param key
	 * @throws CacheException
	 */
	public void remove(Object key) throws CacheException {
		cache.flushEntry( toString(key) );
	}

	/**
	 * 
	 * 功能描述:删除所有缓存
	 * @throws CacheException
	 */
	public void clear() throws CacheException {
		cache.flushAll();
	}

	/**
	 * 
	 * 功能描述:销毁缓存容器
	 * @throws CacheException
	 */
	public void destroy() throws CacheException {
		cache.destroy();
	}

	public String getRegionName() {
		return regionName;
	}
	
	public String toString() {
		return "OSCache(" + regionName + ')';
	}
}
