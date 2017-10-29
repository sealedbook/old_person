package com.esite.framework.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.esite.framework.user.entity.User;

public interface UserDao extends CrudRepository<User,String> {
	
	public User getUserByAccount(String account);
	
	public User getUserByIdCard(String idCard);
	
	@Query("from User u")
	public Page<User> getUserCollection(Pageable pageable);

	@Modifying
	@Query(value="delete from sys_security_user where id=?1",nativeQuery=true)
	@Transactional
	public void deleteUserByIdCard(String idCard);

}
