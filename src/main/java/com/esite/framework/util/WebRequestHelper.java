package com.esite.framework.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class WebRequestHelper {
	
	private static Logger logger = Logger.getLogger(WebRequestHelper.class);
	
	public static String addEndChar(String url) {
		if(StringHelper.isEmpty(url)) {
			return "";
		}
		String oldURL = url;
		char endChar = url.charAt(url.length()-1);
		if(endChar == '$') {
			return url;
		}
		logger.debug("添加正则表达式后缀操作.原URL:" + oldURL + "增加后URL: " + url + '$');
		return url + '$';
	}
	
	public static String deleteSuffix(String url) {
		String oldURL = url;
		if(StringHelper.isEmpty(url)) {
			return "";
		}
		int idx = url.lastIndexOf(".");
		if(idx != -1) {
			url = url.substring(0,idx);
		}
		logger.debug("删除请求后缀操作.原URL:" + oldURL + "去掉后缀URL: " + url);
		return url;
	}
	
	public static String getRequestPath(HttpServletRequest request) {
		String requestURI = getRequestURI(request);
		String contentPath = getContentPath(request);
		if(contentPath.length() <= 0) {
			return requestURI;
		}
		int index = requestURI.indexOf(contentPath);
		if(index != -1) {
			return requestURI.substring(index + contentPath.length());
		}
		return requestURI;
	}
	
	public static String getContentPath(HttpServletRequest request) {
		String contentPath = (String)request.getAttribute("javax.servlet.include.context_path");
		if(null == contentPath) {
			contentPath = request.getContextPath();
		}
		if("/".equals(contentPath)) {
			contentPath = "";
		}
		return contentPath;
	}

	public static String getRequestURI(HttpServletRequest request) {
		String requestURI = (String)request.getAttribute("javax.servlet.include.request_uri");
		if(null == requestURI) {
			requestURI = request.getRequestURI();
		}
		return requestURI;
	}
	
	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static boolean isWebClient(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if(userAgent.toUpperCase().contains("OLD_PERSON_ANDROID_TERMINAL")) {
			return false;
		}
		return true;
	}
}
