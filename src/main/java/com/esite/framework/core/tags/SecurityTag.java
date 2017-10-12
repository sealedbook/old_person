package com.esite.framework.core.tags;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.esite.framework.security.service.impl.Security;
import com.esite.framework.user.entity.User;

public class SecurityTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8621492610400462551L;
	private String url;
	
	

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		
		Boolean falgObj = (Boolean)this.pageContext.getAttribute(url);
		if(falgObj!=null){
			if(falgObj.booleanValue()){
				return EVAL_PAGE;
			}
			else{
			    return SKIP_BODY;		    
			}
		}
		HttpSession session = this.pageContext.getSession();
		Object object = session.getAttribute(Security.SESSION_USER_KEY);
		User user = null;
		if(object instanceof User) {
			user = (User)object;
		}
		boolean isAuth = Security.isRight(url,user);
		if(isAuth){
			this.pageContext.setAttribute(url, new Boolean(isAuth));
			return EVAL_PAGE;
		}
		else{
		    return SKIP_BODY;		    
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#release()
	 */
	public void release() {
		// TODO Auto-generated method stub
		super.release();
	}
	
	
}