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
public class Precredit {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Precredit precredit = (Precredit) o;

        if (id != precredit.id) return false;
        if (createTime != null ? !createTime.equals(precredit.createTime) : precredit.createTime != null) return false;
        if (crl != null ? !crl.equals(precredit.crl) : precredit.crl != null) return false;
        if (imageBill != null ? !imageBill.equals(precredit.imageBill) : precredit.imageBill != null) return false;
        if (memberId != null ? !memberId.equals(precredit.memberId) : precredit.memberId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (imageBill != null ? imageBill.hashCode() : 0);
        result = 31 * result + (crl != null ? crl.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
