package com.huayuan.common.exception;

/**
 * Created by dell on 14-3-28.
 */
public class MemberNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -5280264222686632767L;
    private Long memberId;

    public MemberNotFoundException() {
        super();
    }

    public MemberNotFoundException(final Long memberId) {
        super();
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotFoundException(Throwable cause) {
        super(cause);
    }

    protected MemberNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
