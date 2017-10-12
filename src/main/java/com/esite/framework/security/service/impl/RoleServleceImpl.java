package com.esite.framework.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.esite.framework.security.dao.RoleDAO;
import com.esite.framework.security.entity.Role;
import com.esite.framework.security.service.FunctionService;
import com.esite.framework.security.service.ResourceService;
import com.esite.framework.security.service.RoleService;
import com.esite.framework.user.dao.UserDao;
import com.esite.framework.user.entity.User;
import com.esite.framework.util.StringHelper;

public class RoleServleceImpl implements RoleService {

	public static final String ROLE_CACHE_KEY = "GAB.RESOURCE.CATALOG.ROLE.CACHE.KEY.";
	private Cache roleCache;
	public void setRoleCache(Cache roleCache) {
		this.roleCache = roleCache;
	}
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private FunctionService functionService;
	
	@Override
	public List<Role> getRoleByUserId(String userId) {
		List<Map<String,Object>> userRoleIdList = roleDAO.getUserRole(userId);
		List<Role> roleList = new ArrayList<Role>(userRoleIdList.size());
		for(Map<String,Object> map : userRoleIdList) {
			roleList.add(getRoleByRoleId(map.get("ROLE_ID").toString()));
		}
		return roleList;
	}

	@Override
	public Role getRoleByRoleId(String roleId) {
		String key = ROLE_CACHE_KEY + roleId;
		Element element = roleCache.get(key);
		if(null == element) {
			Role role = createRole(roleId);
			if(null == role) {
				return null;
			}
			roleCache.put(new Element(key,role));
			return role;
		} else {
			Object obj = element.getObjectValue();
			if(obj instanceof Role) {
				return (Role)obj;
			}
		}
		return null;
	}

	private Role createRole(String id) {
		try {
			Role role = roleDAO.queryRoleById(id);
			relevancy(role);
			return role;
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Role> getAllRoleInSystem() {
		List<Role> roleList = roleDAO.loadAllRole();
		for(Role role : roleList) {
			relevancy(role);
			String key = ROLE_CACHE_KEY + role.getId();
			roleCache.put(new Element(key,role));
		}
		return roleList;
	}

	private void relevancy(Role role) {
		resourceService.relevancyUrlWithRole(role);
		functionService.relevancyFunction(role);
	}

	@Override
	public void saveRole(Role role)throws DuplicateKeyException{
		this.roleDAO.save(role);
	}

	@Override
	public void updateRole(Role role) {
		this.roleDAO.save(role);
	}

	@Override
	public Page<Role> queryRoleList(Pageable instance) {
		return this.roleDAO.findAll(new Specification<Role>() {
			
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				return predicate;
			}
		}, instance);
	}

	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			if(StringHelper.isNotEmpty(id)){
				this.roleDAO.delete(id);
			}
		}
	}

	@Override
	public boolean findRoleByName(String name) {
		List<Role> list= this.roleDAO.findRoleByName(name);
		if(list.size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public void saveUrMap(String userId,String[] roleIdArray) {
		this.roleDAO.deleteUrMap(userId);
		for(String roleId : roleIdArray){
			this.roleDAO.saveUrMap(userId,roleId);
		}
	}

	@Override
	public void saveUrMap(String userId, String roleId) {
		this.roleDAO.saveUrMap(userId,roleId);
	}

	@Override
	public void deleteUrMap(String userId, String roleId) {
		this.roleDAO.deleteUrMap(userId, roleId);
	}
	@Autowired
	private UserDao userDAO;
	@Override
	public void saveUrMapForIdCard(String idCard, String managerRoleId) {
		// TODO Auto-generated method stub
		User user = this.userDAO.getUserByIdCard(idCard);
		if(null == user) {
			return;
		}
		this.saveUrMap(user.getId(), managerRoleId);
	}

	@Override
	public void deleteUrMapForIdCard(String idCard, String managerRoleId) {
		// TODO Auto-generated method stub
		User user = this.userDAO.getUserByIdCard(idCard);
		if(null == user) {
			return;
		}
		this.deleteUrMap(user.getId(), managerRoleId);
	}

}
