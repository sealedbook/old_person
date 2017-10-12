package com.esite.framework.core.tags.dic2name;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esite.framework.core.factory.WebApplicationContextUtil;
import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.impl.OrganizeService;

public class OrganizeCode2NameTag extends TagSupport {
	
	private static final long serialVersionUID = -2761945567079070555L;
	private String code;
	public void setCode(String code) {
		this.code = code;
	}
	
	private boolean fullName;
	public void setFullName(boolean fullName) {
		this.fullName = fullName;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = super.pageContext.getOut();
		
		try {
			OrganizeService organizeService = (OrganizeService)WebApplicationContextUtil.getBean("organizeService");
			OrganizeViewEntity organize = organizeService.getOrganizeById(code);
			if(null != organize) {
				if(fullName) {
					out.print(organize.getFullName());
				} else {
					out.print(organize.getName());
				}
			} else {
				out.print("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
