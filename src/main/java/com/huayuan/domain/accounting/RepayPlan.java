package com.huayuan.domain.accounting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huayuan.domain.member.Member;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dell on 14-4-8.
 */
@Entity
@Table(name = "REPAY_PLAN")
public class RepayPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BID")
    @JsonIgnore
    private Loan loan;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    private Member member;
    @Column(name = "TERM")
    private Integer term;
    @Column(name = "TERM_NO")
    private Integer termNo;
    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @Column(name = "DUE_AMT")
    private Double dueAmt;
    @Column(name = "DUE_PRINCIPAL")
    private Double duePrincipal;
    @Column(name = "DUE_INTEREST")
    private Double dueInterest;
    @Column(name = "REST_PRINCIPAL")
    private Double restPrincipal;
    @Column(name = "PAID_INTEREST")
    private Double paidInterest;
    @Column(name = "PAID_PRINCIPAL")
    private Double paidPrincipal;
    @Column(name = "OVERDUE_AMT")
    private Double overDueAmt;
    @Column(name = "OVERDUE_DAY")
    private Integer overDueDay;
    @Column(name = "OVERDUE_INTEREST")
    private Double overDue_Interest;

    public RepayPlan() {
        this.overDue_Interest = 0d;
        this.overDueAmt = 0d;
        this.overDueDay = 0;
        this.paidInterest = 0d;
        this.paidPrincipal = 0d;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getTermNo() {
        return termNo;
    }

    public void setTermNo(Integer termNo) {
        this.termNo = termNo;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(Double dueAmt) {
        this.dueAmt = dueAmt;
    }

    public Double getDuePrincipal() {
        return duePrincipal;
    }

    public void setDuePrincipal(Double duePrincipal) {
        this.duePrincipal = duePrincipal;
    }

    public Double getDueInterest() {
        return dueInterest;
    }

    public void setDueInterest(Double dueInterest) {
        this.dueInterest = dueInterest;
    }

    public Double getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(Double paidInterest) {
        this.paidInterest = paidInterest;
    }

    public Double getPaidPrincipal() {
        return paidPrincipal;
    }

    public void setPaidPrincipal(Double paidPrincipal) {
        this.paidPrincipal = paidPrincipal;
    }

    public Double getOverDueAmt() {
        return overDueAmt;
    }

    public void setOverDueAmt(Double overDueAmt) {
        this.overDueAmt = overDueAmt;
    }

    public Integer getOverDueDay() {
        return overDueDay;
    }

    public void setOverDueDay(Integer overDueDay) {
        this.overDueDay = overDueDay;
    }

    public Double getOverDue_Interest() {
        return overDue_Interest;
    }

    public void setOverDue_Interest(Double overDue_Interest) {
        this.overDue_Interest = overDue_Interest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRestPrincipal() {
        return restPrincipal;
    }

    public void setRestPrincipal(Double restPrincipal) {
        this.restPrincipal = restPrincipal;
    }
}
