package com.huayuan.domain.accounting;

import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.Staff;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Johnson on 4/23/14.
 */
@Entity
@Table(name = "PAY")
public class Pay implements Serializable {
    private static final long serialVersionUID = -5049726809728605176L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BID")
    private Loan loan;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "APP_NO")
    private Application application;
    @Column(name = "PAY_AMT")
    private Double payAmt;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "STAFF_ID", nullable = true)
    private Staff staff;
    @Column(name = "TRANS_CODE")
    private String code;
    @Column(name = "TRANS_TIME")
    private Date transferTime;
    @Column(name = "CONFORMED")
    private Integer confirm = 0;
    @Column(name = "CONFORMED_DATE")
    private Date confirmDate;
    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;
    @Column(name = "CONFORMED_ID")
    private String confirmID;

    public String getConfirmID() {
        return confirmID;
    }

    public void setConfirmID(String confirmID) {
        this.confirmID = confirmID;
    }

    public Pay() {
        this.payAmt = 0d;
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

    public Double getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(Double transferAmt) {
        this.payAmt = transferAmt;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
