package com.esite.framework.system.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.esite.framework.core.enums.CustomHttpStatus;
import com.esite.framework.security.service.impl.Security;
import com.esite.framework.user.entity.User;
import com.esite.framework.util.StringHelper;
import com.esite.framework.util.WebRequestHelper;
import com.esite.ops.operator.service.IOperatorSecurityService;

public class TerminalTokenInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(TerminalTokenInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(!WebRequestHelper.isWebClient(request)) {
			response.setCharacterEncoding("UTF-8");
			String token = request.getParameter(IOperatorSecurityService.TERMINAL_REQUEST_PARAM_OPERATOR_TOKEN);
			if(StringHelper.isEmpty(token)) {
				response.setStatus(CustomHttpStatus.SESSION_TIME_OUT.getCode());
				PrintWriter out = response.getWriter();
				out.print(CustomHttpStatus.SESSION_TIME_OUT.getCode());
				logger.info("终端登录超时.终端请求" + WebRequestHelper.getRequestURI(request));
				return false;
			}
			Object userObject = request.getServletContext().getAttribute(IOperatorSecurityService.SERVLET_OPERATOR_TOKEN_KEY_PREFIX + token);
			if(userObject instanceof User) {
				User user = (User)userObject;
				request.getSession().setAttribute(Security.SESSION_USER_KEY, user);
				logger.info("令牌检测正常.终端请求" + WebRequestHelper.getRequestURI(request));
				return true;
			}
			return false;
		}
		return true;
	}
	
	
	
}
