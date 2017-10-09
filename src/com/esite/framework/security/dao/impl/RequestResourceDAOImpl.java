package com.esite.framework.security.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.esite.framework.core.dao.jdbcDaoImpl.JdbcDAOImpl;
import com.esite.framework.security.dao.RequestResourceDAO;
import com.esite.framework.security.entity.RequestResource;
import com.esite.framework.util.WebRequestHelper;

public class RequestResourceDAOImpl extends JdbcDAOImpl implements RequestResourceDAO {

	@Override
	public List<RequestResource> getResourceByRoleId(String roleId) {
		String sql = "select * from SYS_SECURITY_RESOURCE r where r.function_id in (select rf.function_id from SYS_SECURITY_RF_MAP rf where rf.role_id=?)";
		return super.query(sql, new Object[]{roleId}, new RequestResourceRowMapper());
	}

	@Override
	public List<RequestResource> getGlobalResource() {
		return super.query("select * from SYS_SECURITY_RESOURCE t where t.resource_type = '1'", new RequestResourceRowMapper());
	}

}
class RequestResourceRowMapper implements RowMapper<RequestResource> {

	@Override
	public RequestResource mapRow(ResultSet rs, int rowNum) throws SQLException {
		RequestResource requestResource = new RequestResource();
		requestResource.setId(rs.getString("ID"));
		requestResource.setName(rs.getString("RESOURCE_NAME"));
		requestResource.setUrl(WebRequestHelper.addEndChar(WebRequestHelper.deleteSuffix(rs.getString("RESOURCE_URL"))));
		requestResource.setFunctionId(rs.getString("FUNCTION_ID"));
		return requestResource;
	}
	
}