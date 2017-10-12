package com.esite.framework.core.tags.dic2name;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esite.framework.core.factory.WebApplicationContextUtil;
import com.esite.framework.user.entity.User;
import com.esite.framework.user.service.impl.UserService;

public class UserCode2NameTag extends TagSupport {
	
	private static final long serialVersionUID = -2761945567079070555L;
	private String code;
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = super.pageContext.getOut();
		try {
			UserService userService = (UserService)WebApplicationContextUtil.getBean("userService");
			User user = userService.getUserById(code);
			if(null != user) {
					out.print(user.getShowName());
			} else {
				out.print("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
