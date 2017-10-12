package com.esite.framework.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.esite.framework.security.service.impl.Security;
import com.esite.framework.user.entity.User;
import com.esite.framework.util.WebRequestHelper;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger(SecurityInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		Object obj = request.getSession().getAttribute(Security.SESSION_USER_KEY);
		if(null == obj) {
			logger.info("权限认证:用户未登录,请求是:" + WebRequestHelper.getRequestPath(request));
			response.sendRedirect(request.getContextPath() + "/security/login/page.do");
			return false;
		}
		User user = null;
		if(obj instanceof User) {
			 user = (User)obj;
		}
		
		String requestPath = WebRequestHelper.getRequestPath(request);
		if(!Security.isRight(requestPath,user)) {
			String errorMessage = "error request [401] path:" + requestPath;
			request.setAttribute("errorMessage", errorMessage);
			response.sendError(401, errorMessage);
			logger.info(errorMessage);
			return false;
		}
		return true;
	}
	
}
