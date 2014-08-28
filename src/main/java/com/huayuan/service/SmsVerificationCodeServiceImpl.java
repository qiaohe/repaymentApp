package com.huayuan.service;

import com.cloopen.rest.sdk.CCPRestSDK;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 14-4-18.
 */
@Service(value = "smsVerificationCodeService")
@Transactional
public class SmsVerificationCodeServiceImpl implements SmsVerificationCodeService {
    private static final String SEND_SUCCESS = "000000";
    private static final ConcurrentHashMap<String, String> MOBILE_PHONE_VERIFICATION_CODE_MAP = new ConcurrentHashMap<>();
    @Value("${sms.cloud.host}")
    private String smsHost;
    @Value("${sms.cloud.port}")
    private String smsPort;
    @Value("${sms.cloud.account}")
    private String smsAccount;
    @Value("${sms.cloud.token}")
    private String smsToken;
    @Value("${sms.cloud.appid}")
    private String smsAppId;
    @Value("${sms.cloud.template}")
    private String smsTemplateId;

    @Override
    public boolean sendVerificationCode(String mobilePhone) {
        String code = RandomStringUtils.randomNumeric(6);
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init(smsHost, smsPort);
        restAPI.setAccount(smsAccount, smsToken);
        restAPI.setAppId(smsAppId);
        if (SEND_SUCCESS.equals(restAPI.sendTemplateSMS(mobilePhone, smsTemplateId, new String[]{code}).get("statusCode"))) {
            MOBILE_PHONE_VERIFICATION_CODE_MAP.put(mobilePhone, code);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyCode(String mobilePhone, String code) {
        if (MOBILE_PHONE_VERIFICATION_CODE_MAP.containsKey(mobilePhone) && MOBILE_PHONE_VERIFICATION_CODE_MAP.get(mobilePhone).equals(code)) {
            MOBILE_PHONE_VERIFICATION_CODE_MAP.remove(mobilePhone);
            return true;
        }
        return false;
    }
}
