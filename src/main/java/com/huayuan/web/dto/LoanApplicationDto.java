package com.huayuan.web.dto;

/**
 * Created by Johnson on 4/26/14.
 */
public class LoanApplicationDto {
    private int term;
    private Double repayPerTerm;
    private Double amt;
    private String appNo;
    private Double saveCost;
    private Boolean isFullyApproved;

    public LoanApplicationDto() {
        isFullyApproved = false;
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

    public Boolean getIsFullyApproved() {
        return isFullyApproved;
    }

    public void setIsFullyApproved(Boolean isFullyApproved) {
        this.isFullyApproved = isFullyApproved;
    }
}
