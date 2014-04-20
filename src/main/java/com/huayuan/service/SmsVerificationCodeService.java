package com.huayuan.service;

/**
 * Created by dell on 14-4-18.
 */
public interface SmsVerificationCodeService {
    public boolean sendVerificationCode(String mobilePhone);

    public boolean verifyCode(String mobilePhone, String code);
}
