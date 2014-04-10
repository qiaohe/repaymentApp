package com.huayuan.domain.loanapplication;


import com.huayuan.domain.member.Member;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "APPL")
public class Application {
    @Id
    @Column(name = "APPL_NO")
    private String applicationNo;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "application")
    private TelephoneVerification telephoneVerification;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "application")
    private AScore aScore;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "application")
    private Approval approval;

    @Column(name = "EXISTING_FLAG")
    private Integer existingFlag;


    @Column(name = "ID_ID")
    private Long idId;


    @Column(name = "CC_ID")
    private Long ccId;


    @Column(name = "BILL_ID")
    private Long billId;


    @Column(name = "AMT")
    private Double amt;


    @Column(name = "TERM")
    private Integer term;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "REPAY_TYPE")
    private RepaymentModeEnum repayType;


    @Column(name = "TITLE")
    private String title;


    @Column(name = "PRE_SCORE")
    private Double preScore;

    @Column(name = "PRE_RATING")
    private String preRating;

    @Column(name = "STATUS")
    private Integer status;


    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getExistingFlag() {
        return existingFlag;
    }

    public void setExistingFlag(Integer existingFlag) {
        this.existingFlag = existingFlag;
    }

    public Long getIdId() {
        return idId;
    }

    public void setIdId(Long idId) {
        this.idId = idId;
    }

    public Long getCcId() {
        return ccId;
    }

    public void setCcId(Long ccId) {
        this.ccId = ccId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public RepaymentModeEnum getRepayType() {
        return repayType;
    }

    public void setRepayType(RepaymentModeEnum repayType) {
        this.repayType = repayType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPreScore() {
        return preScore;
    }

    public void setPreScore(Double preScore) {
        this.preScore = preScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public TelephoneVerification getTelephoneVerification() {
        return telephoneVerification;
    }

    public void setTelephoneVerification(TelephoneVerification telphoneVerification) {
        this.telephoneVerification = telphoneVerification;
    }

    public AScore getaScore() {
        return aScore;
    }

    public void setaScore(AScore aScore) {
        this.aScore = aScore;
    }

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }
}
