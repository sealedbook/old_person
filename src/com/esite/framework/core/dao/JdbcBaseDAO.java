/**
 * (C) Copyright esite Corporation 2010
 *       All Rights Reserved.
 * 2010-12-26
 * zhangzf
 * JdbcBaseDAO.java
 * esite-web-framework
 */
package com.esite.framework.core.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.esite.framework.util.Criteria;
import com.esite.framework.util.Pager;

/**
 *<PRE>
 * 功能描述:开发者在此描述类的主要功能或目的
 * 修改历史:
 * -----------------------------------------------------------------------------
 * 		VERSION		DATE		BY			CHANGE/COMMENT
 * -----------------------------------------------------------------------------
 * 		1.0			2010-12-26	zhangzf		create
 * -----------------------------------------------------------------------------
 * </PRE>
 */
public interface JdbcBaseDAO {
	
	public Serializable getSysGuid();
	
	public Date getSysDate();
	
	public <T> T queryForObject(String sql,RowMapper<T> rowMapper,Object... params);
	
	public Map<String,Object> queryForMap(String sql, Object...params);
	
	public List<Map<String,Object>> queryForList(String querySQL, Object... params);
	
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper);
	
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper,Object... params);
	
	public Pager<Map<String,Object>> queryForPager(Pager<Map<String,Object>> pager, String sql, Object... params);
	
	public <T> Pager<T> queryForPager(String sql,Pager<T> pager, RowMapper<T> rowMapper,Object... params);
	
	public int delete(String sql, Object... params);
	
	public int update(String updateSQL, Object... params);
	
	public int count(String sql, Object... params);
	
	public List<Map<String,Object>> query(Criteria criteria);
}
