package com.huayuan.domain.accounting.core;

import java.util.Date;

/**
 * Created by dell on 14-4-8.
 */
public class RepayItem {
    private int termNo;
    private Date dueDate;
    private Double duePrincipal;
    private Double dueInterest;
    private Double restPrincipal;

    public int getTermNo() {
        return termNo;
    }

    public void setTermNo(int termNo) {
        this.termNo = termNo;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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

    public Double getRestPrincipal() {
        return restPrincipal;
    }

    public void setRestPrincipal(Double restPrincipal) {
        this.restPrincipal = restPrincipal;
    }
}
