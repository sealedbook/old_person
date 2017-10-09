package com.esite.framework.security.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.esite.framework.security.entity.Role;
import com.esite.framework.security.service.RoleService;
import com.esite.framework.security.service.UserLoginService;
import com.esite.framework.user.entity.User;
import com.esite.framework.user.service.UserService;
import com.esite.framework.util.StringHelper;

public class UserLoginServiceImpl implements UserLoginService {
	
	private static Logger logger = Logger.getLogger(UserLoginServiceImpl.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Override
	@Transactional("jdbcTransactionManager")
	public User login(User user) {
		if(StringHelper.isEmpty(user.getAccount()) || StringHelper.isEmpty(user.getPassword())) {
			throw new IllegalArgumentException("登录账号/密码不能为空.");
		}
		User dbUser = null;
		try {
			dbUser = userService.getUserByUserAccount(user.getAccount().trim());
			if(null == dbUser) {
				throw new IllegalArgumentException("不存在用该用户名.【" + user.getAccount() + "】");
			}
			//user.setPassword(StringHelper.getMd5(user.getPassword()));
			if(!user.getPassword().equals(dbUser.getPassword())) {
				throw new IllegalArgumentException("密码错误.");
			}
			List<Role> roleList = roleService.getRoleByUserId(dbUser.getId());
			if(null == roleList || roleList.size() <= 0) {
				throw new RuntimeException("该账号还不属于任何角色,请联系管理员进行分配.");
			}
			dbUser.setRoleCollection(roleList);
		} catch(EmptyResultDataAccessException e) {
			throw new IllegalArgumentException("不存在用该用户名.");
		} catch(IncorrectResultSizeDataAccessException e) {
			throw new IllegalArgumentException("该账号存在过多.");
		}
		return dbUser;
	}
	
}
