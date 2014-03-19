package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
@Table(name = "ID_CARD", schema = "dbo", catalog = "MEMBER")
public class IdCard {
    private int id;
    private Integer memberId;
    private String idNo;
    private Byte sex;
    private Timestamp birthday;
    private String nationality;
    private String address;
    private String issuer;
    private Timestamp validFrom;
    private Timestamp validThru;
    private String imageFront;
    private String imageBack;
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
    @Column(name = "ID_NO")
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Basic
    @Column(name = "SEX")
    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "BIRTHDAY")
    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "NATIONALITY")
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Basic
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "ISSUER")
    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
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
    @Column(name = "IMAGE_FRONT")
    public String getImageFront() {
        return imageFront;
    }

    public void setImageFront(String imageFront) {
        this.imageFront = imageFront;
    }

    @Basic
    @Column(name = "IMAGE_BACK")
    public String getImageBack() {
        return imageBack;
    }

    public void setImageBack(String imageBack) {
        this.imageBack = imageBack;
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

        IdCard idCard = (IdCard) o;

        if (id != idCard.id) return false;
        if (address != null ? !address.equals(idCard.address) : idCard.address != null) return false;
        if (applNo != null ? !applNo.equals(idCard.applNo) : idCard.applNo != null) return false;
        if (birthday != null ? !birthday.equals(idCard.birthday) : idCard.birthday != null) return false;
        if (createTime != null ? !createTime.equals(idCard.createTime) : idCard.createTime != null) return false;
        if (idNo != null ? !idNo.equals(idCard.idNo) : idCard.idNo != null) return false;
        if (imageBack != null ? !imageBack.equals(idCard.imageBack) : idCard.imageBack != null) return false;
        if (imageFront != null ? !imageFront.equals(idCard.imageFront) : idCard.imageFront != null) return false;
        if (issuer != null ? !issuer.equals(idCard.issuer) : idCard.issuer != null) return false;
        if (memberId != null ? !memberId.equals(idCard.memberId) : idCard.memberId != null) return false;
        if (nationality != null ? !nationality.equals(idCard.nationality) : idCard.nationality != null) return false;
        if (sex != null ? !sex.equals(idCard.sex) : idCard.sex != null) return false;
        if (validFrom != null ? !validFrom.equals(idCard.validFrom) : idCard.validFrom != null) return false;
        if (validThru != null ? !validThru.equals(idCard.validThru) : idCard.validThru != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (idNo != null ? idNo.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (issuer != null ? issuer.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validThru != null ? validThru.hashCode() : 0);
        result = 31 * result + (imageFront != null ? imageFront.hashCode() : 0);
        result = 31 * result + (imageBack != null ? imageBack.hashCode() : 0);
        result = 31 * result + (applNo != null ? applNo.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
