package com.esite.framework.core.tags.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.esite.framework.core.factory.WebApplicationContextUtil;
import com.esite.framework.util.BeanUtil;

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
public class SelectTag extends TagSupport {

	private Logger logger = Logger.getLogger(SelectTag.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -3476436959425462492L;
	
	//取值select/text,默认select;指定输出方式是下拉框select还是文本
	private String type="select";
	
	/**
	 * dm_code表
	 * 父编码,这个是兼容专项而特意保留的
	 */
	private String parentCode;  //1
	
	/**
	 * 代码表 T_DICTIONARY
	 */
	private String supType;
	
	/**
	 * options 集合
	 * Collection(List Map等)
	 * 当没有声明collection时,自数据库查询dmcode
	 * 如声明collection则使用提供的值
	 */
	private String collection;         //2
	private Object collectionLocal;
	
	/**
	 * 表名
	 */
	private String table; //3
	//条件
	private String where;
	//排序
	private String order;
	/**
	 * 除数据库中 编码外,另添加的 外部 编码
	 * 格式:编码|名称;;;编码|名称
	 * 位置:开始位置
	 */
	private String preOptions;
	
	/**
	 * 除数据库中 编码外,可以添加的编码
	 * 格式:编码|名称;;;编码|名称
	 * 位置:结束位置
	 */
	private String lastOptions;
	
	/**
	 * 选中值
	 */
	private String selected;
	
	
	
	/**
	 * 指定对象的哪个属性用于显示,哪个属性用于取值
	 * 对应<option value="valueItem">displayItem</option>
	 */
	private String displayItem = "name";
	private String valueItem = "code";
	
	private String defaultValue;
	
	
	/**
	 * 以下属性和HTML的属性相同
	 * 其中className对应class属性
	 * event对应事件,原样输出
	 */
	private String name;
	private String id;
	private String className;
	private String style;
	private String event;
	private boolean disabled = false;

	/**
	 * 构造函数
	 */
	public SelectTag() {
		super();
	}

	/**
	 * @return Returns the parentCode.
	 */
	public String getParentCode() {
		return parentCode;
	}





	/**
	 * @param parentCode The parentCode to set.
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}


	


	/**
	 * @return Returns the lastOptions.
	 */
	public String getLastOptions() {
		return lastOptions;
	}





	/**
	 * @param lastOptions The lastOptions to set.
	 * @throws JspException 
	 */
	public void setLastOptions(String lastOptions) throws JspException {
//		this.lastOptions = lastOptions;
		try {
			this.lastOptions = String.valueOf(ExpressionEvaluatorManager.evaluate(
			        "lastOptions", lastOptions, Object.class, this,
			        pageContext));
		} catch (JspException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new JspException(e);
		}
	}





	/**
	 * @return Returns the preOptions.
	 */
	public String getPreOptions() {
		return preOptions;
	}





	/**
	 * @param preOptions The preOptions to set.
	 * @throws JspException 
	 */
	public void setPreOptions(String preOptions) throws JspException {
//		this.preOptions = preOptions;
		try {
			this.preOptions = String.valueOf(ExpressionEvaluatorManager.evaluate(
			        "preOptions", preOptions, Object.class, this,
			        pageContext));
		} catch (JspException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new JspException(e);
		}
	}



	
	
	


	/**
	 * @return Returns the collection.
	 */
	public String getCollection() {
		return collection;
	}

	/**
	 * @param collection The collection to set.
	 * @throws JspException 
	 */
	public void setCollection(String collection) throws JspException {
		this.collection = collection;
//		collectionLocal
		try {
			this.collectionLocal = ExpressionEvaluatorManager.evaluate(
			        "collection", collection, Object.class, this,
			        pageContext);
		} catch (JspException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JspException(e.getMessage());
		}
	}

	/**
	 * @return Returns the className.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className The className to set.
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
	 * @param disabled The disabled to set.
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
	 * @param event The event to set.
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
	 * @param id The id to set.
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
	 * @param name The name to set.
	 */
	public void setName(String name) {
		try {
			this.name = String.valueOf(ExpressionEvaluatorManager.evaluate(
			        "name", name, Object.class, this,
			        pageContext));
		} catch (JspException e) {
			logger.error(e);
			name = "no_name";
		}
		//this.name = name;
	}

	/**
	 * @return Returns the selected.
	 */
	public String getSelected() {
		return selected;
	}

	/**
	 * @param selected The selected to set.
	 * @throws JspException 
	 */
	public void setSelected(String selected) throws JspException {
		try {
			this.selected = String.valueOf(ExpressionEvaluatorManager.evaluate(
			        "selected", selected, Object.class, this,
			        pageContext));
		} catch (JspException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new JspException(e);
		}
	}

	/**
	 * @return Returns the style.
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style The style to set.
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
	 * @param displayItem The displayItem to set.
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
	 * @param valueItem The valueItem to set.
	 */
	public void setValueItem(String valueItem) {
		this.valueItem = valueItem;
	}
	
	

	/**
	 * @return Returns the table.
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table The table to set.
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * @return Returns the where.
	 */
	public String getWhere() {
		return where;
	}

	/**
	 * @param where The where to set.
	 */
	public void setWhere(String where) {
		try {
			this.where = String.valueOf(ExpressionEvaluatorManager.evaluate(
			        "where", where, Object.class, this,
			        pageContext));
		} catch (JspException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			where = " 1>2";
		}
	}
	
	

	/**
	 * @return Returns the order.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order The order to set.
	 */
	public void setOrder(String order) {
		this.order = order;
	}
	
	

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	

	/**
	 * @return Returns the defaultValue.
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue The defaultValue to set.
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
	 * @param supType The supType to set.
	 */
	public void setSupType(String supType) {
		this.supType = supType;
	}

	public int doStartTag() throws JspException{   
		JspWriter out = pageContext.getOut();
		
		Code2Name[] preCode = genCodeFromOptions(this.preOptions);
		Code2Name[] lastCode = genCodeFromOptions(this.lastOptions);
		Object con = getCode();
		if ("select".equalsIgnoreCase(type)) {
			try {
				out.print("<select ");
				if (name != null && name.length() > 0)
					out.print(" name=\"" + name + "\""+" id=\"" + name + "\"");
				if (id != null && id.length() > 0)
					out.print(" id=\"" + id + "\"");
				if (this.className != null && className.length() > 0) {
					out.print(" class=\"" + className + "\"");
				}
				if (style != null && style.length() > 0)
					out.print(" style=\"" + style + "\"");
				if (event != null && event.length() > 0)
					out.print(" " + event + " ");
				if (disabled == true)
					out.print(" disabled=\"" + disabled + "\"");
				out.println(">");
				if (preCode != null) {
					for (int i = 0; i < preCode.length; i++) {
						this.writeOption(out, preCode[i]);
					}
				}
				if(con instanceof Collection){
					Collection conLocal = (Collection)con;
					if (con != null && conLocal.size() > 0) {
						Iterator it = conLocal.iterator();
						while (it.hasNext()) {
							this.writeOption(out, it.next());
						}
					}
				}else if(con instanceof Map){
					Map conLocal = (Map)con;
					if (con != null && conLocal.size() > 0) {
						Iterator it = conLocal.values().iterator();
						while (it.hasNext()) {
							this.writeOption(out, it.next());
						}
					}
				}
				

				if (lastCode != null) {
					for (int i = 0; i < lastCode.length; i++) {
						this.writeOption(out, lastCode[i]);
					}
				}
				out.print("</select>");

			} catch (IOException e) {
				throw new JspException("生成标签时发生错误!" + e.getMessage());
			}
		} else {
			try {
				
				if (preCode != null) {
					for (int i = 0; i < preCode.length; i++) {
						if(this.writeText(out, preCode[i])) return SKIP_BODY;
					}
				}
				if(con instanceof Collection){
					Collection conLocal = (Collection)con;
					if (con != null && conLocal.size() > 0) {
						Iterator it = conLocal.iterator();
						while (it.hasNext()) {
							if(this.writeText(out, it.next())) return SKIP_BODY;
						}
					}
				}else if(con instanceof Map){
					Map conLocal = (Map)con;
					if (con != null && conLocal.size() > 0) {
						Iterator it = conLocal.values().iterator();
						while (it.hasNext()) {
							if(this.writeText(out, it.next())) return SKIP_BODY;
						}
					}
				}
				

				if (lastCode != null) {
					for (int i = 0; i < lastCode.length; i++) {
						if(this.writeText(out, lastCode[i])) return SKIP_BODY;
					}
				}
				out.print(this.defaultValue==null?"":this.defaultValue);

			} catch (Exception e) {
				throw new JspException("生成标签时发生错误!" + e.getMessage());
			}
			
		}
		
		return SKIP_BODY;
	}
	
	
	public int doEndTag(){
		return EVAL_PAGE;
	}
	
	/************************************** 私有方法 ************************************/

	/**
	 * 
	 * 功能描述:查询编码
	 * 同一页面,首次自数据库查询
	 * 其他会从pagecontext中查询
	 * @author zhangzhf
	 * 2006-8-22
	 * @return
	 * @throws Exception 
	 */
	private Collection getDeCodeList() throws JspException{
		List list = null;
		if(this.name!=null&&this.name.length()>0)
		list = (List)this.pageContext.getAttribute(this.name+"_LIST");
		if(list!=null) return list;
		if(this.supType!=null&&this.supType.length()>0){
			try {
				Object obj = WebApplicationContextUtil.getBean("codesService");
				Method method = obj.getClass().getMethod("queryChildren",new Class[]{String.class});
				Map codeMap =  (Map)method.invoke(obj,new Object[]{supType});
				return codeMap.values();
			} catch (Exception e) {
				throw new JspException(e);
			}
			
			//list = dmcodService.getDmCode(parentCode);
		}else if(this.parentCode!=null&&this.parentCode.length()>0){
			
			if(list==null){
//				DmCodeService dmcodService = (DmCodeService)ServiceFactory.getBean("dmcodeService");
//				list = dmcodService.getDmCode(parentCode);
				list = FetchData.getData("dm_code",valueItem,displayItem," parent_code = '"+parentCode+"'",order);
			}
		} else {
			if (table != null && table.length() > 0) {

				list = FetchData.getData(table, valueItem, displayItem, where,
						order);
			}
		}
		this.pageContext.setAttribute(this.name+"_LIST",list);
		return list;
		
	}
	
	private Object getCodeCollection(){
		return this.collectionLocal;
	}
	
	private Object getCode() throws JspException {
		if(this.collection==null||this.collection.length()==0){
			return getDeCodeList();
		}else{
			return getCodeCollection();
		}
	}
	
	private Code2Name[] genCodeFromOptions(String optionsStr){
		if(optionsStr==null||optionsStr.length()==0){
			return new Code2Name[0];
		}
		String[] options = optionsStr.split(";;;");
		Code2Name[] codes = new Code2Name[options.length];
		for(int i=0;i<options.length;i++){
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
	
	private void writeOption(JspWriter out,Code2Name code) throws JspException{
		try {
			out.print("<option value=\"");
			out.print(code.getCode());
			out.print("\" ");
			if(this.selected!=null&&this.selected.equalsIgnoreCase(code.getCode())){
				out.print(" selected ");
			}
			out.print(">");
			out.print(code.getName());
			out.println("</option>");
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		}
	}
	private void writeOption(JspWriter out,Object obj) throws JspException{
		if(obj instanceof Code2Name){
			writeOption(out,(Code2Name)obj);
			return;
		}
		try {
			String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
		    String value = BeanUtil.getPropertiesValue(obj, this.valueItem);
			out.print("<option value=\"");
			out.print(value);
			out.print("\" ");
			if(this.selected!=null&&this.selected.equalsIgnoreCase(value)){
				out.print(" selected ");
			}
			out.print(">");
			out.print(dis);
			out.println("</option>");
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		} catch (IllegalAccessException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		} catch (InvocationTargetException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		}
	}
	
	private boolean writeText(JspWriter out,Code2Name code) throws JspException{
		try {
			if(this.selected!=null&&this.selected.equalsIgnoreCase(code.getCode())){
				out.print(code.getName());
				return true;
			}
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		}
		return false;
	}
	
	private boolean writeText(JspWriter out,Object obj) throws JspException{
		
		if(obj instanceof Code2Name){
			return writeText(out,(Code2Name)obj);
		}
		
		try {
			String dis = BeanUtil.getPropertiesValue(obj, this.displayItem);
		    String value = BeanUtil.getPropertiesValue(obj, this.valueItem);
			
			if(this.selected!=null&&this.selected.equalsIgnoreCase(value)){
				out.print(dis);
				return true;
			}
		} catch (IOException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		} catch (IllegalAccessException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		} catch (InvocationTargetException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new JspException("生成标签时发生错误!"+e.getMessage());
		}
		return false;
	}
}
