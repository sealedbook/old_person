package com.esite.framework.security.service;

import com.esite.framework.user.entity.User;

public interface UserLoginService {
	
	/**
	 * 用户登录操作
	 * @param user
	 * @return
	 */
	public User login(User user);
	
}
