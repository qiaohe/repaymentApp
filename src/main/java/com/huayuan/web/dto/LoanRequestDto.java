package com.huayuan.web.dto;

/**
 * Created by Johnson on 4/7/14.
 */
public class LoanRequestDto {
    private Long memberId;
    private double amt;
    private int term;
    private String creditCarNo;

    public LoanRequestDto() {
    }

    public LoanRequestDto(Long memberId, double amt, int term) {
        this.memberId = memberId;
        this.amt = amt;
        this.term = term;
    }
    public LoanRequestDto(Long memberId, double amt, int term, String creditCarNo) {
        this(memberId, amt, term);
        this.creditCarNo = creditCarNo;
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

    public String getCreditCarNo() {
        return creditCarNo;
    }

    public void setCreditCarNo(String creditCarNo) {
        this.creditCarNo = creditCarNo;
    }
}
