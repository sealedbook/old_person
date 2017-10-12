package com.esite.framework.system.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.esite.framework.security.entity.Customer;
import com.esite.framework.util.WebRequestHelper;

@Component
public class SystemExceptionHandler implements HandlerExceptionResolver {

	private static Logger logger = Logger.getLogger(SystemExceptionHandler.class);
	
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response, Object object, Exception exception) {
		log(request,exception);
        return null;
	}

	private void log(HttpServletRequest request, Exception exception) {
		logger.error("Catch Exception: ",exception);
		StringPrintWriter strintPrintWriter = new StringPrintWriter();
		exception.printStackTrace(strintPrintWriter);
		if(!WebRequestHelper.isWebClient(request)) {
			//String terminalNumber = request.getParameter(ITerminalHttpRequestParam.HTTP_REQUEST_TERMINAL_IMEI);
			//String terminalType = request.getParameter(ITerminalHttpRequestParam.HTTP_REQUEST_TERMINAL_TYPE);
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
