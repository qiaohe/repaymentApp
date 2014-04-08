package com.huayuan.domain.loanapplication;

/**
 * Created by Johnson on 4/3/14.
 */
public enum RepaymentModeEnum {
    AVERAGE_CAPITAL_INTEREST(0, "等额本息"),
    AVERAGE_CAPITAL(1, "等额本金"),
    INTEREST_PLUS_CAPITAL(2, "随本付息"),
    INTEREST_ONLY(3, "按期付息，到期还本");

    private final Integer id;
    private final String name;

    RepaymentModeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static RepaymentModeEnum fromName(final String name) {
        for (RepaymentModeEnum rm : values()) {
            if (rm.name.equalsIgnoreCase(name)) return rm;
        }
        return null;
    }
}
