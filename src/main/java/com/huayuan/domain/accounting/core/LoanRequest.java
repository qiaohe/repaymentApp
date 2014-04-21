package com.huayuan.domain.accounting.core;

import com.huayuan.common.Number;

import java.util.Date;


/**
 * Created by dell on 14-4-8.
 */
public class LoanRequest {
    private final Double principal;
    private final Double apr;
    private final int term;
    private final Date startDate;

    public LoanRequest(Double principal, Double apr, int term, Date startDate) {
        this.principal = principal;
        this.apr = apr;
        this.term = term;
        this.startDate = startDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public Double getAprByMonth() {
        return new Number(apr / 12).getValue(6, 4);
    }

    public Double getMonthlyRepay() {
        return new Number((principal * getAprByMonth() * Math.pow(1 + getAprByMonth(), term) /
                (Math.pow(1 + getAprByMonth(), term) - 1))).getValue();
    }
}
