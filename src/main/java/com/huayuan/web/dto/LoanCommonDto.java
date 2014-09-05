package com.huayuan.web.dto;

import java.math.BigInteger;

/**
 * Created by Richard Xue on 14-5-21.
 */
public class LoanCommonDto {
    private BigInteger loanId;
    private String appNo;
    private String appDate;
    private String name;
    private String cardNo;
    private Short bank;
    private Double appAmt;
    private Double amt;
    private Double payAmt;
    private String transCode;
    private String transTime;
    private String confirmId;
    private String confirmDate;
    private String errorMsg;
    private Integer confirmStatus;

    public LoanCommonDto(BigInteger loanId, String appNo, String appDate, String name, String cardNo, Short bank,
                         Double appAmt,Double amt, Double payAmt, String transCode, String transTime, String confirmId,
                         String confirmDate, String errorMsg, Integer confirmStatus) {
        this.loanId = loanId;
        this.appNo = appNo;
        this.appDate = appDate;
        this.name = name;
        this.cardNo = cardNo;
        this.bank = bank;
        this.appAmt = appAmt;
        this.amt = amt;
        this.payAmt = payAmt;
        this.transCode = transCode;
        this.transTime = transTime;
        this.confirmId = confirmId;
        this.confirmDate = confirmDate;
        this.errorMsg = errorMsg;
        this.confirmStatus = confirmStatus;
    }

    public BigInteger getLoanId() {
        return loanId;
    }

    public void setLoanId(BigInteger loanId) {
        this.loanId = loanId;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Short getBank() {
        return bank;
    }

    public void setBank(Short bank) {
        this.bank = bank;
    }

    public Double getAppAmt() {
        return appAmt;
    }

    public void setAppAmt(Double appAmt) {
        this.appAmt = appAmt;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(Double payAmt) {
        this.payAmt = payAmt;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }
}
