package com.esite.framework.system.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.esite.framework.system.annotation.Token;
import com.esite.framework.util.StringHelper;
import com.esite.framework.util.WebRequestHelper;

public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(AvoidDuplicateSubmissionInterceptor.class);
	
	public static final String TOKEN_STRING = "esite_form_token";
	public static final String TOKEN_STRING_OLD = "esite_form_token_old";
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		String requestUrl = WebRequestHelper.getRequestURI(request);
		Token annotation = method.getAnnotation(Token.class);
		if (annotation != null) {
			logger.info("The AvoidDuplicateSubmit Interceptor intercept request url : " + requestUrl);
			boolean create = annotation.create();
			if (create) {
				request.getSession(false).setAttribute(TOKEN_STRING,StringHelper.createUUID());
			}

			boolean remove = annotation.remove();
			if (remove) {
				if (isRepeatSubmit(request)) {
					logger.info("The AvoidDuplicateSubmit is repeat submit!!! request url : " + requestUrl);
					response.setStatus(700);
					response.sendError(700, "系统正在处理...请稍后");
					return false;
				}
				request.getSession(false).setAttribute(TOKEN_STRING_OLD, request.getSession(false).getAttribute(TOKEN_STRING));
				request.getSession(false).removeAttribute(TOKEN_STRING);
			}
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) throws Exception {
		
		int httpStatus = response.getStatus();
		if(null == exception && (httpStatus == 200 || httpStatus == 302)) {
		} else {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				boolean remove = annotation.remove();
				if (remove) {
					//request.getSession(false).removeAttribute(TOKEN_STRING);
					request.getSession(false).setAttribute(TOKEN_STRING, request.getSession(false).getAttribute(TOKEN_STRING_OLD));
				}
			}
		}
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute(TOKEN_STRING);
		if (serverToken == null) {
			return true;
		}
		String clinetToken = request.getParameter(TOKEN_STRING);
		if (clinetToken == null) {
			return true;
		}
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}
}
