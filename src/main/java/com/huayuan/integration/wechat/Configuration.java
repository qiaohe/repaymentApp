package com.huayuan.integration.wechat;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Johnson on 4/14/14.
 */
public class Configuration {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
    private static org.apache.commons.configuration.Configuration CONFIG;

    static {
        try {
            CONFIG = new PropertiesConfiguration(Thread.currentThread().getContextClassLoader().getResource("wechatconfig.properties"));
        } catch (ConfigurationException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static String token() {
        return CONFIG.getString("weChat.token");
    }

    public static String appId() {
        return CONFIG.getString("weChat.appId");
    }

    public static String appSecret() {
        return CONFIG.getString("weChat.appSecret");
    }
}
