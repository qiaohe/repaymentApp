package com.huayuan.service;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 14-4-18.
 */
@Service(value = "smsVerificationCodeService")
@Transactional
public class SmsVerificationCodeServiceImpl implements SmsVerificationCodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsVerificationCodeService.class);
    private static final String SEND_SUCCESS = "<State>0</State>";
    private static final ConcurrentHashMap<String, String> MOBILE_PHONE_VERIFICATION_CODE_MAP = new ConcurrentHashMap<>();
    @Inject
    private RestTemplate restTemplate;
    @Value("${sms.template}")
    private String smsTemplate;
    @Value("${sms.postTemplate}")
    private String postTemplate;
    @Value("${sms.url}")
    private String smsUrl;

    private String getSmsMessage(String mobilePhone, String code) {
        try {
            String msg = URLEncoder.encode(MessageFormat.format(smsTemplate, code), "UTF-8");
            return MessageFormat.format(postTemplate, mobilePhone, msg);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("sms message can not be encoded.");
        }
        return null;
    }

    @Override
    public boolean sendVerificationCode(String mobilePhone) {
        String code = RandomStringUtils.randomNumeric(6);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity req = new HttpEntity<>(getSmsMessage(mobilePhone, code), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.postForEntity(smsUrl, req, String.class);
        if (response.toString().contains(SEND_SUCCESS)) {
            MOBILE_PHONE_VERIFICATION_CODE_MAP.put(mobilePhone, code);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyCode(String mobilePhone, String code) {
        boolean result = MOBILE_PHONE_VERIFICATION_CODE_MAP.containsKey(mobilePhone) &&
                MOBILE_PHONE_VERIFICATION_CODE_MAP.get(mobilePhone).equals(code);
        MOBILE_PHONE_VERIFICATION_CODE_MAP.remove(mobilePhone);
        return result;
    }
}
