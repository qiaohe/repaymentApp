package com.huayuan.service;

import com.huayuan.integration.sms.SingletonClient;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 14-4-18.
 */
@Service(value = "smsVerificationCodeService")
@Transactional
public class SmsVerificationCodeServiceImpl implements SmsVerificationCodeService {
    private static final int SEND_SUCCESS = 1;
    private static final ConcurrentHashMap<String, String> MOBILE_PHONE_VERIFICATION_CODE_MAP = new ConcurrentHashMap<>();
    @Value("${sms.template}")
    private String smsTemplate;
    @Override
    public boolean sendVerificationCode(String mobilePhone) {
        final String code = RandomStringUtils.randomNumeric(6);
        int response = SingletonClient.getClient().sendSMS(new String[]{mobilePhone}, MessageFormat.format(smsTemplate, code), "", 5);
        MOBILE_PHONE_VERIFICATION_CODE_MAP.put(mobilePhone, code);
        return response == SEND_SUCCESS;
    }

    @Override
    public boolean verifyCode(String mobilePhone, String code) {
        boolean result = MOBILE_PHONE_VERIFICATION_CODE_MAP.contains(mobilePhone) &&
                MOBILE_PHONE_VERIFICATION_CODE_MAP.get(mobilePhone).equals(code);
        MOBILE_PHONE_VERIFICATION_CODE_MAP.remove(mobilePhone);
        return result;
    }
}
