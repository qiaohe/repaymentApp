package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "APPROVAL", schema = "dbo", catalog = "REPAYMENTDB")
public class Approval {

    @Id
    @Column(name = "APPL_NO")
    private String applNo;

    @Basic
    @Column(name = "DECISION")
    private String decision;

    @Basic
    @Column(name = "AMT")
    private Double amt;

    @Basic
    @Column(name = "APR")
    private Double apr;

    @Basic
    @Column(name = "TERM")
    private Byte term;

    @Basic
    @Column(name = "REPAY_TYPE")
    private Byte repayType;

    @Basic
    @Column(name = "PROFILE")
    private String profile;

    @Basic
    @Column(name = "REASON_1")
    private String reason1;

    @Basic
    @Column(name = "REASON_2")
    private String reason2;

    @Basic
    @Column(name = "REASON_3")
    private String reason3;

    @Basic
    @Column(name = "OPINION")
    private String opinion;

    @Basic
    @Column(name = "CREDITOR")
    private String creditor;

    @Basic
    @Column(name = "CLASS")
    private String clazz;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;


    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }


    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }


    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }


    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
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


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }


    public String getReason1() {
        return reason1;
    }

    public void setReason1(String reason1) {
        this.reason1 = reason1;
    }


    public String getReason2() {
        return reason2;
    }

    public void setReason2(String reason2) {
        this.reason2 = reason2;
    }


    public String getReason3() {
        return reason3;
    }

    public void setReason3(String reason3) {
        this.reason3 = reason3;
    }


    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }


    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }


    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
