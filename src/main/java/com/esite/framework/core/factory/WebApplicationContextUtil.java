package com.esite.framework.core.factory;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * WebApplicationContext工具类
 * @author sealedbook
 *
 */
public class WebApplicationContextUtil{
	
    private static WebApplicationContext wac; 

    public WebApplicationContextUtil(ServletContext ctx) { 
        wac = (WebApplicationContext) ctx.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE); 
        System.out.println( "***********ServiceFactory  start ***********" );
    }
   
    public static Object getBean(String beanName) {
        Object bean = wac.getBean(beanName);
        return bean;
    }
    
    /**
     * 获得国际化信息
     * @param key 国际化配置文件中的key
     * @param request HttpServletRequest
     * @return
     */
    public static String getMessage(String key,HttpServletRequest request) {
    	return getMessage(key,null, request);
    }
    
    /**
     * 获得国际化信息
     * @param key 国际化配置文件中的key
     * @param defaultMessage 默认显示的信息
     * @param request HttpServletRequest
     * @return
     */
    public static String getMessage(String key,String defaultMessage,HttpServletRequest request) {
    	return getMessage(key,null, defaultMessage, request);
    }
    
    /**
     * 获得国际化信息
     * @param key 国际化配置文件中的key
     * @param params 国际化配置文件中value的参数{0}{1}...{n}依次代替
     * @param defaultMessage 默认显示的信息
     * @param request HttpServletRequest
     * @return
     */
    public static String getMessage(String key,String[] params,String defaultMessage,HttpServletRequest request) {
    	Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
    	if(null == defaultMessage) {
    		defaultMessage = "";
    	}
    	return wac.getMessage(key,params, defaultMessage, locale);
    }
}








