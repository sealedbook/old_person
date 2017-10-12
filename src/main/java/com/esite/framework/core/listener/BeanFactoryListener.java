/*
 * $Header: /repository/esite-web-framework/src/com/esite/framework/core/listener/BeanFactoryListener.java,v 1.1 2011/03/28 01:08:48 zhangzf Exp $
 * $Revision: 1.1 $
 * $Date: 2011/03/28 01:08:48 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * [Additional notices, if required by prior licensing conditions]
 *
 */


package com.esite.framework.core.listener;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.esite.framework.core.factory.WebApplicationContextUtil;

/**
 * Example listener for context-related application events, which were
 * introduced in the 2.3 version of the Servlet API.  This listener
 * merely documents the occurrence of such events in the application log
 * associated with our servlet context.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.1 $ $Date: 2011/03/28 01:08:48 $
 */

public final class BeanFactoryListener
    implements  ServletContextListener {


    // ----------------------------------------------------- Instance Variables


    /**
     * The servlet context with which we are associated.
     */
    private ServletContext context = null;
    private Logger logger= Logger.getLogger(BeanFactoryListener.class);

    // --------------------------------------------------------- Public Methods


    /**
     * Record the fact that a servlet context attribute was added.
     *
     * @param event The servlet context attribute event
     */
    public void attributeAdded(ServletContextAttributeEvent event) {

//	log("attributeAdded('" + event.getName() + "', '" +
//	    event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event The servlet context attribute event
     */
    public void attributeRemoved(ServletContextAttributeEvent event) {

//	log("attributeRemoved('" + event.getName() + "', '" +
//	    event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event The servlet context attribute event
     */
    public void attributeReplaced(ServletContextAttributeEvent event) {

//	log("attributeReplaced('" + event.getName() + "', '" +
//	    event.getValue() + "')");

    }


    /**
     * Record the fact that this web application has been destroyed.
     *
     * @param event The servlet context event
     */
    public void contextDestroyed(ServletContextEvent event) {

	//log("contextDestroyed()");
	this.context = null;
	logger.info("System shutdown。");
    }


    /**
     * Record the fact that this web application has been initialized.
     *
     * @param event The servlet context event
     */
    public void contextInitialized(ServletContextEvent event) {

	this.context = event.getServletContext();
    //ServletContext ctx = config.getServletContext(); 
    WebApplicationContextUtil sf = new WebApplicationContextUtil(context); 
    //Object obj = sf.getBean("userService");
	//log("contextInitialized()");
	logger.info("System start");
	//new Timer(true).schedule(new SchemeTask(),0L,1000L*60L*60L);
    }


    // -------------------------------------------------------- Private Methods


    /**
     * Log a message to the servlet context application log.
     *
     * @param message Message to be logged
     */
//    private void log(String message) {
//
//	if (context != null)
//	    context.log("ContextListener: " + message);
//	else
//	    System.out.println("ContextListener: " + message);
//
//    }
//
//
//    /**
//     * Log a message and associated exception to the servlet context
//     * application log.
//     *
//     * @param message Message to be logged
//     * @param throwable Exception to be logged
//     */
//    private void log(String message, Throwable throwable) {
//
//	if (context != null)
//	    context.log("ContextListener: " + message, throwable);
//	else {
//	    System.out.println("ContextListener: " + message);
//	    throwable.printStackTrace(System.out);
//	}
//
//    }


}
