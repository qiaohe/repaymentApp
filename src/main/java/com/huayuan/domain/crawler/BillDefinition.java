package com.huayuan.domain.crawler;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dell on 14-4-6.
 */
@XmlRootElement(name="bill")
public class BillDefinition {
    private String bank;
    private String title;
    private String amtRmb;
    private String amtUsd;
    private String crl;
    private String cycleFrom;
    private String cycleThru;
    private String payDue;

    public BillDefinition() {
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmtRmb() {
        return amtRmb;
    }

    public void setAmtRmb(String amtRmb) {
        this.amtRmb = amtRmb;
    }

    public String getAmtUsd() {
        return amtUsd;
    }

    public void setAmtUsd(String amtUsd) {
        this.amtUsd = amtUsd;
    }

    public String getCrl() {
        return crl;
    }

    public void setCrl(String crl) {
        this.crl = crl;
    }

    public String getCycleFrom() {
        return cycleFrom;
    }

    public void setCycleFrom(String cycleFrom) {
        this.cycleFrom = cycleFrom;
    }

    public String getCycleThru() {
        return cycleThru;
    }

    public void setCycleThru(String cycleThru) {
        this.cycleThru = cycleThru;
    }

    public String getPayDue() {
        return payDue;
    }

    public void setPayDue(String payDue) {
        this.payDue = payDue;
    }
}
