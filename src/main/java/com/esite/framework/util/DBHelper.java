/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shitianshu on 2017/11/13 下午8:37.
 */
public class DBHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DBHelper.class);

    public static final String name = "com.mysql.jdbc.Driver";

    public static final String url = SystemConfigUtil.fetchConfig().getProperty("jdbc.url");
    public static final String user = SystemConfigUtil.fetchConfig().getProperty("jdbc.user");
    public static final String password = SystemConfigUtil.fetchConfig().getProperty("jdbc.password");

    public static final String QA_TABLE_NAME = SystemConfigUtil.fetchConfig().getProperty("qa.db.table.name");

    public PreparedStatement pst = null;

    public static Map<String, String> query(String sql) {
        Connection conn = null;
        Statement statement = null;
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (null == resultSet) {
                return null;
            }
            Map<String, String> data = new HashMap<>();
            while (resultSet.next()) {
                String height = resultSet.getString("height");
                String weight = resultSet.getString("weight");
                String blood = resultSet.getString("blood");
                data.put("height", height);
                data.put("weight", weight);
                data.put("blood", blood);
                return data;
            }
            return null;
        } catch (Exception e) {
            LOG.error("query error", e);
            return null;
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String queryHeightColumn() {
        String sql = "select CONCAT(`sid`,\"X\",`gid`,\"X\",`qid`,\"SQ001\") height from " + QA_TABLE_NAME + " where title = 'G5Q00007'";
        Connection conn = null;
        Statement statement = null;
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (null == resultSet) {
                return null;
            }
            while (resultSet.next()) {
                String height = resultSet.getString("height");
                return height;
            }
            return null;
        } catch (Exception e) {
            LOG.error("query error", e);
            return null;
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String queryWeightColumn() {
        String sql = "select CONCAT(`sid`,\"X\",`gid`,\"X\",`qid`,\"SQ002\") weight from " + QA_TABLE_NAME + " where title = 'G5Q00007'";
        Connection conn = null;
        Statement statement = null;
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (null == resultSet) {
                return null;
            }
            while (resultSet.next()) {
                String height = resultSet.getString("weight");
                return height;
            }
            return null;
        } catch (Exception e) {
            LOG.error("query error", e);
            return null;
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String queryBloodColumn() {
        String sql = "select CONCAT(`sid`,\"X\",`gid`,\"X\",`qid`) blood from " + QA_TABLE_NAME + " where title = 'G5Q00004'";
        Connection conn = null;
        Statement statement = null;
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (null == resultSet) {
                return null;
            }
            while (resultSet.next()) {
                String height = resultSet.getString("blood");
                return height;
            }
            return null;
        } catch (Exception e) {
            LOG.error("query error", e);
            return null;
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String queryRequestColumn() {
        String sql = "select CONCAT(`sid`,\"X\",`gid`,\"X\",`qid`) request from " + QA_TABLE_NAME + " where title = 'G1Q00004'";
        Connection conn = null;
        Statement statement = null;
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (null == resultSet) {
                return null;
            }
            while (resultSet.next()) {
                String height = resultSet.getString("request");
                return height;
            }
            return null;
        } catch (Exception e) {
            LOG.error("query error", e);
            return null;
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
