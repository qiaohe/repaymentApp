package com.huayuan.web.dto;

/**
 * Created by dell on 14-4-21.
 */
public class SavedCostDto {
    private int term;
    private Double amt;
    private Long memberId;

    public SavedCostDto() {
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
