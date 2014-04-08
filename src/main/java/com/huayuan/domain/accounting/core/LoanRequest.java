package com.huayuan.domain.accounting.core;

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

    private Double getAprByMonth() {
        return apr / 12;
    }
}
