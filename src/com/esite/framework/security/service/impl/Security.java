package com.esite.framework.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.esite.framework.core.factory.WebApplicationContextUtil;
import com.esite.framework.security.entity.Role;
import com.esite.framework.security.service.ResourceService;
import com.esite.framework.user.entity.User;
import com.esite.framework.util.WebRequestHelper;

public class Security {
	
	private static Logger logger = Logger.getLogger(Security.class);
	
	public static final String SESSION_USER_KEY = "SESSION_USER";
	
	public static boolean isRight(String requestURI,User user) {
		requestURI = WebRequestHelper.deleteSuffix(requestURI);
		
		ResourceService resourceService = (ResourceService)WebApplicationContextUtil.getBean("resourceService");
		List<String> globalResource = resourceService.getGlobalResourceURL();
		
		List<String> urlRoleURLList = new ArrayList<String>();
		for(Role role : user.getRoleCollection()) {
			if(null == role) {
				continue;
			}
			urlRoleURLList.addAll(role.getRequestUrlCollection());
		}
		for(String globalURL : globalResource) {
			urlRoleURLList.add(globalURL);
		}
		for(String url : urlRoleURLList) {
			Pattern pattern = Pattern.compile(url);
			Matcher matcher = pattern.matcher(requestURI);
			if(matcher.find()) {
				logger.debug("权限认证：" + url + "与" + requestURI + "比较【成功】");
				return true;
			}
		}
		return false;
	}
	
}
