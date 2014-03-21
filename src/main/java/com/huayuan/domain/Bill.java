package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
public class Bill implements java.io.Serializable {
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;
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

    public Bill() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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
}
