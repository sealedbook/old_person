package com.esite.framework.system.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.esite.framework.util.WebRequestHelper;

public class ExceptionInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(ExceptionInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception exception) throws Exception {
		
		if(null == exception) {
			return;
		}
		LOG.error("catch exception", exception);
        if(WebRequestHelper.isWebClient(request)) {
        	System.out.println(request.getHeader("Content-Type"));
        	HandlerMethod handlerMethod = (HandlerMethod)handler;
    		Method method = handlerMethod.getMethod();
    		
        	ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
        	Class<?> methodReturnType = method.getReturnType();
        	if(null != responseBody || methodReturnType == org.springframework.http.ResponseEntity.class) {
        		ServletOutputStream out = null;
        		try {
    				response.setCharacterEncoding("UTF-8");
    				response.setStatus(500);
    				out = response.getOutputStream();
    				out.print(exception.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if(null != out) {
						out.close();
					}
				}
        	} else {
        		StringPrintWriter strintPrintWriter = new StringPrintWriter();
                exception.printStackTrace(strintPrintWriter);
                response.setStatus(500);
                request.setAttribute("errorMessage", exception.getMessage());
                request.setAttribute("errorMessageDetail", strintPrintWriter.getString());
                request.getRequestDispatcher("/jsp/system/error/index.jsp").forward(request, response);
        	}
        }
	}
	
	class StringPrintWriter extends PrintWriter{  
		  
	    public StringPrintWriter(){  
	        super(new StringWriter());  
	    }  
	     
	    public StringPrintWriter(int initialSize) {  
	          super(new StringWriter(initialSize));  
	    }  
	     
	    public String getString() {  
	          flush();  
	          return ((StringWriter) super.out).toString();  
	    }  
	     
	    @Override  
	    public String toString() {  
	        return getString();  
	    }  
	}
	
}
