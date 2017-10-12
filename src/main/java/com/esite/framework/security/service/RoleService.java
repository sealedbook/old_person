package com.esite.framework.security.service;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esite.framework.security.entity.Role;

public interface RoleService {
	public static final String OPERATOR_ROLE_ID = "402881ee4a48d7dd014a48d907030001";
	public static final String OLD_PERSON_ROLE_ID = "402881e94ab3e7f7014ab3fdbc63000e";
	
	/**
	 * 根据用户id获得角色列表
	 * @param userId
	 * @return
	 */
	public List<Role> getRoleByUserId(String userId);
	
	/**
	 * 根据角色id获得角色对象
	 * @param roleId
	 * @return
	 */
	public Role getRoleByRoleId(String roleId);
	/**
	 * 从系统中取得所有角色
	 * @return
	 */
	public List<Role> getAllRoleInSystem();
	
	
	public Page<Role> queryRoleList(Pageable pageable);
	
	public void saveRole(Role role)throws DuplicateKeyException;
	
	public void updateRole(Role role);

	public void delete(String[] ids);

	public boolean findRoleByName(String name);

	public void saveUrMap(String userId,String[] roleIdArray);
	
	public void saveUrMap(String userId,String roleId);

	public void deleteUrMap(String id, String managerRoleId);

	public void saveUrMapForIdCard(String idCard, String managerRoleId);

	public void deleteUrMapForIdCard(String idCard, String managerRoleId); 
	
}
