/*
 * Created on 2004-12-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.esite.framework.core.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.esite.framework.core.exception.BaseException;
import com.esite.framework.core.exception.DAOException;

/**
 * @author zhangzf
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class QueryHelper {
    private static Logger logger = Logger.getLogger(QueryHelper.class);
    /**
     * 根据sql进行查询,返回RowSet
     * @param sql
     * @return
     */
    public static List query(String sql,Connection con) {
        List rowSet = null;

        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = con.createStatement();
            if (logger.isDebugEnabled()) {
                logger.debug("查询SQL是:" + sql);
            }
            resultSet = stmt.executeQuery(sql);
            rowSet = ResultSetHelper.newInstance().getList(resultSet);
        } catch (SQLException e) {
            logger.error("执行查询语句时发生错误,原因:" + e);
            throw new BaseException("执行查询语句时发生错误,原因:" + e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqle) {
                    logger.error(sqle.getMessage());
                }
                resultSet = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqle) {
                    logger.error(sqle.getMessage());
                }
                stmt = null;
            }
        }
        return rowSet;
    }
    
    public static List query(int start, int count, String sql, Object[] param, Connection conn){
    	StringBuilder sb = new StringBuilder("SELECT * FROM (  SELECT pqc.*, ROWNUM RN  FROM (").append(sql).append(") pqc  WHERE ROWNUM < ?  )  WHERE RN >= ?");
    	
    	List rowSet = null;

    	 PreparedStatement stmt = null;
         ResultSet resultSet = null;
         try {
             stmt = conn.prepareStatement(sb.toString());
             if (logger.isDebugEnabled()) {
                 logger.debug("查询SQL是:" + sql);
             }
             int position = 1;
             if(param!=null){
            	 for(int i=0;i<param.length;i++)
            		 setObjectValue(stmt,i+1,param[i]);
                     //stmt.setObject(i+1, param[i]);
            	 position = param.length+1;
             }
             stmt.setObject(position, start+count+1);
             stmt.setObject(position+1, start+1);
             resultSet = stmt.executeQuery();
             rowSet = ResultSetHelper.newInstance().getList(resultSet);
         } catch (SQLException e) {
             logger.error("执行查询语句时发生错误,原因:" + e);
             throw new BaseException("执行查询语句时发生错误,原因:" + e);
         } finally {
             if (resultSet != null) {
                 try {
                     resultSet.close();
                 } catch (SQLException sqle) {
                     logger.error(sqle.getMessage());
                 }
                 resultSet = null;
             }
             if (stmt != null) {
                 try {
                     stmt.close();
                 } catch (SQLException sqle) {
                     logger.error(sqle.getMessage());
                 }
                 stmt = null;
             }
         }
         return rowSet;
    }
   
    
    /**
     * 
     *<PRE>
     * 功能描述:批量更新
     * @param sqlwithpara
     * @param para
     * @param con
     * @return
     * 修改历史:
     * -----------------------------------------------------------------------------
     * 		VERSION		DATE		BY			CHANGE/COMMENT
     * -----------------------------------------------------------------------------
     * 		1.0			2010-6-6	zhangzf		create
     * -----------------------------------------------------------------------------
     * </PRE>
     */
    public static int[] batchUpdate(String sqlwithpara, List para,Connection con) {
        if (sqlwithpara == null || sqlwithpara.length() == 0)
            return null;
        PreparedStatement stmt = null;
        int[] updateNum;
        try {
            stmt = con.prepareStatement(sqlwithpara);
            for (int i = 0; i < para.size(); i++) {
                List nativepara = (List) para.get(i);
                for (int j = 0; j < nativepara.size(); j++) {
                	setObjectValue(stmt,j + 1,nativepara.get(j));
                    //stmt.setObject(j + 1, nativepara.get(j));
                }
                stmt.addBatch();
            }
            updateNum = stmt.executeBatch();
        } catch (SQLException e) {
            logger.error("执行更新语句时发生错误,原因:" + e);
            throw new BaseException("执行更新语句时发生错误,原因:" + e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqle) {
                    logger.error(sqle.getMessage());
                }
                stmt = null;
            }
        }
        return updateNum;
    }
    
    public static int[] batchUpdate(String sqlwithpara, Object[][] para,Connection con) {
        if (sqlwithpara == null || sqlwithpara.length() == 0)
            return null;
        PreparedStatement stmt = null;
        int[] updateNum;
        try {
            stmt = con.prepareStatement(sqlwithpara);
            for (int i = 0; i < para.length; i++) {
                Object[] nativepara = para[i];
                for (int j = 0; j < nativepara.length; j++) {
                	setObjectValue(stmt,j + 1, nativepara[j]);
                    //stmt.setObject(j + 1, nativepara[j]);
                }
                stmt.addBatch();
            }
            updateNum = stmt.executeBatch();
        } catch (SQLException e) {
            logger.error("执行更新语句时发生错误,原因:" + e);
            throw new BaseException("执行更新语句时发生错误,原因:" + e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqle) {
                    logger.error(sqle.getMessage());
                }
                stmt = null;
            }
        }
        return updateNum;
    }
    
    public static List query(String sql,Object[] para,Connection con) {
        List rowSet = null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = con.prepareStatement(sql);
            if (logger.isDebugEnabled()) {
                logger.debug("查询SQL是:" + sql);
            }
            if(para!=null){
            	for(int i=0;i<para.length;i++)
            		setObjectValue(stmt,i+1, para[i]);
                   // stmt.setObject(i+1, para[i]);
            }
            
            resultSet = stmt.executeQuery();
            rowSet = ResultSetHelper.newInstance().getList(resultSet);
        } catch (SQLException e) {
            logger.error("执行查询语句时发生错误,原因:" + e);
            throw new BaseException("执行查询语句时发生错误,原因:" + e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqle) {
                    logger.error(sqle.getMessage());
                }
                resultSet = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqle) {
                    logger.error(sqle.getMessage());
                }
                stmt = null;
            }
        }
        return rowSet;
    }
    public static void update(String sql,Connection con){
    	update(sql,null,con);
    }
    public static void update(String sql,Object[] data,Connection con){
    	PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            if(data!=null){
            	for(int i=0;i<data.length;i++){
            		setObjectValue(stmt,i+1, data[i]);
            		//stmt.setObject( i+1, data[i]);
            	}            	
            }
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("执行语句时发生错误,原因:" + e);
            throw new DAOException("执行语句时发生错误,原因:" + e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqle) {
                    logger.error(sqle.getMessage());
                }
                stmt = null;
            }
        }
    }

	private static void setObjectValue(PreparedStatement ps, int count,Object object) throws SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("count is " + count + " object is " + object);
		}
		if (object != null && object instanceof Date) {
			if (object instanceof java.sql.Date) {
				ps.setDate(count, (java.sql.Date) object);
			} else if (object instanceof java.sql.Timestamp) {
				ps.setTimestamp(count, (java.sql.Timestamp) object);
			} else if (object instanceof java.util.Date) {
				long time = ((java.util.Date) object).getTime();
				ps.setTimestamp(count, new java.sql.Timestamp(time));
			} else {
				ps.setObject(count, object);
			}
		} else {
			ps.setObject(count, object);
		}
	}

}