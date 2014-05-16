package com.huayuan.common.util;

import org.apache.commons.math.util.MathUtils;

import java.math.BigDecimal;

/**
 * Created by Johnson on 4/8/14.
 */
public class Number {
    private static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    private static final int DEFAULT_SCALE = 2;

    private final Double value;

    public Number(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return MathUtils.round(value, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    public Number valueOf(Double d) {
        return new Number(d);
    }

    public Double getValue(int scale, int roundingMode) {
        return MathUtils.round(value, scale, roundingMode);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
