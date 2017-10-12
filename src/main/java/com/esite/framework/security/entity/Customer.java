package com.esite.framework.security.entity;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.esite.framework.security.service.impl.Security;
import com.esite.framework.user.entity.User;

public class Customer {
	private Logger logger = Logger.getLogger(Customer.class);
	private User user;
	private String ip;
	private String userAgent;
	
	public Customer(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(Security.SESSION_USER_KEY);
		if(null == obj) {
			logger.debug(request.getRemoteAddr() + "还没有登录系统,Referer:" + request.getHeader("Referer"));
			throw new RuntimeException("还没有登录系统");
		}
		if(obj instanceof User) {
			this.user = (User)obj;
		}
		this.ip = request.getRemoteAddr();
		this.userAgent = request.getHeader("User-Agent");
	}

	public User getUser() {
		return user;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	
}
