package com.esite.framework.core.tags;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;


import com.esite.framework.core.factory.WebApplicationContextUtil;
import com.esite.framework.core.tags.util.Code2Name;
import com.esite.framework.core.tags.util.FetchData;
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
public class CodeBaseTag extends TagSupport {

	private static final long serialVersionUID = 4853204331252529351L;
	private Logger logger = Logger.getLogger(CodeBaseTag.class);

	protected String codeBean="dictionaryService";
	protected String method="findSubDictionaryFromCache";
	
	/**
	 * options 集合
	 * Collection Map(List Map等)
	 * 当没有声明collection时,自数据库查询dmcode
	 * 如声明collection则使用提供的值
	 */
	protected Object collection;         //2
	protected Object collectionLocal;
	
	/**
	 * 表名
	 * 查询条件
	 * 排序
	 */
	protected String table;
	protected String where;
	protected String orderBy;
	protected String key;

	

	/**
	 * 代码表 PUB_DICTIONARY
	 */
	protected String supType;

	/**
	 * 除数据库中 编码外,另添加的 外部 编码 格式:编码|名称;;;编码|名称 位置:开始位置
	 */
	protected String preOptions;

	/**
	 * 除数据库中 编码外,可以添加的编码 格式:编码|名称;;;编码|名称 位置:结束位置
	 */
	protected String lastOptions;

	/**
	 * 选中值
	 */
	protected String selected;

	/**
	 * 指定对象的哪个属性用于显示,哪个属性用于取值 对应<option value="valueItem">displayItem</option>
	 */
	protected String displayItem = "dicName";

	protected String valueItem = "dicCode";

	protected String optionKey = "id";

	/**
	 * 页面pageContext中临时保存属性名和属性值,只在字典表和通过collection传入集合的情况做了处理,其他情况忽略了该属性,仅仅保存被选中值
	 */ 
	protected String var;

	protected String varProperty;

	protected boolean useKey = false;

	protected String defaultValue;

	/**
	 * 以下属性和HTML的属性相同 其中className对应class属性 event对应事件,原样输出
	 */
	protected String name;

	protected String id;

	protected String className;

	protected String style;

	protected String event;

	protected boolean disabled = false;

	/**
	 * 构造函数
	 */
	public CodeBaseTag() {
		super();
	}

	/**
	 * @return Returns the optionKey.
	 */
	public String getOptionKey() {
		return optionKey;
	}

	/**
	 * @param optionKey
	 *            The optionKey to set.
	 */
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}

	/**
	 * @return Returns the useKey.
	 */
	public boolean isUseKey() {
		return useKey;
	}

	/**
	 * @param useKey
	 *            The useKey to set.
	 */
	public void setUseKey(boolean useKey) {
		this.useKey = useKey;
	}

	public void setUseKey(String useKey) {
		try {
			this.useKey = Boolean.getBoolean(useKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the lastOptions.
	 */
	public String getLastOptions() {
		return lastOptions;
	}

	/**
	 * @param lastOptions
	 *            The lastOptions to set.
	 * @throws JspException
	 */
	public void setLastOptions(String lastOptions) throws JspException {
		this.lastOptions = lastOptions;
	}

	/**
	 * @return Returns the preOptions.
	 */
	public String getPreOptions() {
		return preOptions;
	}

	/**
	 * @param preOptions
	 *            The preOptions to set.
	 * @throws JspException
	 */
	public void setPreOptions(String preOptions) throws JspException {
		this.preOptions = preOptions;
	}

	/**
	 * @return Returns the className.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            The className to set.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return Returns the disabled.
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * @param disabled
	 *            The disabled to set.
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * @return Returns the event.
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            The event to set.
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
			this.name = name;
	}

	/**
	 * @return Returns the selected.
	 */
	public String getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            The selected to set.
	 * @throws JspException
	 */
	public void setSelected(String selected) throws JspException {
		this.selected = selected;
	}

	/**
	 * @return Returns the style.
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            The style to set.
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return Returns the displayItem.
	 */
	public String getDisplayItem() {
		return displayItem;
	}

	/**
	 * @param displayItem
	 *            The displayItem to set.
	 */
	public void setDisplayItem(String displayItem) {
		this.displayItem = displayItem;
	}

	/**
	 * @return Returns the valueItem.
	 */
	public String getValueItem() {
		return valueItem;
	}

	/**
	 * @param valueItem
	 *            The valueItem to set.
	 */
	public void setValueItem(String valueItem) {
		this.valueItem = valueItem;
	}


	/**
	 * @return Returns the defaultValue.
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            The defaultValue to set.
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return Returns the supType.
	 */
	public String getSupType() {
		return supType;
	}

	/**
	 * @param supType
	 *            The supType to set.
	 * @throws JspException
	 */
	public void setSupType(String supType) throws JspException {
		if (supType == null) {
			this.supType = "-100";
		} else {
			this.supType = supType;
		}

	}

	/**
	 * @return Returns the var.
	 */
	public String getVar() {
		return var;
	}

	/**
	 * @param var
	 *            The var to set.
	 */
	public void setVar(String var) {
		this.var = var;
	}

	/**
	 * @return Returns the varProperty.
	 */
	public String getVarProperty() {
		return varProperty;
	}

	/**
	 * @param varProperty
	 *            The varProperty to set.
	 */
	public void setVarProperty(String varProperty) {
		this.varProperty = varProperty;
	}

	/**
	 * @return the codeBean
	 */
	public String getCodeBean() {
		return codeBean;
	}

	/**
	 * @param codeBean
	 *            the codeBean to set
	 */
	public void setCodeBean(String codeBean) {
		this.codeBean = codeBean;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	/**
	 * @return Returns the collection.
	 */
	public Object getCollection() {
		return collection;
	}
	
	

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * @return the where
	 */
	public String getWhere() {
		return where;
	}

	/**
	 * @param where the where to set
	 */
	public void setWhere(String where) {
		this.where = where;
	}
	
	

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @param collection The collection to set.
	 * @throws JspException 
	 */
	public void setCollection(Object collection) throws JspException {
		this.collection = collection;
	}

	

	public int doEndTag() {
		return EVAL_PAGE;
	}

	/**
	 * ************************************ 私有方法************************************
	 */

	/**
	 * 
	 * 功能描述:查询编码 同一页面,首次自数据库查询 其他会从pagecontext中查询
	 * 
	 * @author zhangzhf 2006-8-22
	 * @return
	 * @throws Exception
	 */
	protected Map getDeCodeMap() throws JspException {
		if ("-100".equals(this.supType)) {
			return new HashMap();
		} else if("-99999".equals(this.supType)){
			try {
				Object obj = WebApplicationContextUtil.getBean(this.codeBean);
				Method method = obj.getClass().getMethod(this.method,
						new Class[] { String.class });
				Map codeMap = (Map) method
						.invoke(obj, new Object[] { null });
				if (codeMap == null) {
					return new HashMap();
				}
				return codeMap;
			} catch (Exception e) {
				throw new JspException(e);
			}
		}
		if (this.supType != null && this.supType.length() > 0) {
			try {
				Object obj = WebApplicationContextUtil.getBean(this.codeBean);
				Method method = obj.getClass().getMethod(this.method,
						new Class[] { String.class });
				Map codeMap = (Map) method
						.invoke(obj, new Object[] { supType });
				if (codeMap == null) {
					return new HashMap();
				}
				return codeMap;
			} catch (Exception e) {
				throw new JspException(e);
			}
		}

		return null;

	}
	
	protected List getDataFromTable() throws JspException{
		List list = null;
		if(this.key!=null&&this.key.length()>0){
			list = (List)this.pageContext.getAttribute(this.key+"_LIST");
			if(list!=null) return list;
		}		
		if (table != null && table.length() > 0) {

			list = FetchData.getData(table, valueItem, displayItem, where,
					orderBy);
			if(StringHelper.isNotEmpty(this.key))
				this.pageContext.setAttribute(this.key+"_LIST",list);
		}
		return list;
	}
	
	protected Object getCode() throws JspException {
		if(this.collection!=null){
			return this.collectionLocal;
		}else if(StringHelper.isNotEmpty(this.table)){

			return getDataFromTable();
		}else{
			return getDeCodeMap();
		}
	}

		
		
	 protected Code2Name[] genCodeFromOptions(String optionsStr) {
		if (optionsStr == null || optionsStr.length() == 0) {
			return new Code2Name[0];
		}
		String[] options = optionsStr.split(";;;");
		Code2Name[] codes = new Code2Name[options.length];
		for (int i = 0; i < options.length; i++) {
			Code2Name d = new Code2Name();
			String[] codeAndName = options[i].split("\\|");
			d.setCode(codeAndName[0]);
			if (codeAndName.length == 2) {
				d.setName(codeAndName[1]);
			}
			codes[i] = d;
		}
		return codes;
	}
	//	
	// private void writeOption(JspWriter out,Code2Name code) throws
	// JspException{
	// try {
	// out.print("<option value=\"");
	// out.print(code.getCode());
	// out.print("\" ");
	// if(this.selected!=null&&this.selected.equalsIgnoreCase(code.getCode())){
	// out.print(" selected ");
	// }
	// out.print(">");
	// out.print(code.getName());
	// out.println("</option>");
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// }
	// }
	//	
	// private void writeOptionWithKey(JspWriter out,Code2Name code,String key)
	// throws JspException{
	// try {
	// out.print("<option value=\"");
	// out.print(code.getCode());
	// out.print("\" ");
	// out.print(" key=\"");
	// out.print(key==null?"":key);
	// out.print("\" ");
	// if(this.selected!=null&&this.selected.equalsIgnoreCase(code.getCode())){
	// out.print(" selected ");
	// }
	// out.print(">");
	// out.print(code.getName());
	// out.println("</option>");
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// }
	// }
	//	
	// private void writeOption(JspWriter out,Object obj) throws JspException{
	// if(obj instanceof Code2Name){
	// writeOption(out,(Code2Name)obj);
	// return;
	// }
	// try {
	// String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
	// String value = BeanUtil.getPropertiesValue(obj, this.valueItem);
	// out.print("<option value=\"");
	// out.print(value);
	// out.print("\" ");
	//			
	// if(this.selected!=null&&this.selected.equalsIgnoreCase(value)){
	// out.print(" selected ");
	// if(this.var!=null&&this.varProperty!=null&&this.var.length()>0&&this.varProperty.length()>0){
	// Object varValue = BeanUtil.getPropertiesValue(obj,this.varProperty);
	// this.pageContext.setAttribute(this.var,varValue);
	// }
	// }
	// out.print(">");
	// out.print(dis);
	// out.println("</option>");
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (IllegalAccessException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (InvocationTargetException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (NoSuchMethodException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// }
	// }
	//	
	// private void writeOptionWithKey(JspWriter out,Object obj) throws
	// JspException{
	// if(obj instanceof Code2Name){
	// writeOption(out,(Code2Name)obj);
	// return;
	// }
	// try {
	// String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
	// String value = BeanUtil.getPropertiesValue(obj, this.valueItem);
	// String key = BeanUtil.getPropertiesValue(obj, this.optionKey);
	// out.print("<option value=\"");
	// out.print(value);
	// out.print("\" ");
	// if(this.isUseKey()){
	// out.print(" key=\"");
	// out.print(key==null?"":key);
	// out.print("\" ");
	// }
	// if(this.selected!=null&&this.selected.equalsIgnoreCase(value)){
	// out.print(" selected ");
	// if(this.var!=null&&this.varProperty!=null&&this.var.length()>0&&this.varProperty.length()>0){
	// Object varValue = BeanUtil.getPropertiesValue(obj,this.varProperty);
	// this.pageContext.setAttribute(this.var,varValue);
	// }
	// }
	// out.print(">");
	// out.print(dis);
	// out.println("</option>");
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (IllegalAccessException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (InvocationTargetException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (NoSuchMethodException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// }
	// }
	//	
	// private boolean writeText(JspWriter out,Code2Name code,Map selectedMap)
	// throws JspException{
	// try {
	// if(code==null) return false;
	// if(selectedMap!=null&&selectedMap.containsKey(code.getCode())){
	// out.print(" ");
	// out.print(code.getName());
	// if(this.var!=null&&this.varProperty!=null&&this.var.length()>0&&this.varProperty.length()>0){
	// Object varValue = BeanUtil.getPropertiesValue(code,this.varProperty);
	// this.pageContext.setAttribute(this.var,varValue);
	// }
	// return true;
	// }
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (IllegalAccessException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (InvocationTargetException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (NoSuchMethodException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// }
	// return false;
	// }
	//	
	// private boolean writeText(JspWriter out,Object obj, Map selectedMap)
	// throws JspException{
	//		
	// if(obj instanceof Code2Name){
	// return writeText(out,(Code2Name)obj,selectedMap);
	// }
	//		
	// try {
	// if(obj==null) return false;
	// String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
	// String value = BeanUtil.getPropertiesValue(obj, this.valueItem);
	//			
	// if(selectedMap!=null&&selectedMap.containsKey(value)){
	// out.print(" ");
	// out.print(dis);
	// if(this.var!=null&&this.varProperty!=null&&this.var.length()>0&&this.varProperty.length()>0){
	// Object varValue = BeanUtil.getPropertiesValue(obj,this.varProperty);
	// this.pageContext.setAttribute(this.var,varValue);
	// }
	// return true;
	// }
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (IllegalAccessException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (InvocationTargetException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (NoSuchMethodException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// }
	// return false;
	// }
	//	
	// private void writeRadio(JspWriter out,Code2Name code, String name, String
	// id, String className, String style, String event, boolean disabled)
	// throws JspException{
	// try {
	// out.print("<input type=\"radio\" value=\"");
	// out.print(code.getCode());
	// out.print("\" ");
	// if (name != null && name.length() > 0)
	// out.print(" name=\"" + name + "\""+" id=\"" + name + "\"");
	// if (id != null && id.length() > 0)
	// out.print(" id=\"" + id + "\"");
	// if (className != null && className.length() > 0) {
	// out.print(" class=\"" + className + "\"");
	// }
	// if (style != null && style.length() > 0)
	// out.print(" style=\"" + style + "\"");
	// if (event != null && event.length() > 0)
	// out.print(" " + event + " ");
	// if(this.selected!=null&&this.selected.equalsIgnoreCase(code.getCode())){
	// out.print(" checked ");
	// }
	// out.print(">");
	// out.print(code.getName());
	// // out.println("</option>");
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// }
	// }
	//	
	// private void writeRadio(JspWriter out,Object obj, String name, String id,
	// String className, String style, String event, boolean disabled) throws
	// JspException{
	// if(obj instanceof Code2Name){
	// writeOption(out,(Code2Name)obj);
	// return;
	// }
	// try {
	// String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
	// String value = BeanUtil.getPropertiesValue(obj, this.valueItem);
	// out.print("<input type=\"radio\" value=\"");
	// out.print(value);
	// out.print("\" ");
	// if (name != null && name.length() > 0)
	// out.print(" name=\"" + name + "\""+" id=\"" + name + "\"");
	// if (id != null && id.length() > 0)
	// out.print(" id=\"" + id + "\"");
	// if (className != null && className.length() > 0) {
	// out.print(" class=\"" + className + "\"");
	// }
	// if (style != null && style.length() > 0)
	// out.print(" style=\"" + style + "\"");
	// if (event != null && event.length() > 0)
	// out.print(" " + event + " ");
	// if(this.selected!=null&&this.selected.equalsIgnoreCase(value)){
	// out.print(" checked ");
	// if(this.var!=null&&this.varProperty!=null&&this.var.length()>0&&this.varProperty.length()>0){
	// Object varValue = BeanUtil.getPropertiesValue(obj,this.varProperty);
	// this.pageContext.setAttribute(this.var,varValue);
	// }
	// }
	// out.print(">");
	// out.print(dis);
	//			
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (IllegalAccessException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (InvocationTargetException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// } catch (NoSuchMethodException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// }
	// }
	//	
	// private void writeCheckbox(JspWriter out,Code2Name code, String name,
	// String id, String className, String style, String event, boolean
	// disabled, String[] selectedValues) throws JspException{
	// try {
	// out.print("<input type=\"checkbox\" value=\"");
	// out.print(code.getCode());
	// out.print("\" ");
	// if (name != null && name.length() > 0)
	// out.print(" name=\"" + name + "\""+" id=\"" + name + "\"");
	// if (id != null && id.length() > 0)
	// out.print(" id=\"" + id + "\"");
	// if (className != null && className.length() > 0) {
	// out.print(" class=\"" + className + "\"");
	// }
	// if (style != null && style.length() > 0)
	// out.print(" style=\"" + style + "\"");
	// if (event != null && event.length() > 0)
	// out.print(" " + event + " ");
	// /*if(this.selected!=null&&this.selected.equalsIgnoreCase(code.getCode())){
	// out.print(" checked ");
	// }*/
	// if(selectedValues != null && selectedValues.length > 0){
	// for(int i = 0; i < selectedValues.length; i++){
	// if(selectedValues[i].equalsIgnoreCase(code.getCode())){
	// out.print(" checked ");break;
	// }
	// }
	// }
	//			
	// out.print(">");
	// out.print(code.getName());
	// // out.println("</option>");
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	// }
	// }
	//	
	// private void writeCheckbox(JspWriter out,Object obj, String name, String
	// id, String className, String style, String event, boolean disabled,
	// String[] selectedValues) throws JspException{
	// if(obj instanceof Code2Name){
	// writeOption(out,(Code2Name)obj);
	// return;
	// }
	// try {
	// String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
	// String value = BeanUtil.getPropertiesValue(obj, this.valueItem);
	// out.print("<input type=\"checkbox\" value=\"");
	// out.print(value);
	// out.print("\" ");
	// if (name != null && name.length() > 0)
	// out.print(" name=\"" + name + "\""+" id=\"" + name + "\"");
	// if (id != null && id.length() > 0)
	// out.print(" id=\"" + id + "\"");
	// if (className != null && className.length() > 0) {
	// out.print(" class=\"" + className + "\"");
	// }
	// if (style != null && style.length() > 0)
	// out.print(" style=\"" + style + "\"");
	// if (event != null && event.length() > 0)
	// out.print(" " + event + " ");
	// /*if(this.selected!=null&&this.selected.equalsIgnoreCase(value)){
	// out.print(" checked ");
	// if(this.var!=null&&this.varProperty!=null&&this.var.length()>0&&this.varProperty.length()>0){
	// Object varValue = BeanUtil.getPropertiesValue(obj,this.varProperty);
	// this.pageContext.setAttribute(this.var,varValue);
	// }
	// }*/
	//			
	// if(selectedValues != null && selectedValues.length > 0){
	// for(int i = 0; i < selectedValues.length; i++){
	// if(selectedValues[i].equalsIgnoreCase(value)){
	// out.print(" checked ");break;
	// }
	// }
	// if(this.var!=null&&this.varProperty!=null&&this.var.length()>0&&this.varProperty.length()>0){
	// Object varValue = BeanUtil.getPropertiesValue(obj,this.varProperty);
	// this.pageContext.setAttribute(this.var,varValue);
	// }
	// }
	// out.print(">");
	// out.print(dis);
	//			
	// } catch (IOException e) {
	// throw new JspException("生成标签时发生错误!"+e.getMessage());
	//		} catch (IllegalAccessException e) {
	//			throw new JspException("生成标签时发生错误!"+e.getMessage());
	//		} catch (InvocationTargetException e) {
	//			throw new JspException("生成标签时发生错误!"+e.getMessage());
	//		} catch (NoSuchMethodException e) {
	//			throw new JspException("生成标签时发生错误!"+e.getMessage());
	//		}
	//	}

}
