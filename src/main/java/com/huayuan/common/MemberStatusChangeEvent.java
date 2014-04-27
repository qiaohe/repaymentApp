package com.huayuan.common;

import org.springframework.context.ApplicationEvent;

/**
 * Created by dell on 14-4-27.
 */
public class MemberStatusChangeEvent extends ApplicationEvent {
    private final String openId;
    private final String message;

    public MemberStatusChangeEvent(Object source, String openId, String message) {
        super(source);
        this.openId = openId;
        this.message = message;
    }

    public String getOpenId() {
        return openId;
    }

    public String getMessage() {
        return message;
    }
}
