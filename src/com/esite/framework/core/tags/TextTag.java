package com.esite.framework.core.tags;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.esite.framework.core.tags.util.Code2Name;
import com.esite.framework.util.BeanUtil;
import com.esite.framework.util.StringHelper;


/**
 * 
 *<PRE>
 * 功能描述:开发者在此描述类的主要功能或目的
 * 修改历史:
 * -----------------------------------------------------------------------------
 * 		VERSION		DATE		BY			CHANGE/COMMENT
 * -----------------------------------------------------------------------------
 * 		1.0			2010-6-8	zhangzf		create
 * -----------------------------------------------------------------------------
 * </PRE>
 */
public class TextTag extends CodeBaseTag {

	/**
	 * 2008-5-8 esite-web-framework administrator
	 */
	public TextTag() {
		// TODO Auto-generated constructor stub
	}


	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();

		Code2Name[] preCode = genCodeFromOptions(this.preOptions);
		Code2Name[] lastCode = genCodeFromOptions(this.lastOptions);
		Object con = getCode();

		try {

			Map selectedMap = new HashMap();
			String[] seleteds = StringHelper.isEmpty(this.selected) ? new String[0]
					: this.selected.split(",");
			for (int i = 0; i < seleteds.length; i++) {
				seleteds[i] = seleteds[i]==null?null:seleteds[i].trim();
				selectedMap.put(seleteds[i], "1");
			}
			if(selectedMap.size()>0){
				if (preCode != null) {
					for (int i = 0; i < preCode.length; i++) {
						/*if (*/this.writeText(out, preCode[i], selectedMap);/*)*/
							//return SKIP_BODY;
					}
				}
				if (con instanceof Collection) {
					Collection conLocal = (Collection) con;
					if (con != null && conLocal.size() > 0 && this.selected != null
							&& this.selected.length() > 0) {

						Iterator it = conLocal.iterator();
						while (it.hasNext()) {
							this.writeText(out, it.next(), selectedMap);
						}
						//return SKIP_BODY;
					}
				} else if (con instanceof Map) {
					Map conLocal = (Map) con;
					if (con != null && conLocal.size() > 0 && this.selected != null
							&& this.selected.length() > 0) {
						for (int i = 0; i < seleteds.length; i++) {
							if (conLocal.containsKey(seleteds[i]))
								this.writeText(out, conLocal.get(seleteds[i]),
										selectedMap);
						}
						//return SKIP_BODY;
					}
				}

				if (lastCode != null) {
					for (int i = 0; i < lastCode.length; i++) {
						/*if (*/this.writeText(out, lastCode[i], selectedMap);/*)*/
							/*return SKIP_BODY;*/
					}
				}
			}else{
				out.print(this.defaultValue == null ? "" : this.defaultValue);
			}	
			

		} catch (Exception e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		}

		return SKIP_BODY;
	}

	private boolean writeText(JspWriter out, Code2Name code, Map selectedMap)
			throws JspException {
		try {
			if (code == null)
				return false;
			if (selectedMap != null && selectedMap.containsKey(code.getCode())) {
				if(selectedMap.size()>1)out.print(" ");
				out.print(code.getName());
				if (this.var != null && this.varProperty != null
						&& this.var.length() > 0
						&& this.varProperty.length() > 0) {
					Object varValue = BeanUtil.getPropertiesValue(code,
							this.varProperty);
					this.pageContext.setAttribute(this.var, varValue);
				}
				return true;
			}
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		}
		return false;
	}

	private boolean writeText(JspWriter out, Object obj, Map selectedMap)
			throws JspException {
		if (obj == null)
			return false;
		
		if (obj instanceof Code2Name) {
			return writeText(out, (Code2Name) obj, selectedMap);
		}
		try {			
			String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
			String value = BeanUtil.getPropertiesValue(obj, this.valueItem);

			if (selectedMap != null && selectedMap.containsKey(value)) {
				if(selectedMap.size()>1)out.print(" ");
				out.print(dis);
				if (this.var != null && this.varProperty != null
						&& this.var.length() > 0
						&& this.varProperty.length() > 0) {
					Object varValue = BeanUtil.getPropertiesValue(obj,
							this.varProperty);
					this.pageContext.setAttribute(this.var, varValue);
				}
				return true;
			}
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		}
		return false;
	}

}
