package com.esite.framework.core.tags.dic2name;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esite.framework.core.factory.WebApplicationContextUtil;
import com.esite.framework.dictionary.entity.DictionaryEntity;
import com.esite.framework.dictionary.service.DictionaryService;

public class DictionaryCode2NameTag extends TagSupport {

	private static final long serialVersionUID = 3342018432988027631L;
	private String code;
	public void setCode(String code) {
		this.code = code;
	}
	private String parentId;
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	private String showDefault;
	public void setShowDefault(String showDefault) {
		this.showDefault = showDefault;
	}


	@Override
	public int doStartTag() throws JspException {
		JspWriter out = super.pageContext.getOut();
		
		try {
			DictionaryService dictionaryService = (DictionaryService)WebApplicationContextUtil.getBean("dictionaryService");
			DictionaryEntity dic = dictionaryService.getDictionaryByParentIdAndCode(parentId, code);
			if(null != dic) {
				out.print(dic.getDicName());
			} else {
				out.print(showDefault);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
