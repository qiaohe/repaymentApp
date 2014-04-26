package com.huayuan.web.dto;

/**
 * Created by Johnson on 4/26/14.
 */
public class LoanDto {
    private int term;
    private Double repayPerTerm;
    private Double amt;
    private String appNo;
    private Double saveCost;

    public LoanDto() {

    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Double getRepayPerTerm() {
        return repayPerTerm;
    }

    public void setRepayPerTerm(Double repayPerTerm) {
        this.repayPerTerm = repayPerTerm;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public Double getSaveCost() {
        return saveCost;
    }

    public void setSaveCost(Double saveCost) {
        this.saveCost = saveCost;
    }
}
