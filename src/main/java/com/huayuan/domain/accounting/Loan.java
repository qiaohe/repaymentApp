package com.huayuan.domain.accounting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huayuan.domain.accounting.core.LoanRequest;
import com.huayuan.domain.accounting.core.RepayItem;
import com.huayuan.domain.accounting.core.RepayListCalculator;
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
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "APP_NO")
    @JsonIgnore
    private Application application;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
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
    private Double interest;
    @Column(name = "PAID_PRINAIPAL")
    private Double paidPrincipal;
    @Column(name = "PAID_INTEREST")
    private Double paidInterest;
    @Column(name = "CUR_DELQ")
    private Integer curDelq = 0;
    @Column(name = "MAX_DELQ")
    private Integer maxDelq = 0;
    @Column(name = "STATUS")
    private Integer status;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "loan")
    private Pay pay;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "loan")
    private List<RepayPlan> repayPlans = new ArrayList<>();

    public Loan() {
        status = 8;
        paidInterest = 0d;
        paidPrincipal = 0d;
    }

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
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
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

    public List<RepayPlan> getRepayPlans() {
        return repayPlans;
    }

    public void setRepayPlans(List<RepayPlan> repayPlans) {
        this.repayPlans = repayPlans;
    }

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    public Pay createTransfer() {
        Pay transfer = new Pay();
        transfer.setApplication(application);
        transfer.setLoan(this);
        return transfer;
    }

    @JsonIgnore
    public LoanRequest getLoanRequest() {
        return new LoanRequest(this.amt, apr, term, pay.getConfirmDate());
    }

    public List<RepayPlan> createRepayPlan() {
        List<RepayPlan> result = new ArrayList<>();
        List<RepayItem> items = new RepayListCalculator(getLoanRequest()).calculate();
        for (RepayItem repayItem : items) {
            RepayPlan rp = new RepayPlan();
            rp.setLoan(this);
            rp.setTermNo(repayItem.getTermNo());
            rp.setTerm(term);
            rp.setDueDate(repayItem.getDueDate());
            rp.setDueInterest(repayItem.getDueInterest());
            rp.setDuePrincipal(repayItem.getDuePrincipal());
            rp.setRestPrincipal(repayItem.getRestPrincipal());
            rp.setDueAmt(repayItem.getAmt());
            rp.setMember(this.getMember());
            result.add(rp);
        }
        return result;
    }

    @JsonIgnore
    public boolean isOverDue() {
        return curDelq > 0;
    }

    public Double currentBalance(Date date) {
        Double result = 0d;
        for (RepayPlan plan : repayPlans) {
            if (plan.getDueDate().before(date) && (plan.getPaidPrincipal() == null || plan.getOverDue_Interest() > 0)) {
                result += plan.getDueAmt();
            }
        }
        return result;
    }

    @JsonIgnore
    public int getPaidTerm() {
        int result = 0;
        for (RepayPlan plan : repayPlans) {
            if (plan.getPaidPrincipal() > 0) result++;
        }
        return result;
    }

    @JsonIgnore
    public String getRating() {
        return application.getaScore().getRating();
    }

    public String getApplicationNo() {
        return this.getApplication().getApplicationNo();
    }
}

