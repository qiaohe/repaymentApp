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
public class Creditcard {
    private int id;
    private Integer memberId;
    private Short bank;
    private String cardNo;
    private Byte type;
    private String name;
    private Timestamp validFrom;
    private Timestamp validThru;
    private String nameEng;
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
    @Column(name = "CARD_NO")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "VALID_FROM")
    public Timestamp getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Timestamp validFrom) {
        this.validFrom = validFrom;
    }

    @Basic
    @Column(name = "VALID_THRU")
    public Timestamp getValidThru() {
        return validThru;
    }

    public void setValidThru(Timestamp validThru) {
        this.validThru = validThru;
    }

    @Basic
    @Column(name = "NAME_ENG")
    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
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

        Creditcard that = (Creditcard) o;

        if (id != that.id) return false;
        if (applNo != null ? !applNo.equals(that.applNo) : that.applNo != null) return false;
        if (bank != null ? !bank.equals(that.bank) : that.bank != null) return false;
        if (cardNo != null ? !cardNo.equals(that.cardNo) : that.cardNo != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameEng != null ? !nameEng.equals(that.nameEng) : that.nameEng != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) return false;
        if (validThru != null ? !validThru.equals(that.validThru) : that.validThru != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        result = 31 * result + (cardNo != null ? cardNo.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validThru != null ? validThru.hashCode() : 0);
        result = 31 * result + (nameEng != null ? nameEng.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (applNo != null ? applNo.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
