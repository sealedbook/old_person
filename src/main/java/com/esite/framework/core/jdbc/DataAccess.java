package com.esite.framework.core.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.esite.framework.core.config.ConfigHelper;

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
public class DataAccess {

  private static String dataSource = "java:comp/env/";
  private static Logger logger = Logger.getLogger(DataAccess.class);




  /**
   * @wfunction 建立数据库连接
   * @wreturn 数据库连接
   * @throws java.lang.Exception
   */
  public static Connection getConnection() throws Exception {
      return DBConnection.getConnection(dataSource+ConfigHelper.getString("com.esite.framework.core.db.datasource"));
  }

  public static void Close(Connection conn, PreparedStatement pstm,
                           ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
      if (pstm != null) {
        pstm.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {
    	logger.error(e.getMessage());
    }
  }

  public static void Close(Connection conn, PreparedStatement pstm) {
    try {
      if (pstm != null) {
        pstm.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {
    	logger.error("jkjkjk"+e.getMessage());
    }
  }

  public static void Close(Connection conn, CallableStatement cstm,
                           ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
      if (cstm != null) {
        cstm.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {
    	logger.error(e.getMessage());
    }
  }

  public static void Close(Connection conn, Statement stmt, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {
    	logger.error(e.getMessage());
    }
  }

  public static void Close(Connection conn, CallableStatement cstm) {
    try {
      if (cstm != null) {
        cstm.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {
    	logger.error(e.getMessage());
    }finally{
      //System.gc();
    }
  }

  public static void Close(Connection conn) {
    try {
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {
    	logger.error(e.getMessage());
    }finally{
      //System.gc();
    }
  }

  public static void Close(Connection conn, PreparedStatement pstm,
                           CallableStatement cstm, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
      if (pstm != null) {
        pstm.close();
      }
      if (cstm != null) {
        cstm.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {
    	logger.error(e.getMessage());
    }finally{
      //System.gc();
    }
  }
	  
  public static void Close(Connection conn, PreparedStatement pstm,
            Statement cstm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (cstm != null) {
                cstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
        	logger.error(e.getMessage());
        } finally {
            //System.gc();
        }
}

  public static void Close(Connection conn, PreparedStatement pstm1,
                           PreparedStatement pstm2, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
      if (pstm1 != null) {
        pstm1.close();
      }
      if (pstm2 != null) {
        pstm2.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {
    	logger.error(e.getMessage());
    }
  }

}
