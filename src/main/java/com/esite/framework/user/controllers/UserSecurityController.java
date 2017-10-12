package com.esite.framework.user.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.user.service.impl.UserService;
import com.esite.ops.operator.service.impl.OperatorSecurityService;

@Controller
@RequestMapping("/security/user")
public class UserSecurityController {

	@Resource
	private UserService userService;
	
	@RequestMapping("/modifyPassword")
	public ResponseEntity<String> modifyPassword(String oldPassword,String newPassword,HttpServletRequest request) {
		try {
			Customer customer = new Customer(request);
			userService.modifyPassword(oldPassword,newPassword,customer);
			request.getServletContext().removeAttribute(
				OperatorSecurityService.SERVLET_OPERATOR_TOKEN_KEY_PREFIX + customer.getUser().getId());
			return new ResponseEntity<String>("修改成功", HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/modifyPasswordPage")
	public String modifyPasswordPage() {
		return "/system/user/modifyPassword";
	}
	
}

