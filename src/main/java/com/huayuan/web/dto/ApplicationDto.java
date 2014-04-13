package com.huayuan.web.dto;

/**
 * Created by Johnson on 4/7/14.
 */
public class ApplicationDto {
    private double amt;
    private int term;

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
}
