package com.huayuan.web.dto;

/**
 * Created by dell on 14-5-10.
 */
public class ApplicationApproveDto {
    private Double amt;
    private Double sugCrl;
    private Double apr;
    private Integer term;
    private String decision;
    private String reason1;
    private String reason2;
    private String reason3;
    private String opinion;
    private String profile;
    private String creditor;

    public ApplicationApproveDto() {
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getSugCrl() {
        return sugCrl;
    }

    public void setSugCrl(Double sugCrl) {
        this.sugCrl = sugCrl;
    }

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getReason1() {
        return reason1;
    }

    public void setReason1(String reason1) {
        this.reason1 = reason1;
    }

    public String getReason2() {
        return reason2;
    }

    public void setReason2(String reason2) {
        this.reason2 = reason2;
    }

    public String getReason3() {
        return reason3;
    }

    public void setReason3(String reason3) {
        this.reason3 = reason3;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }
}
