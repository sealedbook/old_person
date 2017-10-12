/*
 * Created on 2004-12-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.esite.framework.core.jdbc;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.esite.framework.core.exception.BaseException;

/**
 * @author zhangzf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
  public  class ResultSetHelper {
	private Logger logger = Logger.getLogger(ResultSetHelper.class);
	private static ResultSetHelper helper = null;
	private ResultSetHelper(){}
	
	public static ResultSetHelper newInstance()
	{
		if(helper==null)
			helper = new ResultSetHelper();
		return helper;
	}
	
	public String[] getMetaData(ResultSet rs)
	{
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			List list = new ArrayList();
			for(int i=1;i<=count;i++)
			{
				list.add(rsmd.getColumnName(i).toUpperCase());
			}
			if(list.size()==0)
				return null;
			String[] result = new String[list.size()];
			list.toArray(result);
			return result;
		} catch (SQLException e) {
			logger.error("读取查询结果列名时发生错误,原因:"+e);
			throw new BaseException("读取查询结果列名时发生错误,原因:"+e);
		}
	}
	public List getList(ResultSet rs)
	{
		try {
			List rows = new ArrayList();
			String[] itemsName = this.getMetaData(rs);
			while(rs.next())
			{
				Map row = new HashMap();
				//copyProperties(row,itemsName,rs);
				for(int i=0;i<itemsName.length;i++)
				{
					row.put(itemsName[i].replaceAll("_", "").toUpperCase(),getResultSetValue(rs,i+1));
				}
				rows.add(row);
			}
			return rows;
		} catch (SQLException e) {
			logger.error("读取查询结果时发生错误,原因:"+e);
			throw new BaseException("读取查询结果时发生错误,原因:"+e);
		} 
	}
	
	public Object getResultSetValue(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		if (obj instanceof Blob) {
			obj = rs.getBytes(index);
		}
		else if (obj instanceof Clob) {
			obj = rs.getString(index);
		}
		else if (className != null &&
				("oracle.sql.TIMESTAMP".equals(className) ||
				"oracle.sql.TIMESTAMPTZ".equals(className))) {
			obj = rs.getTimestamp(index);
		}
		else if (className != null && className.startsWith("oracle.sql.DATE")) {
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) ||
					"oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
				obj = rs.getTimestamp(index);
			}
			else {
				obj = rs.getDate(index);
			}
		}
		else if (obj != null && obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		}
		return obj;
	}
	
}
