package com.huayuan.domain.crawler.billparser;

import java.util.Date;

/**
 * Created by dell on 14-6-5.
 */
public class BillValue {
    private Double crl;
    private Date payDue;
    private Date cycleFrom;
    private Date cycleThru;
    private Double amtUsd;
    private Double amtRmb;

    public BillValue() {
    }

    public BillValue(Double crl, Date payDue, Date cycleFrom, Date cycleThru, Double amtUsd, Double amtRmb) {
        this.crl = crl;
        this.payDue = payDue;
        this.cycleFrom = cycleFrom;
        this.cycleThru = cycleThru;
        this.amtUsd = amtUsd;
        this.amtRmb = amtRmb;
    }

    public Double getCrl() {
        return crl;
    }

    public void setCrl(Double crl) {
        this.crl = crl;
    }

    public Date getPayDue() {
        return payDue;
    }

    public void setPayDue(Date payDue) {
        this.payDue = payDue;
    }

    public Date getCycleFrom() {
        return cycleFrom;
    }

    public void setCycleFrom(Date cycleFrom) {
        this.cycleFrom = cycleFrom;
    }

    public Date getCycleThru() {
        return cycleThru;
    }

    public void setCycleThru(Date cycleThru) {
        this.cycleThru = cycleThru;
    }

    public Double getAmtUsd() {
        return amtUsd;
    }

    public void setAmtUsd(Double amtUsd) {
        this.amtUsd = amtUsd;
    }

    public Double getAmtRmb() {
        return amtRmb;
    }

    public void setAmtRmb(Double amtRmb) {
        this.amtRmb = amtRmb;
    }
}
