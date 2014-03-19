package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NAME")
    private String name;
    @Column(name = "MOBILE")
    private String mobile;
    @Column(name = "email")
    private String email;
    @Column(name = "WC_NO")
    private String wcNo;
    @Column(name = "WC_USER_NAME")
    private String wcUserName;
    @Column(name = "WC_PROVINCE")
    private String wcProvince;
    @Column(name = "WC_CITY")
    private String wcCity;
    @Column(name = "WC_SIGNATURE")
    private String wcSignature;
    @Column(name = "TYPE")
    private Byte type;
    @Column(name = "CRL")
    private Short crl;
    @Column(name = "CRL_AVL")
    private Short crlAvl;
    @Column(name = "LAST_APPL_NO")
    private Integer lastApplNo;
    @Column(name = "LAST_SCORE")
    private Integer lastScore;
    @Column(name = "LAST_DECISION")
    private Byte lastDecision;
    @Column(name = "LAST_PBOC_BACK_TIME")
    private Timestamp lastPbocBackTime;
    @Column(name = "POINTS")
    private Short points;
    @Column(name = "STATUS")
    private Byte status;
    @Column(name = "BLOCK_CODE")
    private String blockCode;
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWcNo() {
        return wcNo;
    }

    public void setWcNo(String wcNo) {
        this.wcNo = wcNo;
    }

    public String getWcUserName() {
        return wcUserName;
    }

    public void setWcUserName(String wcUserName) {
        this.wcUserName = wcUserName;
    }

    public String getWcProvince() {
        return wcProvince;
    }

    public void setWcProvince(String wcProvince) {
        this.wcProvince = wcProvince;
    }

    public String getWcCity() {
        return wcCity;
    }

    public void setWcCity(String wcCity) {
        this.wcCity = wcCity;
    }

    public String getWcSignature() {
        return wcSignature;
    }

    public void setWcSignature(String wcSignature) {
        this.wcSignature = wcSignature;
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

    public Short getCrlAvl() {
        return crlAvl;
    }

    public void setCrlAvl(Short crlAvl) {
        this.crlAvl = crlAvl;
    }

    public Integer getLastApplNo() {
        return lastApplNo;
    }

    public void setLastApplNo(Integer lastApplNo) {
        this.lastApplNo = lastApplNo;
    }

    public Integer getLastScore() {
        return lastScore;
    }

    public void setLastScore(Integer lastScore) {
        this.lastScore = lastScore;
    }

    public Byte getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(Byte lastDecision) {
        this.lastDecision = lastDecision;
    }

    public Timestamp getLastPbocBackTime() {
        return lastPbocBackTime;
    }

    public void setLastPbocBackTime(Timestamp lastPbocBackTime) {
        this.lastPbocBackTime = lastPbocBackTime;
    }

    public Short getPoints() {
        return points;
    }

    public void setPoints(Short points) {
        this.points = points;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
