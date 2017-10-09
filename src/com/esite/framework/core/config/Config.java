package com.esite.framework.core.config;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;

/**
 * 
 *<PRE>
 * 功能描述:从配置文件读取配置信息
 * 修改历史:
 * -----------------------------------------------------------------------------
 * 		VERSION		DATE		BY			CHANGE/COMMENT
 * -----------------------------------------------------------------------------
 * 		1.0			2010-6-6	zhangzf		create
 * -----------------------------------------------------------------------------
 * </PRE>
 */
public class Config {

	private String configFileName = null;
	private Configuration config = null;
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
	public Config() {
	}
	
	public Config(String configFileName) {
		this.configFileName = configFileName;
		init();
	}
	
	

	/**
	 * @return the configFileName
	 */
	public String getConfigFileName() {
		return configFileName;
	}

	/**
	 * @param configFileName the configFileName to set
	 */
	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}

	public void init(){
		DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
		File configFile = new File(configFileName);
	    if (!configFile.exists()) { // but is it on CLASSPATH?
			URL fileURL = this.getClass().getClassLoader().getResource(
					configFileName);
			if (fileURL != null) {
				configFile = new File(fileURL.getFile());
			} else {
				throw new RuntimeException("File Not Found: " + configFileName);
			}
		}
		builder.setFile(configFile);
		try {
			config = builder.getConfiguration(true);
		} catch (ConfigurationException e) {
			throw new RuntimeException("create configuration failure." + e.getMessage());
		}
	}
	
	public String getString(String key){
		return config.getString(key);
	}
	
	public List getList(String key){
		return config.getList(key);
	}

	public Configuration getConfiguration(String name){
		return ((CombinedConfiguration)config).getConfiguration(name);
	}

	/**
	 * @param key
	 * @param value
	 * @see org.apache.commons.configuration.Configuration#addProperty(java.lang.String, java.lang.Object)
	 */
	public void addProperty(String key, Object value) {
        //config.addProperty(key, value);
		throw new RuntimeException("不支持修改配置文件操作.");
	}

	/**
	 * 
	 * @see org.apache.commons.configuration.Configuration#clear()
	 */
	public void clear() {
		//config.clear();
		throw new RuntimeException("不支持修改配置文件操作.");
	}

	/**
	 * @param key
	 * @see org.apache.commons.configuration.Configuration#clearProperty(java.lang.String)
	 */
	public void clearProperty(String key) {
		//config.clearProperty(key);
		throw new RuntimeException("不支持修改配置文件操作.");
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#containsKey(java.lang.String)
	 */
	public boolean containsKey(String key) {
		return config.containsKey(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBigDecimal(java.lang.String, java.math.BigDecimal)
	 */
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		return config.getBigDecimal(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBigDecimal(java.lang.String)
	 */
	public BigDecimal getBigDecimal(String key) {
		return config.getBigDecimal(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBigInteger(java.lang.String, java.math.BigInteger)
	 */
	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		return config.getBigInteger(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBigInteger(java.lang.String)
	 */
	public BigInteger getBigInteger(String key) {
		return config.getBigInteger(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBoolean(java.lang.String, boolean)
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBoolean(java.lang.String, java.lang.Boolean)
	 */
	public Boolean getBoolean(String key, Boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getBoolean(java.lang.String)
	 */
	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getByte(java.lang.String, byte)
	 */
	public byte getByte(String key, byte defaultValue) {
		return config.getByte(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getByte(java.lang.String, java.lang.Byte)
	 */
	public Byte getByte(String key, Byte defaultValue) {
		return config.getByte(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getByte(java.lang.String)
	 */
	public byte getByte(String key) {
		return config.getByte(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getDouble(java.lang.String, double)
	 */
	public double getDouble(String key, double defaultValue) {
		return config.getDouble(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getDouble(java.lang.String, java.lang.Double)
	 */
	public Double getDouble(String key, Double defaultValue) {
		return config.getDouble(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getDouble(java.lang.String)
	 */
	public double getDouble(String key) {
		return config.getDouble(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getFloat(java.lang.String, float)
	 */
	public float getFloat(String key, float defaultValue) {
		return config.getFloat(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getFloat(java.lang.String, java.lang.Float)
	 */
	public Float getFloat(String key, Float defaultValue) {
		return config.getFloat(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getFloat(java.lang.String)
	 */
	public float getFloat(String key) {
		return config.getFloat(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getInt(java.lang.String, int)
	 */
	public int getInt(String key, int defaultValue) {
		return config.getInt(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getInt(java.lang.String)
	 */
	public int getInt(String key) {
		return config.getInt(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getInteger(java.lang.String, java.lang.Integer)
	 */
	public Integer getInteger(String key, Integer defaultValue) {
		return config.getInteger(key, defaultValue);
	}

	/**
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getKeys()
	 */
	public Iterator getKeys() {
		return config.getKeys();
	}

	/**
	 * @param prefix
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getKeys(java.lang.String)
	 */
	public Iterator getKeys(String prefix) {
		return config.getKeys(prefix);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getList(java.lang.String, java.util.List)
	 */
	public List getList(String key, List defaultValue) {
		return config.getList(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getLong(java.lang.String, long)
	 */
	public long getLong(String key, long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getLong(java.lang.String, java.lang.Long)
	 */
	public Long getLong(String key, Long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getLong(java.lang.String)
	 */
	public long getLong(String key) {
		return config.getLong(key);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getProperties(java.lang.String)
	 */
	public Properties getProperties(String key) {
		return config.getProperties(key);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getProperty(java.lang.String)
	 */
	public Object getProperty(String key) {
		return config.getProperty(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getShort(java.lang.String, short)
	 */
	public short getShort(String key, short defaultValue) {
		return config.getShort(key, defaultValue);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getShort(java.lang.String, java.lang.Short)
	 */
	public Short getShort(String key, Short defaultValue) {
		return config.getShort(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getShort(java.lang.String)
	 */
	public short getShort(String key) {
		return config.getShort(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getString(java.lang.String, java.lang.String)
	 */
	public String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	/**
	 * @param key
	 * @return
	 * @see org.apache.commons.configuration.Configuration#getStringArray(java.lang.String)
	 */
	public String[] getStringArray(String key) {
		return config.getStringArray(key);
	}

	/**
	 * @return
	 * @see org.apache.commons.configuration.Configuration#isEmpty()
	 */
	public boolean isEmpty() {
		return config.isEmpty();
	}

	/**
	 * @param key
	 * @param value
	 * @see org.apache.commons.configuration.Configuration#setProperty(java.lang.String, java.lang.Object)
	 */
	public void setProperty(String key, Object value) {
		//config.setProperty(key, value);
		throw new RuntimeException("不支持修改配置文件操作.");
	}

	/**
	 * @param prefix
	 * @return
	 * @see org.apache.commons.configuration.Configuration#subset(java.lang.String)
	 */
	public Configuration subset(String prefix) {
		return config.subset(prefix);
	}
	
	

}
