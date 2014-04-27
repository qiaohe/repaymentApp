package com.huayuan.web.dto;

/**
 * Created by Johnson on 4/7/14.
 */
public class LoanRequestDto {
    private Long memberId;
    private double amt;
    private int term;

    public LoanRequestDto() {
    }

    public LoanRequestDto(Long memberId, double amt, int term) {
        this.memberId = memberId;
        this.amt = amt;
        this.term = term;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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