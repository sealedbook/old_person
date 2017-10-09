package com.esite.framework.security.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.esite.framework.security.entity.Role;

public interface RoleDAO extends CrudRepository<Role, String>,JpaSpecificationExecutor<Role> {
	
	@Query(value="select t.role_id ROLE_ID from SYS_SECURITY_UR_MAP t where t.user_id=?1",nativeQuery=true)
	public List<Map<String,Object>> getUserRole(String userId);

	@Query(value="from Role t where t.id=?1")
	public Role queryRoleById(String id);

	@Query("from Role")
	public List<Role> loadAllRole();

	@Query("from Role t where t.name=?1")
	public List<Role> findRoleByName(String name);
	
	@Modifying
	@Query(value="insert into SYS_SECURITY_UR_MAP(ID,USER_ID,ROLE_ID) values(replace(UUID(),'-',''),?1,?2)",nativeQuery=true)
	@Transactional
	public void saveUrMap(String userId, String roleId);
	
	@Modifying
	@Query(value="delete from SYS_SECURITY_UR_MAP where user_id=?1",nativeQuery=true)
	@Transactional
	public void deleteUrMap(String userId);
	
	@Modifying
	@Query(value="delete from SYS_SECURITY_UR_MAP where user_id=?1 and role_id=?2",nativeQuery=true)
	@Transactional
	public void deleteUrMap(String userId,String roleId);
	
}
