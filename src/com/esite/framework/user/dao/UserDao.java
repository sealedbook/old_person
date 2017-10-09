package com.esite.framework.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.framework.user.entity.User;

public interface UserDao extends CrudRepository<User,String> {
	
	public User getUserByAccount(String account);
	
	public User getUserByIdCard(String idCard);
	
	@Query("from User u")
	public Page<User> getUserCollection(Pageable pageable);
	
	public void deleteUserByIdCard(String idCard);

}
