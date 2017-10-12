package com.esite.framework.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.esite.framework.security.service.impl.Security;
import com.esite.framework.util.WebRequestHelper;

public class SessionTimeoutInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger(SessionTimeoutInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		Object obj = request.getSession(true).getAttribute(Security.SESSION_USER_KEY);
		if(null == obj) {
			logger.info("===========================用户登录超时");
			if(!WebRequestHelper.isWebClient(request)) {
				response.sendError(403);
				response.setStatus(403);
				return false;
			} else {
				response.sendRedirect(request.getContextPath() + "/security/login/page.do");
				return false;
			}
		}
		return true;
	}
	
}
