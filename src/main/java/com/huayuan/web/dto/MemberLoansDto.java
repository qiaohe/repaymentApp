package com.huayuan.web.dto;

import java.util.Date;

/**
 * Created by Richard Xue on 14-5-20.
 */
public class MemberLoansDto {
    private String memberNo;
    private String name;
    private String loanNo;
    private Double amt;
    private Double apr;
    private Integer term;
    private Date loanDate;
    private Double prinaipal;
    private Double paidPrinaipal;
    private Double interest;
    private Double paidInterest;
    private Double paidOverdueInterest;
    private Integer curDelq;
    private Integer maxDelq;
    private String status;

    public MemberLoansDto(String memberNo, String name, String loanNo, Double amt, Double apr, Integer term, Date loanDate,
                          Double prinaipal, Double paidPrinaipal, Double interest, Double paidInterest,
                          Double paidOverdueInterest, Integer curDelq, Integer maxDelq, String status) {
        this.memberNo = memberNo;
        this.name = name;
        this.loanNo = loanNo;
        this.amt = amt;
        this.apr = apr;
        this.term = term;
        this.loanDate = loanDate;
        this.prinaipal = prinaipal;
        this.paidPrinaipal = paidPrinaipal;
        this.interest = interest;
        this.paidInterest = paidInterest;
        this.paidOverdueInterest = paidOverdueInterest;
        this.curDelq = curDelq;
        this.maxDelq = maxDelq;
        this.status = status;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
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

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Double getPrinaipal() {
        return prinaipal;
    }

    public void setPrinaipal(Double prinaipal) {
        this.prinaipal = prinaipal;
    }

    public Double getPaidPrinaipal() {
        return paidPrinaipal;
    }

    public void setPaidPrinaipal(Double paidPrinaipal) {
        this.paidPrinaipal = paidPrinaipal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(Double paidInterest) {
        this.paidInterest = paidInterest;
    }

    public Double getPaidOverdueInterest() {
        return paidOverdueInterest;
    }

    public void setPaidOverdueInterest(Double paidOverdueInterest) {
        this.paidOverdueInterest = paidOverdueInterest;
    }

    public Integer getCurDelq() {
        return curDelq;
    }

    public void setCurDelq(Integer curDelq) {
        this.curDelq = curDelq;
    }

    public Integer getMaxDelq() {
        return maxDelq;
    }

    public void setMaxDelq(Integer maxDelq) {
        this.maxDelq = maxDelq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
