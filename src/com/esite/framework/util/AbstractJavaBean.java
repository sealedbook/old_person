package com.esite.framework.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;

public abstract class AbstractJavaBean {
	
	/**
	 * 将对象中的属性拷贝到制定class中.<br/>
	 * 只有当两个类中属性名万全相同时才会进行属性值的拷贝.
	 * @param <T>
	 * @param clazz 目的对象
	 * @return 当正常拷贝后将返回目的对象的实例
	 */
	public <T> T copyPropertiesTo(Class<T> clazz) {
		T origObject = null;
		try {
			origObject = clazz.newInstance();
			BeanUtils.copyProperties(origObject,this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return origObject;
	}
	
	public String createInsertSql(String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append(tableName).append(" (ID");
		List<Property> propertyNameList = getProperty();
		for(Property property : propertyNameList) {
			if("ID".equals(property.getName().toUpperCase())) {
				continue;
			}
			sql.append(",").append(underscoreName(property.getName()));
		}
		sql.append(") values(:id");
		for(Property property : propertyNameList) {
			if("ID".equals(property.getName().toUpperCase())) {
				continue;
			}
			sql.append(",:").append(property.getName());
		}
		sql.append(")");
		return sql.toString();
	}
	
	/**
	 * 将对象中的属性和属性值以key和value的形式转换为Map对象.<br/>
	 * @return
	 */
	public Map<String,Object> toMap() {
		Map<String,Object> propertyValueMapping = null;
		List<Property> propertyList = getProperty();
		propertyValueMapping = new HashMap<String, Object>(propertyList.size());
		for(Property property : propertyList) {
			propertyValueMapping.put(underscoreName(property.getName()), property.getValue());
		}
		return propertyValueMapping;
	}
	
	public String toJson() {
		return "";
	}
	
	private List<Property> getProperty() {
		List<Property> propertyList = new ArrayList<Property>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
			PropertyDescriptor[] propertyDescriptorArray = beanInfo.getPropertyDescriptors();
			
			for(PropertyDescriptor p : propertyDescriptorArray) {
				Method readMethod = null;
				if(!"class".equals(p.getName()) && null != (readMethod = p.getReadMethod())) {
					String name = p.getName();
					Object value = readMethod.invoke(this);
					if(!objectIsEmpty(value)) {
						Property property = new Property(name,value);
						propertyList.add(property);
					}
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return propertyList;
	}
	
	private boolean objectIsEmpty(Object obj) {
		if(null == obj) return true;
		if(obj instanceof java.lang.String) {
			if(obj.toString().length() <= 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 将驼峰式变量的命名方式转换为用大写字母和下划线"_"表示.<br/>
	 * 例如:<br/>
	 * myName将转换为MY_NAME<br/>
	 * ThisIsMyName将转换为_THIS_IS_MY_NAME
	 * @param name
	 * @return
	 */
	private String underscoreName(String name) {
		if(null == name || name.length() <= 0) {
			return "";
		}
		StringBuffer n = new StringBuffer();
		for(char c : name.toCharArray()) {
			char upperChar = Character.toUpperCase(c);
			Pattern pattern = Pattern.compile("[A-Z]+");
			Matcher matcher = pattern.matcher(String.valueOf(upperChar));
			if(c == upperChar && matcher.find()) {
				n.append("_");
			}			
			n.append(upperChar);
		}
		return n.toString();
	}

}

class Property {
	public Property(String name,Object value) {
		this.name = name;
		this.value = value;
	}
	private String name;
	private Object value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
