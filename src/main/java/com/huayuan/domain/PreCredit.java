package com.huayuan.domain;

import com.google.common.base.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
public class PreCredit {
    private int id;
    private Integer memberId;
    private String imageBill;
    private Short crl;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(memberId, imageBill);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof PreCredit) {
            final PreCredit other = (PreCredit) obj;
            return Objects.equal(memberId, other.memberId) && Objects.equal(imageBill, other.imageBill);
        } else {
            return false;
        }
    }
}