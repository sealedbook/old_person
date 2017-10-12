package com.esite.framework.core.dao.jdbcDaoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.esite.framework.util.Criteria;
import com.esite.framework.util.StringHelper;

public abstract class AbstractJdbcBaseDAO extends JdbcTemplate {

	private String table;
	private String defaultOrderBy;
	
	public <T> T queryForObject(String sql,RowMapper<T> rowMapper,Object... params) {
		try {
			T obj = super.queryForObject(sql, params, rowMapper);
			return obj;
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw e;
		}
	}
	
	public Map<String,Object> queryForMap(String sql, Object...params) {
		try {
			return super.queryForMap(sql, params);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw e;
		}
	}

	public List<Map<String, Object>> queryForList(String sql, Object... params) {
		return super.queryForList(sql, params);
	}
	
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) {
		return super.query(sql, rowMapper);
	}
	
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper,Object... params) {
		return super.query(sql, params, rowMapper);
	}
	

	public int delete(String sql, Object... params) {
		int total = super.update(sql, params);
		return total;
	}
	
	public int update(String sql, Object... params) {
		return super.update(sql, params);
	}
	
	public int count(String sql, Object... params) {
		return super.queryForInt(sql, params);
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setDefaultOrderBy(String defaultOrderBy) {
		this.defaultOrderBy = defaultOrderBy;
	}
	

	public List<Map<String, Object>> query(Criteria criteria) {
		StringBuilder sb = new StringBuilder("select * from ").append(this.table);
		Object[] params = null;
		if(criteria!=null){
			sb.append(" ").append("where").append(" ").append(criteria.toString());
			params = criteria.getValueArray().toArray();
		}
		if(criteria!=null&&StringHelper.isNotEmpty(criteria.getOrderBy())){
			sb.append(" order by ").append(criteria.getOrderBy());
		}else if(StringHelper.isNotEmpty(this.defaultOrderBy)){
			sb.append(" order by ").append(this.defaultOrderBy);
		}
		return super.queryForList(sb.toString(), params);
	}
	
}
