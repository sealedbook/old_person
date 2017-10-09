package com.esite.framework.util;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 *<PRE>
 * 功能描述:查询条件封装类
 * 修改历史:
 * -----------------------------------------------------------------------------
 * 		VERSION		DATE		BY			CHANGE/COMMENT
 * -----------------------------------------------------------------------------
 * 		1.0			2010-6-6	zhangzf		create
 * -----------------------------------------------------------------------------
 * </PRE>
 */
public class Criteria {

	
	private Object itemA;
	private String relation;
	private Object itemB;
	
	private String orderBy;
	
	public Criteria() {
		super();
		this.itemA = null;
		this.itemB = null;
		this.relation = null;
	}
	
	public Criteria(Object itemA,String relation,Object itemB) {
		super();
		this.itemA = itemA;
		this.itemB = itemB instanceof String?(StringHelper.isEmpty((String)itemB)?null:itemB):itemB;
		this.relation = relation;
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

	public String getItemAString(){
		if(itemA instanceof Criteria && !((Criteria)itemA).isSimple())
			return "("+itemA.toString()+")";
		else
			return itemA.toString();
	}
	
	public String getItemAString(String prefix) {
		if (itemB instanceof Criteria) {
			if (!((Criteria) itemA).isSimple())
				return "(" + ((Criteria) itemA).toString(prefix) + ")";
			else
				return ((Criteria) itemA).toString(prefix);
		} else
			return "1".equals(itemA)?"1":prefix+"."+itemA.toString();
	}
	
	public String getItemBString() {
		if (itemB instanceof Criteria) {
			if (!((Criteria) itemB).isSimple())
				return "(" + itemB.toString() + ")";
			else
				return itemB.toString();
		} else
			return "?";
	}
	
	public String getItemBString(String prefix) {
		if (itemB instanceof Criteria) {
			if (!((Criteria) itemB).isSimple())
				return "(" + ((Criteria) itemB).toString(prefix) + ")";
			else
				return ((Criteria) itemB).toString(prefix);
		} else
			return "?";
	}
	
	
	public boolean isSimple(){
		if(itemA instanceof Criteria || itemB instanceof Criteria){
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	public String toString(){
		if("is".equalsIgnoreCase(relation)&&(itemB==null||"null".equals(itemB))){
			return getItemAString() + " IS NULL ";
		}
		if("is not".equalsIgnoreCase(relation)&&(itemB==null||"null".equals(itemB))){
			return getItemAString() + " IS NOT NULL ";
		}
		if("in".equalsIgnoreCase(relation)){
			return getItemAString() + " " + relation + " " +this.itemB;
		}
		if(itemA==null) {
			throw new NullPointerException("条件中itemA为空.");
		}
		if(itemB==null) {
			throw new NullPointerException("条件中itemB为空.");
		}
		if(relation==null) {
			throw new NullPointerException("条件中关系relation为空.");
		}
		return getItemAString() + " " + relation + " " + getItemBString();
	}
	
	public String toString(String prefix){
		if("is".equalsIgnoreCase(relation)&&(itemB==null||"null".equals(itemB))){
			return getItemAString(prefix) + " IS NULL ";
		}
		if("is not".equalsIgnoreCase(relation)&&(itemB==null||"null".equals(itemB))){
			return getItemAString(prefix) + " IS NOT NULL ";
		}
		if("in".equalsIgnoreCase(relation)){
			return getItemAString(prefix) + " " + relation + " " +this.itemB;
		}
		if(itemA==null) {
			throw new NullPointerException("条件中itemA为空.");
		}
		if(itemB==null) {
			throw new NullPointerException("条件中itemB为空.");
		}
		if(relation==null) {
			throw new NullPointerException("条件中关系relation为空.");
		}
		return getItemAString(prefix) + " " + relation + " " + getItemBString(prefix);
	}
	/**
	 * 
	 * @return
	 */
	public List getValueArray(){
		List list = new ArrayList();
		if("is".equalsIgnoreCase(relation)&&(itemB==null||"null".equals(itemB))){
			return list;
		}
		if("is not".equalsIgnoreCase(relation)&&(itemB==null||"null".equals(itemB))){
			return list;
		}
		if("in".equalsIgnoreCase(relation)){
			return list;
		}
		if(itemA==null||itemB==null||relation==null) return list;
		if(itemA instanceof Criteria){
			list.addAll(((Criteria)itemA).getValueArray());
		}
		if(itemB instanceof Criteria){
			list.addAll(((Criteria)itemB).getValueArray());
		}else{
			list.add(itemB);
		}
		return list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Criteria a = new Criteria("a",">","1");
//		Criteria b = new Criteria("b",">","2");
//		Criteria ab = new Criteria(a,"and",b);
//		Criteria c = new Criteria("c","<",new java.util.Date());
//		Criteria abc = new Criteria(ab,"and",c);
//		List l = abc.getValueArray();
//		System.out.println(abc.toString());
//		System.out.println(abc.toString("t"));
//		System.out.println(new Criteria().toString());
		Criteria a = new Criteria("a","in","('1','2','3')");
		System.out.println(a.toString());
		Criteria a1 = new Criteria("a","is not","null");
		System.out.println(a1.toString());
	}

}
