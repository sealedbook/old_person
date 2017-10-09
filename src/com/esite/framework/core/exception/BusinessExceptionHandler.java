package com.esite.framework.core.exception;
//
//package com.esite.framework.core.exception;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//import org.apache.struts.action.ExceptionHandler;
//import org.apache.struts.config.ExceptionConfig;
//
///**
// * @author zhangzf
// *
// * TODO To change the template for this generated type comment go to
// * Window - Preferences - Java - Code Style - Code Templates
// */
//public class BusinessExceptionHandler extends ExceptionHandler {
//	Logger logger = Logger.getLogger("EXCEPTION");
//	/* (non-Javadoc)
//	 * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception, org.apache.struts.config.ExceptionConfig, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
//	 */
//	public ActionForward execute(
//		Exception exception,
//		ExceptionConfig config,
//		ActionMapping mapping,
//		ActionForm form,
//		HttpServletRequest request,
//		HttpServletResponse response)
//		throws ServletException{
//		if(exception instanceof BaseException)
//			request.setAttribute("exception", (exception.getMessage()==null?exception.toString():exception.getMessage()));
//		else
//			request.setAttribute("exception", "未预料到的异常: "+(exception.getMessage()==null?exception.toString():exception.getMessage()));
//		return mapping.findForward("FAIL");
//	}
//}
