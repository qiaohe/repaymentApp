package com.huayuan.domain.member;

/**
 * Created by Administrator on 14-3-27.
 */
public enum SexEnum {
    FEMALE("男"),
    MALE("女");

    private final String name;

    SexEnum(String name) {
        this.name = name;
    }

    public static SexEnum fromName(final String name) {
        for (SexEnum se : values()) {
            if (name.equalsIgnoreCase(se.name)) {
                return se;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
