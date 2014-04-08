package com.huayuan.domain.accounting;

import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 14-4-8.
 */
@Entity
@Table(name = "BORROW")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BID")
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APP_NO")
    private Application application;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @Column(name = "AMT")
    private Double amt;
    @Column(name = "APR")
    private Double apr;
    @Column(name = "TERM")
    private Integer term;
    @Column(name = "STARTDATE")
    private Date startDate;
    @Column(name = "PRINAIPAL")
    private Double principal;
    @Column(name = "INTEREST")
    private Double Integererest;
    @Column(name = "PAID_PRINAIPAL")
    private Double paidPrincipal;
    @Column(name = "PAID_INTEREST")
    private Double paidInterest;
    @Column(name = "CUR_DELQ")
    private Integer curDelq;
    @Column(name = "MAX_DELQ")
    private Integer maxDelq;
    @Column(name = "STATUS")
    private Integer status;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private List<RepayPlan> repayPlans = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getInterest() {
        return Integererest;
    }

    public void setInterest(Double Integererest) {
        this.Integererest = Integererest;
    }

    public Double getPaidPrincipal() {
        return paidPrincipal;
    }

    public void setPaidPrincipal(Double paidPrincipal) {
        this.paidPrincipal = paidPrincipal;
    }

    public Double getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(Double paidInterest) {
        this.paidInterest = paidInterest;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

