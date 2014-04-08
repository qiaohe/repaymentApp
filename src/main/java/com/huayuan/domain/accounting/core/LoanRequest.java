package com.huayuan.domain.accounting.core;

import org.apache.commons.math.util.MathUtils;

/**
 * Created by dell on 14-4-8.
 */
public class LoanRequest {
    private final Double principal;
    private final Double apr;
    private final int term;

    public LoanRequest(Double principal, Double apr, int term) {
        this.principal = principal;
        this.apr = apr;
        this.term = term;
    }

    public Double getPrincipal() {
        return principal;
    }

    public Double getApr() {
        return apr;
    }

    public int getTerm() {
        return term;
    }

    public Double getAprByMonth() {
        return apr / 12;
    }

    public Double getMonthlyRepay() {
        final double d = principal * getAprByMonth() * Math.pow(1 + getAprByMonth(), term) / (Math.pow(1 + getAprByMonth(), term) - 1);
        return MathUtils.round(d, 2, 6);
    }
}
