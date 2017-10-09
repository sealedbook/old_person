package com.esite.framework.core.tags.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

import com.esite.framework.core.jdbc.DataAccess;

public class FetchData {

	private static Logger logger =  Logger.getLogger(FetchData.class);
	public static Map getDataMap(String table,String codeItem,String nameItem,String criteria,String order) throws Exception{
		Map map = new HashMap();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			stmt = conn.createStatement();
			String querysql = "select "+ codeItem+"," + nameItem+" from "+table;
			if(criteria!=null&&criteria.length()>0)
				querysql+=" where "+criteria;
			if(order!=null&&order.length()>0)
				querysql+=" order by "+order;
			rs = stmt.executeQuery(querysql);
			while (rs.next()) {
				String item1 = rs.getString(1);
				String item2 = rs.getString(2);
				map.put(item1,item2);
			}
		} catch (Exception e) {
			//throw new JspException(e);
			logger.error("Select标签,"+e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					System.err.println(sqle.getMessage());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqle) {
					System.err.println(sqle.getMessage());
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					close(conn);
				} catch (Exception sqle) {

				}
				conn = null;
			}

		}
		return map;
	}
	
	public static List getData(String table,String codeItem,String nameItem,String criteria,String order) throws JspException{
		List list = new ArrayList();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			stmt = conn.createStatement();
			String querysql = "select rtrim(ltrim("+ codeItem+")),rtrim(ltrim(" + nameItem+")) from "+table;
			if(criteria!=null&&criteria.length()>0)
				querysql+=" where "+criteria;
			if(order!=null&&order.length()>0)
				querysql+=" order by "+order;
			rs = stmt.executeQuery(querysql);
			while (rs.next()) {
				String item1 = rs.getString(1);
				String item2 = rs.getString(2);
				Code2Name cn = new Code2Name(item1,item2);
				list.add(cn);
			}
		} catch (Exception e) {
			logger.error("Select标签,"+e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					System.err.println(sqle.getMessage());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqle) {
					System.err.println(sqle.getMessage());
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					close(conn);
				} catch (Exception sqle) {

				}
				conn = null;
			}

		}
		return list;
		
	}
	
	private static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block

			}
		}
	}
	
	public static Connection getConn() throws Exception{
		return DataAccess.getConnection();
	}
	
}
