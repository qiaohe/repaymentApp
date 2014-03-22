package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
public class PreCredit implements java.io.Serializable{
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;
    private String imageBill;
    private Short crl;
    private Timestamp createTime;

    public PreCredit() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Basic
    @Column(name = "IMAGE_BILL")
    public String getImageBill() {
        return imageBill;
    }

    public void setImageBill(String imageBill) {
        this.imageBill = imageBill;
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
    @Column(name = "CREATE_TIME")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

}