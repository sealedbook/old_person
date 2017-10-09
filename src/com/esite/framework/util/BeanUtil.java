/*
 * Created on 2005-1-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.esite.framework.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/**
 * @author zhangzf
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BeanUtil {

	private Logger logger = Logger.getLogger(BeanUtil.class);

	private BeanUtil() {
	}

	private static BeanUtil instance;

	public static BeanUtil newInstance() {
		if (instance == null)
			instance = new BeanUtil();
		return instance;
	}

//	/**
//	 * 属性拷贝
//	 * 
//	 * @param dest
//	 * @param orig
//	 * @throws NoSuchMethodException
//	 * @throws InvocationTargetException
//	 * @throws IllegalAccessException
//	 */
//	public void copyProperties(Object dest, Object orig)
//			throws NoSuchMethodException, IllegalAccessException,
//			InvocationTargetException {
//		if (dest == null) {
//			throw new IllegalArgumentException("No destination bean specified");
//		}
//		if (orig == null) {
//			throw new IllegalArgumentException("No origin bean specified");
//		}
//		if (dest instanceof Row && orig instanceof Row) {
//			copyProperties((Row) dest, (Row) orig);
//		} else if (!(dest instanceof Row) && orig instanceof Row) {
//			copyProperties(dest, (Row) orig);
//		} else if (dest instanceof Row && !(orig instanceof Row)) {
//			copyProperties((Row) dest, orig);
//		} else {
//			BeanUtils.copyProperties(dest, orig);
//		}
//	}
//
//	private void copyProperties(Object dest, Row orig)
//			throws IllegalAccessException, InvocationTargetException {
//		PropertyDescriptor destDescriptors[] = PropertyUtils
//				.getPropertyDescriptors(dest);
//		for (int i = 0; i < destDescriptors.length; i++) {
//			String name = destDescriptors[i].getName();
//			if ("class".equals(name)) {
//				continue; // No point in trying to set an object's class
//			}
//			if (PropertyUtils.isReadable(dest, name)
//					&& PropertyUtils.isWriteable(dest, name)
//					&& ((Row) orig).isContainsItem(name)) {
//				Object value = ((Row) orig).get(name);
//				BeanUtils.copyProperty(dest, name, value);
//			}
//		}
//	}
//
//	private void copyProperties(Row dest, Object orig)
//			throws IllegalAccessException, InvocationTargetException,
//			NoSuchMethodException {
//		PropertyDescriptor origDescriptors[] = PropertyUtils
//				.getPropertyDescriptors(orig);
//		for (int i = 0; i < origDescriptors.length; i++) {
//			String name = origDescriptors[i].getName();
//			if ("class".equals(name)) {
//				continue; // No point in trying to set an object's class
//			}
//			if (PropertyUtils.isReadable(orig, name)
//					&& PropertyUtils.isWriteable(orig, name)) {
//				Object value = PropertyUtils.getSimpleProperty(orig, name);
//				//是否需要类型转换?待定
//				dest.put(name, value);
//			}
//		}
//	}
//
//	private void copyProperties(Row dest, Row orig) {
//		String[] items = orig.getItems();
//		if (items != null) {
//			for (int i = 0; i < items.length; i++)
//				dest.put(items[i], orig.get(items[i]));
//		}
//	}

	/**
	 * 比较obj1和obj2的属性值，返回obj1与obj2都有但是值不相同的属性集
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public List compare(Object obj1, Object obj2) {
		List list = new ArrayList();
		Map temp = new HashMap();
		try {
			copyProperties(temp, obj1);
			String[] items = new String[temp.keySet().size()];
			temp.keySet().toArray(items);
			for (int i = 0; i < items.length; i++) {
				Object obj1_item_value = temp.get(items[i]);
				Object obj2_item_value = null;
				if (PropertyUtils.isReadable(obj2, items[i])
						&& PropertyUtils.isWriteable(obj2, items[i])) {
					obj2_item_value = PropertyUtils.getProperty(obj2, items[i]);
					if (obj1_item_value == null) {
						if (obj2_item_value != null) {
							CompareResultBean cmp = new CompareResultBean();
							cmp.setItemName(items[i]);
							cmp.setCurrValue(obj2_item_value);
							cmp.setOrigValue(obj1_item_value);
							list.add(cmp);
						}
					} else {
						if (!obj1_item_value.equals(obj2_item_value)) {
							CompareResultBean cmp = new CompareResultBean();
							cmp.setItemName(items[i]);
							cmp.setCurrValue(obj2_item_value);
							cmp.setOrigValue(obj1_item_value);
							list.add(cmp);
						}
					}
				}

			}
		} catch (IllegalAccessException e) {
			logger.error("error is :" + e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error("error is :" + e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.error("error is :" + e.getMessage());
		}
		return list;
	}

	/**
	 * 
	 * 功能描述:copy属性到Map中
	 * @author zhangzhf
	 * 2006-8-17
	 * @param dest
	 * @param orig
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void copyProperties(Map dest, Object orig)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		PropertyDescriptor origDescriptors[] = PropertyUtils
				.getPropertyDescriptors(orig);
		for (int i = 0; i < origDescriptors.length; i++) {
			/** *******主要是过滤索引属性************** */
			if (origDescriptors[i].getReadMethod() == null)
				continue;
			String name = origDescriptors[i].getName();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (PropertyUtils.isReadable(orig, name)
					&& PropertyUtils.isWriteable(orig, name)) {
				Object value = PropertyUtils.getSimpleProperty(orig, name);
				//是否需要类型转换?待定
				dest.put(name, value);
			}
		}
	}

	/**
	 * 根据bean属性生成查询条件字符串whereclause;该字符串中仅仅包含不为空的值
	 * 限制:目前只能处理字符串/数值等属性,其他的属性不能正确的转成数据库中的值
	 * 
	 * @param orig
	 * @return
	 */
	public static String getWhereClauseString(Object orig) {
		StringBuffer buffer = new StringBuffer();
		boolean flag = false;
		PropertyDescriptor origDescriptors[] = PropertyUtils
				.getPropertyDescriptors(orig);
		for (int i = 0; i < origDescriptors.length; i++) {
			/** *******主要是过滤索引属性************** */
			if (origDescriptors[i].getReadMethod() == null)
				continue;
			String name = origDescriptors[i].getName();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (PropertyUtils.isReadable(orig, name)
					&& PropertyUtils.isWriteable(orig, name)) {
				try {
					Object value = PropertyUtils.getSimpleProperty(orig, name);
					if (value == null)
						continue;
					if (isValidString(String.valueOf(value))) {
						if (flag) {
							buffer.append(" AND ");
						}
						buffer.append(name);
						buffer.append(" = '");
						buffer.append(value);
						buffer.append("'");
						flag = true;
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * 功能描述:根据属性,生成页面查询条件
	 * 例如 &a=1&b=2&c=3
	 * @author zhangzhf
	 * @param orig
	 * @param pre
	 * @return
	 */
	public static String getQueryString(Object orig,String pre){
		return getQueryString(orig,pre,"&");
	}
	
	public static String getQueryString(Object orig,String pre,String orp) {
		StringBuffer buffer = new StringBuffer();
		boolean flag = false;
		PropertyDescriptor origDescriptors[] = PropertyUtils
				.getPropertyDescriptors(orig);
		for (int i = 0; i < origDescriptors.length; i++) {
			/** *******主要是过滤索引属性************** */
			if (origDescriptors[i].getReadMethod() == null)
				continue;
			String name = origDescriptors[i].getName();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (PropertyUtils.isReadable(orig, name)
					&& PropertyUtils.isWriteable(orig, name)) {
				try {
					Object value = PropertyUtils.getSimpleProperty(orig, name);
					if (value == null||value instanceof Object[] || value instanceof Collection)
						continue;
					if (isValidString(String.valueOf(value))) {
						if (flag) {
							buffer.append(orp);
						}
						buffer.append(pre+".");
						buffer.append(name);
						buffer.append("=");
						buffer.append(value);
						flag = true;
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}

	private static boolean isValidString(String obj) {
		return obj == null || obj.length() == 0 ? false : true;
	}
	
	
	public static String getPropertiesValue(Object orig, String property)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return (String)PropertyUtils.getProperty(orig, property);
	}
	
	/**
	 * 
	 * 功能描述:
	 * @author zhangzhf
	 * 2006-12-27
	 * @param dest
	 * @param orig
	 * @param escape,map中Key大写
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyProperties(Object dest,Object orig,Map escape) throws IllegalAccessException, InvocationTargetException{
//		 Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException
                    ("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (escape == null) {
            throw new IllegalArgumentException("No escape Map specified");
        }

        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty origDescriptors[] =
                ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if(escape.containsKey(name.toUpperCase())){
                	continue;
                }
                if (PropertyUtils.isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    BeanUtilsBean.getInstance().copyProperty(dest, name, value);
                }
            }
        } else if (orig instanceof Map) {
            Iterator names = ((Map) orig).keySet().iterator();
            while (names.hasNext()) {
                String name = (String) names.next();
                if(escape.containsKey(name.toUpperCase())){
                	continue;
                }
                if (PropertyUtils.isWriteable(dest, name)) {
                    Object value = ((Map) orig).get(name);
                    BeanUtilsBean.getInstance().copyProperty(dest, name, value);
                }
            }
        } else /* if (orig is a standard JavaBean) */ {
            PropertyDescriptor origDescriptors[] =
            	PropertyUtils.getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if(escape.containsKey(name.toUpperCase())){
                	continue;
                }
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (PropertyUtils.isReadable(orig, name) &&
                		PropertyUtils.isWriteable(dest, name)) {
                    try {
                        Object value =
                        	PropertyUtils.getSimpleProperty(orig, name);
                        BeanUtilsBean.getInstance().copyProperty(dest, name, value);
                    } catch (NoSuchMethodException e) {
                        ; // Should not happen
                    }
                }
            }
        }
	}

	//    public static void main(String args[]) {
	//        UserDTO dto = new UserDTO();
	//        LoginForm form = new LoginForm();
	//// try {
	//// BeanUtils.copyProperty(dto, "userName", "zzf");
	//// } catch (IllegalAccessException e) {
	//// // TODO Auto-generated catch block
	//// e.printStackTrace();
	//// } catch (InvocationTargetException e) {
	//// // TODO Auto-generated catch block
	//// e.printStackTrace();
	//// }
	//// System.out.println(dto.getUserName());
	//        dto.setEmail("email");
	//        dto.setIsInvalid("1");
	//        dto.setLastLoginTime("2005-03-23");
	//        dto.setPassword("zzfpassword");
	//        dto.setUserName("zzfname");
	//        form.setPassword("zjpassword");
	//        form.setUsername("zjname");
	//        List list = BeanUtil.newInstance().compare(form,dto);
	//        int i=0;
	//    }

}