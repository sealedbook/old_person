/**
 * (C) Copyright esite Corporation 2010
 *       All Rights Reserved.
 * 2010-12-26
 * zhangzf
 * JdbcBaseDAOImpl.java
 * esite-web-framework
 */
package com.esite.framework.core.dao.jdbcDaoImpl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.esite.framework.core.dao.JdbcBaseDAO;
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
public class OracleJdbcBaseDAOImpl extends AbstractJdbcBaseDAO  implements JdbcBaseDAO{

	private static Logger logger = Logger.getLogger(OracleJdbcBaseDAOImpl.class);

	
	public Serializable getSysGuid() {
		return queryForObject("select sys_guid() id from dual", new RowMapper<Serializable>(){
			public Serializable mapRow(ResultSet rs, int index)throws SQLException {
				return rs.getString("ID");
			}
		});
	}
	
	public Date getSysDate() {
		return queryForObject("select sysdate d from dual", new RowMapper<Date>(){
			public Date mapRow(ResultSet rs, int index)throws SQLException {
				return rs.getDate("D");
			}
		});
	}
	
	@Override
	public <T> Pager<T> queryForPager(String sql, Pager<T> pager,RowMapper<T> rowMapper, Object... params) {
		StringBuffer sqlBuffer = new StringBuffer("SELECT * FROM (  SELECT pqc.*, ROWNUM RN  FROM (");
		sqlBuffer.append(sql).append(")  pqc  WHERE ROWNUM < ");
		sqlBuffer.append(pager.getPage()*pager.getRows()+1);
		int page = pager.getPage();
		if(page -1 > 0) {
			page = pager.getPage()*pager.getRows()+1 - pager.getRows();
		}
		sqlBuffer.append("  )  WHERE RN >= ").append(page);
		logger.debug(sqlBuffer);
		pager.setRowsdata(super.query(sqlBuffer.toString(), params, rowMapper));
		
		StringBuffer countBuffer = new StringBuffer("select count(1) from (").append(sql).append(")");
		pager.setTotalRows(super.queryForInt(countBuffer.toString(),params));
		
		return pager;
	}
	
	@Override
	public Pager<Map<String,Object>> queryForPager(Pager<Map<String,Object>> pager, String sql, Object... params) {
		StringBuffer sqlBuffer = new StringBuffer("SELECT * FROM (  SELECT pqc.*, ROWNUM RN  FROM (");
		sqlBuffer.append(sql).append(")  pqc  WHERE ROWNUM < ");
		sqlBuffer.append(pager.getPage()*pager.getRows()+1);
		int page = pager.getPage();
		if(page -1 > 0) {
			page = pager.getPage()*pager.getRows()+1 - pager.getRows();
		}
		sqlBuffer.append("  )  WHERE RN >= ").append(page);
		pager.setRowsdata(super.queryForList(sqlBuffer.toString(), params));
		
		StringBuffer countBuffer = new StringBuffer("select count(1) from (").append(sql).append(")");
		pager.setTotalRows(super.queryForInt(countBuffer.toString(),params));
		
		return pager;
	}
	

	
}
