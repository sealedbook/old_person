package com.esite.framework.core.tags;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
public class SelectTag extends CodeBaseTag {
	
	private boolean multiple = false;
	private boolean autoComplete = false;
	protected static String IMPORT_AUTO_COMPLETE_JS_FLAG = "import.auto.complete.js.flag";
	private Map selectedMap = null;
	/**
	 * 2008-5-8 esite-web-framework administrator
	 */
	public SelectTag() {
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @return the multiple
	 */
	public boolean isMultiple() {
		return multiple;
	}



	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	
	

	/**
	 * @return the selectedMap
	 */
	public Map getSelectedMap() {
		return selectedMap;
	}



	/**
	 * @param selectedMap the selectedMap to set
	 */
	public void setSelectedMap(Map selectedMap) {
		this.selectedMap = selectedMap;
	}
	


	public boolean isAutoComplete() {
		return autoComplete;
	}



	public void setAutoComplete(boolean autoComplete) {
		this.autoComplete = autoComplete;
	}



	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();

		Code2Name[] preCode = genCodeFromOptions(this.preOptions);
		Code2Name[] lastCode = genCodeFromOptions(this.lastOptions);
		Object con = getCode();

		this.selectedMap = new HashMap();
		
		if(this.selected!=null){
			String[] st = this.selected.split(",");
			for(int i=0;i<st.length;i++){
				this.selectedMap.put(st[i]==null?"":st[i].toUpperCase(), "1");
			}
		}else{
			if(this.defaultValue!=null){
				String[] st = this.defaultValue.split(",");
				for(int i=0;i<st.length;i++){
					this.selectedMap.put(st[i]==null?"":st[i].toUpperCase().trim(), "1");
				}
			}
		}
		try {
			out.print("<select ");
			if (name != null && name.length() > 0)
				out.print(" name=\"" + name + "\"" );
			String uid = id != null && id.length() > 0?id:name;
			out.print(" id=\"" + uid + "\"");
			
			if(this.isAutoComplete()){
				out.print(" class=\"default\"");
			}else if (this.className != null && className.length() > 0) {
				out.print(" class=\"" + className + "\"");
			}
			if (style != null && style.length() > 0)
				out.print(" style=\"" + style + "\"");
			if (event != null && event.length() > 0)
				out.print(" " + event + " ");
			if (disabled == true)
				out.print(" disabled=\"" + disabled + "\"");
			if (multiple == true)
				out.print(" multiple=\"multiple\"");
			out.println(">");
			if (preCode != null) {
				for (int i = 0; i < preCode.length; i++) {
					this.writeOption(out, preCode[i]);
				}
			}
			if (con instanceof Collection) {
				Collection conLocal = (Collection) con;
				if (con != null && conLocal.size() > 0) {
					Iterator it = conLocal.iterator();
					while (it.hasNext()) {
						if (this.useKey) {
							this.writeOptionWithKey(out, it.next());
						} else {
							this.writeOption(out, it.next());
						}

					}
				}
			} else if (con instanceof Map) {
				Map conLocal = (Map) con;
				if (con != null && conLocal.size() > 0) {
					Iterator it = conLocal.values().iterator();
					while (it.hasNext()) {
						if (this.useKey) {
							this.writeOptionWithKey(out, it.next());
						} else {
							this.writeOption(out, it.next());
						}
					}
				}
			}

			if (lastCode != null) {
				for (int i = 0; i < lastCode.length; i++) {
					this.writeOption(out, lastCode[i]);
				}
			}
			out.print("</select>");
			if(this.isAutoComplete()){
				if(!"true".equals(this.pageContext.getAttribute(IMPORT_AUTO_COMPLETE_JS_FLAG))){
					String path = ((HttpServletRequest) this.pageContext.getRequest()).getContextPath();
					out.print("<link href=\""+path+"/themes/base/jquery.ui.all.css\" rel=\"stylesheet\" type=\"text/css\"/>");
					out.print("<link href=\""+path+"/css/framework/combobox.css\" rel=\"stylesheet\" type=\"text/css\"/>");
					out.print("<script src=\""+path+"/js/jquery-ui-1.8.10.custom.min.js\"></script>");
					out.print("<script src=\""+path+"/js/jquery.ui.autocomplete.js\"></script>");
					out.print("<script src=\""+path+"/js/esite-autocomplete.js\"></script>");					
					this.pageContext.setAttribute(IMPORT_AUTO_COMPLETE_JS_FLAG,"true");
				}
				out.println("<script>$(function() {$( \"#"+uid+"\" ).combobox();});</script>");
			}
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		}
		return SKIP_BODY;
	}

	private void writeOption(JspWriter out, Code2Name code) throws JspException {
		try {
			out.print("<option value=\"");
			out.print(code.getCode());
			out.print("\" ");
			if (this.selectedMap.containsKey(StringHelper.toUpperCase(code.getCode()))) {
				out.print(" selected ");
			}
			out.print(">");
			out.print(code.getName());
			out.println("</option>");
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		}
	}

	private void writeOptionWithKey(JspWriter out, Code2Name code, String key)
			throws JspException {
		try {
			out.print("<option value=\"");
			out.print(code.getCode());
			out.print("\" ");
			out.print(" key=\"");
			out.print(key == null ? "" : key);
			out.print("\" ");
			if (this.selectedMap.containsKey(StringHelper.toUpperCase(code.getCode()))) {
				out.print(" selected ");
			}
			out.print(">");
			out.print(code.getName());
			out.println("</option>");
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		}
	}

	private void writeOption(JspWriter out, Object obj) throws JspException {
		if (obj instanceof Code2Name) {
			writeOption(out, (Code2Name) obj);
			return;
		}
		try {
			String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
			String value = BeanUtil.getPropertiesValue(obj, this.valueItem);
			out.print("<option value=\"");
			out.print(value);
			out.print("\" ");

			if (this.selectedMap.containsKey(StringHelper.toUpperCase(value))) {
				out.print(" selected ");
				if (this.var != null && this.varProperty != null
						&& this.var.length() > 0
						&& this.varProperty.length() > 0) {
					Object varValue = BeanUtil.getPropertiesValue(obj,
							this.varProperty);
					this.pageContext.setAttribute(this.var, varValue);
				}
			}
			out.print(">");
			out.print(dis);
			out.println("</option>");
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		}
	}

	private void writeOptionWithKey(JspWriter out, Object obj)
			throws JspException {
		if (obj instanceof Code2Name) {
			writeOption(out, (Code2Name) obj);
			return;
		}
		try {
			String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
			String value = BeanUtil.getPropertiesValue(obj, this.valueItem);
			String key = BeanUtil.getPropertiesValue(obj, this.optionKey);
			out.print("<option value=\"");
			out.print(value);
			out.print("\" ");
			if (this.isUseKey()) {
				out.print(" key=\"");
				out.print(key == null ? "" : key);
				out.print("\" ");
			}
			if (this.selectedMap.containsKey(StringHelper.toUpperCase(value))) {
				out.print(" selected ");
				if (this.var != null && this.varProperty != null
						&& this.var.length() > 0
						&& this.varProperty.length() > 0) {
					Object varValue = BeanUtil.getPropertiesValue(obj,
							this.varProperty);
					this.pageContext.setAttribute(this.var, varValue);
				}
			}
			out.print(">");
			out.print(dis);
			out.println("</option>");
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new JspException("生成标签时发生错误!" + e.getMessage());
		}
	}

}
