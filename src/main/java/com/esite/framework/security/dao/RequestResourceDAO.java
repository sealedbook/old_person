package com.esite.framework.security.dao;

import java.util.List;

import com.esite.framework.core.dao.JdbcBaseDAO;
import com.esite.framework.security.entity.RequestResource;

public interface RequestResourceDAO extends JdbcBaseDAO {

	public List<RequestResource> getResourceByRoleId(String roleId);

	public List<RequestResource> getGlobalResource();

}
