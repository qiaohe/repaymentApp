package com.huayuan.domain;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "APPL", schema = "dbo", catalog = "REPAYMENTDB")
public class Appl {

    @Id
    @Column(name = "APPL_NO")
    private String applNo;

    @Basic
    @Column(name = "MEMBER_ID")
    private Integer memberId;

    @Basic
    @Column(name = "EXSTING_FLAG")
    private Byte exstingFlag;

    @Basic
    @Column(name = "ID_ID")
    private Integer idId;

    @Basic
    @Column(name = "CC_ID")
    private Integer ccId;

    @Basic
    @Column(name = "BILL_ID")
    private Integer billId;

    @Basic
    @Column(name = "BMB_ID")
    private Integer bmbId;
    @Basic
    @Column(name = "AMT")
    private Double amt;

    @Basic
    @Column(name = "TERM")
    private Byte term;

    @Basic
    @Column(name = "REPAY_TYPE")
    private Byte repayType;

    @Basic
    @Column(name = "TITLE")
    private String title;

    @Basic
    @Column(name = "PRE_SCORE")
    private String preScore;

    @Basic
    @Column(name = "STATUS")
    private Byte status;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;
    private String id;

    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Byte getExstingFlag() {
        return exstingFlag;
    }

    public void setExstingFlag(Byte exstingFlag) {
        this.exstingFlag = exstingFlag;
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

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Byte getTerm() {
        return term;
    }

    public void setTerm(Byte term) {
        this.term = term;
    }

    public Byte getRepayType() {
        return repayType;
    }

    public void setRepayType(Byte repayType) {
        this.repayType = repayType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreScore() {
        return preScore;
    }

    public void setPreScore(String preScore) {
        this.preScore = preScore;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
