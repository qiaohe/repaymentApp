package com.huayuan.common;

import com.huayuan.domain.dictionary.Dictionary;
import com.huayuan.repository.DictionaryRepository;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 14-3-24.
 */
@Component
public final class App {
    private static final App INSTANCE = new App();
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static Configuration CONFIG;
    @Inject
    private DictionaryRepository dictionaryRepository;
    private static final ConcurrentHashMap<Integer, String> BANK_MAP = new ConcurrentHashMap<>();

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

    public String getOcrServerHost() {
        return get("ocrserver.host");
    }

    public int getOcrServerPort() {
        return Integer.valueOf(get("ocrserver.port"));
    }

    @PostConstruct
    private void init() {
        List<Dictionary> banks = dictionaryRepository.findByType("BANK");
        for (Dictionary dictionary : banks) {
            BANK_MAP.put(Integer.valueOf(dictionary.getValue()), dictionary.getName());
        }
    }

    public String getBankName(final Integer bankId) {
        return BANK_MAP.get(bankId);
    }

    public Integer getBankId(final String bankName) {
        for (Map.Entry<Integer, String> entry : BANK_MAP.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(bankName)) return entry.getKey();
        }
        return null;
    }
}
