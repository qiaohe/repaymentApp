package com.huayuan.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
public class Bill {
    private int id;
    private Integer memberId;
    private Short bank;
    private Byte type;
    private Short crl;
    private Timestamp payDue;
    private Double amtRmb;
    private Double amtUsd;
    private Timestamp cycleFrom;
    private Timestamp cycleThru;
    private String email;
    private String image;
    private String applNo;
    private Timestamp createTime;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MEMBER_ID")
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "BANK")
    public Short getBank() {
        return bank;
    }

    public void setBank(Short bank) {
        this.bank = bank;
    }

    @Basic
    @Column(name = "TYPE")
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "CRL")
    public Short getCrl() {
        return crl;
    }

    public void setCrl(Short crl) {
        this.crl = crl;
    }

    @Basic
    @Column(name = "PAY_DUE")
    public Timestamp getPayDue() {
        return payDue;
    }

    public void setPayDue(Timestamp payDue) {
        this.payDue = payDue;
    }

    @Basic
    @Column(name = "AMT_RMB")
    public Double getAmtRmb() {
        return amtRmb;
    }

    public void setAmtRmb(Double amtRmb) {
        this.amtRmb = amtRmb;
    }

    @Basic
    @Column(name = "AMT_USD")
    public Double getAmtUsd() {
        return amtUsd;
    }

    public void setAmtUsd(Double amtUsd) {
        this.amtUsd = amtUsd;
    }

    @Basic
    @Column(name = "CYCLE_FROM")
    public Timestamp getCycleFrom() {
        return cycleFrom;
    }

    public void setCycleFrom(Timestamp cycleFrom) {
        this.cycleFrom = cycleFrom;
    }

    @Basic
    @Column(name = "CYCLE_THRU")
    public Timestamp getCycleThru() {
        return cycleThru;
    }

    public void setCycleThru(Timestamp cycleThru) {
        this.cycleThru = cycleThru;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "IMAGE")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "APPL_NO")
    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }

    @Basic
    @Column(name = "CREATE_TIME")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (id != bill.id) return false;
        if (amtRmb != null ? !amtRmb.equals(bill.amtRmb) : bill.amtRmb != null) return false;
        if (amtUsd != null ? !amtUsd.equals(bill.amtUsd) : bill.amtUsd != null) return false;
        if (applNo != null ? !applNo.equals(bill.applNo) : bill.applNo != null) return false;
        if (bank != null ? !bank.equals(bill.bank) : bill.bank != null) return false;
        if (createTime != null ? !createTime.equals(bill.createTime) : bill.createTime != null) return false;
        if (crl != null ? !crl.equals(bill.crl) : bill.crl != null) return false;
        if (cycleFrom != null ? !cycleFrom.equals(bill.cycleFrom) : bill.cycleFrom != null) return false;
        if (cycleThru != null ? !cycleThru.equals(bill.cycleThru) : bill.cycleThru != null) return false;
        if (email != null ? !email.equals(bill.email) : bill.email != null) return false;
        if (image != null ? !image.equals(bill.image) : bill.image != null) return false;
        if (memberId != null ? !memberId.equals(bill.memberId) : bill.memberId != null) return false;
        if (payDue != null ? !payDue.equals(bill.payDue) : bill.payDue != null) return false;
        if (type != null ? !type.equals(bill.type) : bill.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (crl != null ? crl.hashCode() : 0);
        result = 31 * result + (payDue != null ? payDue.hashCode() : 0);
        result = 31 * result + (amtRmb != null ? amtRmb.hashCode() : 0);
        result = 31 * result + (amtUsd != null ? amtUsd.hashCode() : 0);
        result = 31 * result + (cycleFrom != null ? cycleFrom.hashCode() : 0);
        result = 31 * result + (cycleThru != null ? cycleThru.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (applNo != null ? applNo.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
