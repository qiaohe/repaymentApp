package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "BILL", schema = "dbo", catalog = "REPAYMENTDB")
public class Bill {

    @Id
    @Column(name = "ID")
    private int id;

    @Basic
    @Column(name = "BANK")
    private Short bank;

    @Basic
    @Column(name = "TYPE")
    private Byte type;

    @Basic
    @Column(name = "CRL")
    private Short crl;

    @Basic
    @Column(name = "PAY_DUE")
    private Timestamp payDue;

    @Basic
    @Column(name = "AMT_RMB")
    private Double amtRmb;

    @Basic
    @Column(name = "AMT_USD")
    private Double amtUsd;

    @Basic
    @Column(name = "CYCLE_FROM")
    private Timestamp cycleFrom;

    @Basic
    @Column(name = "CYCLE_THRU")
    private Timestamp cycleThru;

    @Basic
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "IMAGE")
    private String image;

    @Basic
    @Column(name = "APPL_NO")
    private String applNo;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Short getBank() {
        return bank;
    }

    public void setBank(Short bank) {
        this.bank = bank;
    }


    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }


    public Short getCrl() {
        return crl;
    }

    public void setCrl(Short crl) {
        this.crl = crl;
    }


    public Timestamp getPayDue() {
        return payDue;
    }

    public void setPayDue(Timestamp payDue) {
        this.payDue = payDue;
    }


    public Double getAmtRmb() {
        return amtRmb;
    }

    public void setAmtRmb(Double amtRmb) {
        this.amtRmb = amtRmb;
    }


    public Double getAmtUsd() {
        return amtUsd;
    }

    public void setAmtUsd(Double amtUsd) {
        this.amtUsd = amtUsd;
    }


    public Timestamp getCycleFrom() {
        return cycleFrom;
    }

    public void setCycleFrom(Timestamp cycleFrom) {
        this.cycleFrom = cycleFrom;
    }


    public Timestamp getCycleThru() {
        return cycleThru;
    }

    public void setCycleThru(Timestamp cycleThru) {
        this.cycleThru = cycleThru;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
