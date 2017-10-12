package com.esite.framework.core.dao.jdbcDaoImpl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.esite.framework.core.dao.JdbcBaseDAO;
import com.esite.framework.util.Pager;

public class MySQLJdbcBaseDAOImpl extends AbstractJdbcBaseDAO implements JdbcBaseDAO {

	@Override
	public Serializable getSysGuid() {
		return queryForObject("select REPLACE(UUID(),'-','') id from DUAL", new RowMapper<Serializable>(){
			public Serializable mapRow(ResultSet rs, int index)throws SQLException {
				return rs.getString("ID");
			}
		});
	}

	@Override
	public Date getSysDate() {
		return queryForObject("select SYSDATE() d from dual", new RowMapper<Date>(){
			public Date mapRow(ResultSet rs, int index)throws SQLException {
				return rs.getDate("D");
			}
		});
	}

	@Override
	public Pager<Map<String, Object>> queryForPager(
			Pager<Map<String, Object>> pager, String sql, Object... params) {
		
		return null;
	}

	@Override
	public <T> Pager<T> queryForPager(String sql, Pager<T> pager,
			RowMapper<T> rowMapper, Object... params) {
		
		return null;
	}

}
