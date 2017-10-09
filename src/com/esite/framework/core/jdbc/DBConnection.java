/*
 * Created on 2004-12-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.esite.framework.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.esite.framework.core.exception.CatchedException;
import com.esite.framework.core.factory.WebApplicationContextUtil;

/**
 * 
 *<PRE>
 * 功能描述:开发者在此描述类的主要功能或目的
 * 修改历史:
 * -----------------------------------------------------------------------------
 * 		VERSION		DATE		BY			CHANGE/COMMENT
 * -----------------------------------------------------------------------------
 * 		1.0			2010-6-6	zhangzf		create
 * -----------------------------------------------------------------------------
 * </PRE>
 */
public final class  DBConnection {
	private static Logger logger = Logger.getLogger(DBConnection.class);
	private static DataSource dataSource = null;
	
	private DBConnection() {
	}
	
	
	/**
	 *	初始化数据源
	 */
	private synchronized static DataSource init(String datasourcestr) {
		dataSource = (DataSource)WebApplicationContextUtil.getBean("dataPersistentSource");
		return dataSource;
	}
	
	/**
	 * 查找数据源
	 * @param datasourcestr
	 * @return
	 */
	private static DataSource getDS(String datasourcestr){
		DataSource ds = dataSource;
		if(ds==null){
			ds = init(datasourcestr);
		}
		return ds;
	}
	
	
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConnection(String datasourcestr) {
	    
	    Connection con = null;
        DataSource ds = getDS(datasourcestr);
        if(ds==null){
        	throw new CatchedException("不能连接到数据库,请与管理员联系或稍后再试。");
        }
        try {
			con = ds.getConnection();
		} catch (SQLException e) {
			logger.error("初始化数据源时发生错误,原因:\n" + e.getMessage());
			throw new CatchedException("不能连接到数据库,请与管理员联系或稍后再试。");
		}
        return con;
    }
}