package com.huayuan.domain.accounting.core;

/**
 * Created by Johnson on 5/19/14.
 */
public enum LoanStatusEnum {
    NORMAL(0), OVERDUE(1), BAD_DEBT(2), WAITING(8), CLOSE(9);

    private final int num;

    LoanStatusEnum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public static LoanStatusEnum from(String name) {
        for (LoanStatusEnum ls : values()) {
            if (ls.name().equals(name)) return ls;
        }
        return null;
    }

    public static LoanStatusEnum fromNum(int num) {
        for (LoanStatusEnum ls : values()) {
            if (ls.getNum() == num) return ls;
        }
        return null;
    }
}

