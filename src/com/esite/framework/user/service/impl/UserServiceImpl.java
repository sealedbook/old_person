package com.esite.framework.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.security.service.RoleService;
import com.esite.framework.user.dao.UserDao;
import com.esite.framework.user.entity.User;
import com.esite.framework.user.service.UserService;
import com.esite.framework.util.IdentityCardHelper;
import com.esite.framework.util.StringHelper;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDAO;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	public User getUserByUserAccount(String username) {
		return this.userDAO.getUserByAccount(username);
	}
	
	@Override
	public User getUserById(String id) {
		return this.userDAO.findOne(id);
	}

	@Override
	public Page<User> getUserCollection(Pageable pageable) {
		return this.userDAO.getUserCollection(pageable);
	}

	@Override
	@Transactional
	public void save(User user,String[] roleIdArray) {
		if(StringHelper.isNotEmpty(user.getPassword())){
			user.setPassword(StringHelper.getMd5(user.getPassword()));
		}
		user.setSex(IdentityCardHelper.getSex(user.getIdCard()));
		save(user);
		roleService.saveUrMap(user.getId(), roleIdArray);
	}

	private void save(User user) {
		this.userDAO.save(user);
	}
	
	@Override
	public void update(User user) {
		this.userDAO.save(user);
	}

	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			if(StringHelper.isNotEmpty(id)){
				this.userDAO.delete(id);
			}
		}
	}

	@Override
	public void deleteByIdCard(String idCard) {
		this.userDAO.deleteUserByIdCard(idCard);
	}

	@Override
	public void modifyPassword(String oldPassword, String newPassword,Customer customer) {
		if(StringHelper.isEmpty(oldPassword)) {
			throw new IllegalArgumentException("旧密码不能为空.");
		}
		if(StringHelper.isEmpty(newPassword)) {
			throw new IllegalArgumentException("新密码不能为空.");
		}
		if(oldPassword.equals(newPassword)) {
			throw new IllegalArgumentException("旧密码与新密码相同.");
		}
		if(!oldPassword.equals(customer.getUser().getPassword())) {
			throw new IllegalArgumentException("旧密码输入错误.");
		}
		User u = this.getUserById(customer.getUser().getId());
		u.setPassword(newPassword);
		this.save(u);
	}

}
