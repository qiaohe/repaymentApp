package com.huayuan.web.dto;

import com.huayuan.domain.loanapplication.RepaymentModeEnum;

/**
 * Created by Johnson on 4/7/14.
 */
public class ApplicationDto {
    private double amt;
    private int term;
    private String title;
    private int memberId;
    private RepaymentModeEnum repaymentMode;

    public ApplicationDto() {

    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public RepaymentModeEnum getRepaymentMode() {
        return repaymentMode;
    }

    public void setRepaymentMode(RepaymentModeEnum repaymentMode) {
        this.repaymentMode = repaymentMode;
    }
}
