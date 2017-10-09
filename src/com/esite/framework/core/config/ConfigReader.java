package com.esite.framework.core.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;

import com.esite.framework.core.factory.WebApplicationContextUtil;;


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
public class ConfigReader {
	private static Config config= null;
	
	static{
		config = (Config)WebApplicationContextUtil.getBean("configBean");
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#containsKey(java.lang.String)
	 */
	public static boolean containsKey(String key) {
		return config.containsKey(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getBigDecimal(java.lang.String, java.math.BigDecimal)
	 */
	public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		return config.getBigDecimal(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getBigDecimal(java.lang.String)
	 */
	public static BigDecimal getBigDecimal(String key) {
		return config.getBigDecimal(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getBigInteger(java.lang.String, java.math.BigInteger)
	 */
	public static BigInteger getBigInteger(String key, BigInteger defaultValue) {
		return config.getBigInteger(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getBigInteger(java.lang.String)
	 */
	public static BigInteger getBigInteger(String key) {
		return config.getBigInteger(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getBoolean(java.lang.String, boolean)
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getBoolean(java.lang.String, java.lang.Boolean)
	 */
	public static Boolean getBoolean(String key, Boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getBoolean(java.lang.String)
	 */
	public static boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getByte(java.lang.String, byte)
	 */
	public static byte getByte(String key, byte defaultValue) {
		return config.getByte(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getByte(java.lang.String, java.lang.Byte)
	 */
	public static Byte getByte(String key, Byte defaultValue) {
		return config.getByte(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getByte(java.lang.String)
	 */
	public static byte getByte(String key) {
		return config.getByte(key);
	}

	/**
	 * @return
	 * @see com.esite.config.Config#getConfigFileName()
	 */
	public static String getConfigFileName() {
		return config.getConfigFileName();
	}

	/**
	 * @param name
	 * @return
	 * @see com.esite.config.Config#getConfiguration(java.lang.String)
	 */
	public static Configuration getConfiguration(String name) {
		return config.getConfiguration(name);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getDouble(java.lang.String, double)
	 */
	public static double getDouble(String key, double defaultValue) {
		return config.getDouble(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getDouble(java.lang.String, java.lang.Double)
	 */
	public static Double getDouble(String key, Double defaultValue) {
		return config.getDouble(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getDouble(java.lang.String)
	 */
	public static double getDouble(String key) {
		return config.getDouble(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getFloat(java.lang.String, float)
	 */
	public static float getFloat(String key, float defaultValue) {
		return config.getFloat(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getFloat(java.lang.String, java.lang.Float)
	 */
	public static Float getFloat(String key, Float defaultValue) {
		return config.getFloat(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getFloat(java.lang.String)
	 */
	public static float getFloat(String key) {
		return config.getFloat(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getInt(java.lang.String, int)
	 */
	public static int getInt(String key, int defaultValue) {
		return config.getInt(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getInt(java.lang.String)
	 */
	public static int getInt(String key) {
		return config.getInt(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getInteger(java.lang.String, java.lang.Integer)
	 */
	public static Integer getInteger(String key, Integer defaultValue) {
		return config.getInteger(key, defaultValue);
	}

	/**
	 * @return
	 * @see com.esite.config.Config#getKeys()
	 */
	public static Iterator getKeys() {
		return config.getKeys();
	}

	/**
	 * @param prefix
	 * @return
	 * @see com.esite.config.Config#getKeys(java.lang.String)
	 */
	public static Iterator getKeys(String prefix) {
		return config.getKeys(prefix);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getList(java.lang.String, java.util.List)
	 */
	public static List getList(String key, List defaultValue) {
		return config.getList(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getList(java.lang.String)
	 */
	public static List getList(String key) {
		return config.getList(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getLong(java.lang.String, long)
	 */
	public static long getLong(String key, long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getLong(java.lang.String, java.lang.Long)
	 */
	public static Long getLong(String key, Long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getLong(java.lang.String)
	 */
	public static long getLong(String key) {
		return config.getLong(key);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getProperties(java.lang.String)
	 */
	public static Properties getProperties(String key) {
		return config.getProperties(key);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getProperty(java.lang.String)
	 */
	public static Object getProperty(String key) {
		return config.getProperty(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getShort(java.lang.String, short)
	 */
	public static short getShort(String key, short defaultValue) {
		return config.getShort(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getShort(java.lang.String, java.lang.Short)
	 */
	public static Short getShort(String key, Short defaultValue) {
		return config.getShort(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getShort(java.lang.String)
	 */
	public static short getShort(String key) {
		return config.getShort(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see com.esite.config.Config#getString(java.lang.String, java.lang.String)
	 */
	public static String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getString(java.lang.String)
	 */
	public static String getString(String key) {
		return config.getString(key);
	}

	/**
	 * @param key
	 * @return
	 * @see com.esite.config.Config#getStringArray(java.lang.String)
	 */
	public static String[] getStringArray(String key) {
		return config.getStringArray(key);
	}

	/**
	 * @return
	 * @see com.esite.config.Config#isEmpty()
	 */
	public static boolean isEmpty() {
		return config.isEmpty();
	}

	/**
	 * @param prefix
	 * @return
	 * @see com.esite.config.Config#subset(java.lang.String)
	 */
	public static Configuration subset(String prefix) {
		return config.subset(prefix);
	}
	
	
}
