package com.huayuan.common.exception;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 14-3-24.
 */
@Component
public final class App {
    private static final App INSTANCE = new App();
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static Configuration CONFIG;

    static {
        try {
            CONFIG = new PropertiesConfiguration("config.properties");
        } catch (ConfigurationException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private App() {
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public String get(final String key) {
        return CONFIG.getString(key);
    }

    public String getImageBase() {
        return get("image.store.base");
    }

    public String getIdCardImageBase() {
        return getImageBase() + "/idCard";
    }

    public String getCreditCardImageBase() {
        return getImageBase() + "/creditCard";
    }

    public String getImageMagickInstallation() {
        return get("imageMagick.installation");
    }

    public String getTesseractOCR() {
        return get("tesseractOCR.installation");
    }
}
