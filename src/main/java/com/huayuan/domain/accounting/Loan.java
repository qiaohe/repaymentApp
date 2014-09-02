package com.huayuan.domain.accounting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huayuan.common.util.Day;
import com.huayuan.domain.accounting.core.LoanRequest;
import com.huayuan.domain.accounting.core.RepayItem;
import com.huayuan.domain.accounting.core.RepayListCalculator;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.member.Member;
import org.apache.commons.lang.math.IntRange;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

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
    private static final int NOTIFICATION_THRESHOLD = 3;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BID")
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)

    @JoinColumn(name = "APP_NO")
    @JsonIgnore
    private Application application;

    @Column(name = "APP_NO", updatable = false, insertable = false)
    private String applicationNo;

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
    private Double principal = 0d;
    @Column(name = "INTEREST")
    private Double interest = 0d;
    @Column(name = "PAID_PRINAIPAL")
    private Double paidPrincipal = 0d;
    @Column(name = "PAID_INTEREST")
    private Double paidInterest = 0d;
    @Column(name = "CUR_DELQ")
    private Integer curDelq = 0;
    @Column(name = "MAX_DELQ")
    private Integer maxDelq = 0;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "PAID_OVERDUE_INT")
    private Double paidOverDueInt;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "loan")
    private Pay pay;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "loan")
    private List<RepayPlan> repayPlans = new ArrayList<>();

    public Loan() {
        status = 8;
        paidInterest = 0d;
        paidPrincipal = 0d;
        paidOverDueInt = 0d;
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
        return status == null ? 0 : status;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
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

    public void addRepayPlan(RepayPlan plan) {
        if (!repayPlans.contains(plan))
            repayPlans.add(plan);
    }

    public void createRepayPlans() {
        Double sumInterest = 0d;
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
            sumInterest += rp.getDueInterest();
            this.addRepayPlan(rp);
        }
        interest = sumInterest;

    }

    @JsonIgnore
    public boolean isOverDue() {
        return curDelq > 0;
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

    public Double getPaidOverDueInt() {
        return paidOverDueInt;
    }

    public void setPaidOverDueInt(Double paidOverDueInt) {
        this.paidOverDueInt = paidOverDueInt;
    }

    @Transient
    public boolean isOverDueStatus() {
        return status == 1 || status == 2;
    }

    @Transient
    public Date getDuePayDay() {
        return new Day(getStartDate()).getCurrentDayOfMonth();
    }

    public boolean overDueNotificationNeeded() {
        final IntRange threeDaysRange = new IntRange(0, NOTIFICATION_THRESHOLD);
        return isOverDue() && (threeDaysRange.containsInteger(curDelq) || curDelq % NOTIFICATION_THRESHOLD == 0);
    }

    public Date currentDueDate() {
        final Integer months = Months.monthsBetween(new DateTime(startDate).withDayOfMonth(1).withTime(0, 0, 0, 0), DateTime.now().withDayOfMonth(1).withTime(0, 0, 0, 0)).getMonths();
        return new Day(startDate).plusMonths(months);
    }

    public int daysBetweenDueDateAndNow() {
        return Days.daysBetween(new DateTime().withTime(0, 0, 0, 0), new DateTime(currentDueDate()).withTime(0, 0, 0, 0)).getDays();
    }

    public boolean repaymentNotificationNeeded() {
        return !isOverDue() && daysBetweenDueDateAndNow() < NOTIFICATION_THRESHOLD;
    }

    public boolean withTheSameMemberAndStartDate(Loan anotherLoan) {
        return member.getId().equals(anotherLoan.getMember().getId()) && new Day(startDate).isSameDay(anotherLoan.getStartDate());
    }

    @Transient
    public boolean isPaid() {
        for (RepayPlan plan : repayPlans) {
            DateTime dt = new DateTime(plan.getDueDate());
            if (dt.isAfterNow() && dt.plusMonths(-1).isBeforeNow() && plan.getPaidPrincipal() > 0) {
                return true;
            }
        }
        return false;
    }
}

