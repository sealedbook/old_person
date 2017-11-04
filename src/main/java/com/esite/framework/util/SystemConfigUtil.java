/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.framework.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shitianshu on 2017/11/3 下午8:56.
 */
public class SystemConfigUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfigUtil.class);

    private final static Properties config;

    public static String ECG_BASE_PATH = System.getProperty("ecg.basepath");

    static {
        config = new Properties();
        InputStream stream = null;
        try {
            stream = SystemConfigUtil.class.getResourceAsStream("/app.properties");
            config.load(stream);
        } catch (Exception e) {
            LOGGER.error("error parse processor config", e);
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (Exception e1) {
                    LOGGER.warn("close error", e1);
                }
            }
        }
        ECG_BASE_PATH = config.getProperty("ecg.basepath");
    }

    /**
     * 获得心电图文件读写路径
     * @return
     */
    public static String getEcgBasePath() {
        return ECG_BASE_PATH;
    }
}