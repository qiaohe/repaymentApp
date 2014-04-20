package com.huayuan.domain.crawler;

import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import java.text.MessageFormat;

/**
 * Created by dell on 14-4-6.
 */
public class BillEmail {
    private static final String POP3_PATTERN = "pop.{0}";
    private static final String IMAP_PATTERN = "imap.{0}";
    private final String email;
    private final String password;
    private final Integer bank;

    public BillEmail(String email, String password, Integer bank) {
        this.email = email;
        this.password = password;
        this.bank = bank;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getBank() {
        return bank;
    }

    public String getPop3() {
        return MessageFormat.format(POP3_PATTERN, StringUtils.substringAfter(email, "@"));
    }

    public String getImap() {
        return MessageFormat.format(IMAP_PATTERN, StringUtils.substringAfter(email, "@"));
    }
}
