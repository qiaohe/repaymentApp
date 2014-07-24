package com.huayuan.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huayuan.domain.accounting.Loan;
import com.huayuan.domain.loanapplication.Application;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dell on 14-7-23.
 */
@Entity
@Table(name = "CONTRACT")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;
    @Column(name = "BORROWER")
    private String borrower;
    @Column(name = "LENDER")
    private String lender;
    @Column(name = "APR")
    private Double apr;
    @Column(name = "TERM")
    private Integer term;
    @Column(name = "AMOUNT")
    private Double amount;
    @Column(name = "SIGNINGDATE")
    private Date signingDate;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "REPAYMENT_DAY")
    private Integer repaymentDay;
    @Column(name = "APP_NO")
    private String appNo;
    @Column(name = "LOAN_ID")
    private Long loanId;
    @Column(name = "BORROWER_MOBILE")
    private String borrowerMobile;
    @Column(name = "BORROWER_EMAIL")
    private String borrowerEmail;
    @Column(name = "LENDER_MOBILE")
    private String lenderMobile;
    @Column(name = "LENDER_EMAIL")
    private String lenderEmail;
    @Column(name = "VERSION")
    private String version = "1.0";
    @Column(name = "CREATE_TIME")
    private Date create_Time;

    public Contract() {
        create_Time = new Date();
    }

    public Contract(Loan loan, String lender, String lenderEmail, String lenderMobile) {
        this();
        Application application = loan.getApplication();
        this.appNo = application.getApplicationNo();
        this.signingDate = application.getCreateTime();
        this.term = application.getTerm();
        this.borrower = application.getMember().getIdCard().getName();
        this.borrowerEmail = application.getMember().getEmail();
        this.borrowerMobile = application.getMember().getMobile();
        this.lender = lender;
        this.lenderEmail = lenderEmail;
        this.lenderMobile = lenderMobile;
        this.amount = application.getApproval().getAmt();
        this.apr = application.getApproval().getApr();
        this.loanId = loan.getId();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getRepaymentDay() {
        return repaymentDay;
    }

    public void setRepaymentDay(Integer repaymentDay) {
        this.repaymentDay = repaymentDay;
    }

    public String getBorrowerMobile() {
        return borrowerMobile;
    }

    public void setBorrowerMobile(String borrowerMobile) {
        this.borrowerMobile = borrowerMobile;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

    public void setBorrowerEmail(String borrowerEmail) {
        this.borrowerEmail = borrowerEmail;
    }

    public String getLenderMobile() {
        return lenderMobile;
    }

    public void setLenderMobile(String lenderMobile) {
        this.lenderMobile = lenderMobile;
    }

    public String getLenderEmail() {
        return lenderEmail;
    }

    public void setLenderEmail(String lenderEmail) {
        this.lenderEmail = lenderEmail;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreate_Time() {
        return create_Time;
    }

    public void setCreate_Time(Date create_Time) {
        this.create_Time = create_Time;
    }
}
