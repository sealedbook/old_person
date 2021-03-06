package com.esite.ops.oldperson.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.security.service.RoleService;
import com.esite.framework.user.entity.User;
import com.esite.framework.user.service.UserService;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.IOldPersonSecurityService;

public class OldPersonSecurityServiceImpl implements IOldPersonSecurityService {
	
	@Autowired
	private UserService userService;
	
	@Override
	public void addSystemUser(OldPersonEntity oldPerson) {
		if(null == oldPerson) {
			return;
		}
		//当老年人属于本地人时,不进行用户创建
		if(1 == oldPerson.getType()) {
			return;
		}
		User user = new User();
		user.setAccount(oldPerson.getIdCard());
		user.setIdCard(oldPerson.getIdCard());
		//user.setPassword(oldPerson.getIdCard().substring(12, 18));
		//老年人登陆密码由于输入15位导致后6位人工无法准确知道，因此修改为默认密码是123456
		user.setPassword("123456");
		user.setSex(oldPerson.getSex());
		user.setShowName(oldPerson.getName());
		userService.save(user, new String[]{RoleService.OLD_PERSON_ROLE_ID});
	}

	@Override
	public void deleteSystemUser(OldPersonEntity oldPerson) {
		userService.deleteByIdCard(oldPerson.getIdCard());
	}
}
