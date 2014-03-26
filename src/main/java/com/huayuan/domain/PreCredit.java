package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
public class PreCredit implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(name = "ID_ID")
    private Integer idId;

    @Column(name = "CC_ID")
    private Integer ccId;

    @Column(name = "BILL_ID")
    private Integer billId;

    @Column(name = "BMB_ID")
    private Integer bmbId;

    @Basic
    @Column(name = "CRL")
    private Short crl;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public PreCredit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getIdId() {
        return idId;
    }

    public void setIdId(Integer idId) {
        this.idId = idId;
    }

    public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getBmbId() {
        return bmbId;
    }

    public void setBmbId(Integer bmbId) {
        this.bmbId = bmbId;
    }

    public Short getCrl() {
        return crl;
    }

    public void setCrl(Short crl) {
        this.crl = crl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}