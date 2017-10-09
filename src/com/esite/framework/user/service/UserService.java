package com.esite.framework.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.user.entity.User;

public interface UserService {
	
	public User getUserByUserAccount(String username);
	
	public User getUserById(String id);

	public Page<User> getUserCollection(Pageable instance);

	public void save(User user,String[] roleIdArray);

	public void update(User user);

	public void delete(String[] ids);
	
	public void deleteByIdCard(String idCard);

	/**
	 * 密码修改
	 * @param oldPassword
	 * @param newPassword
	 */
	public void modifyPassword(String oldPassword, String newPassword,Customer customer);
}
