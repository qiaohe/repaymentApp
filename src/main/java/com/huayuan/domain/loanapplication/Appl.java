package com.huayuan.domain.loanapplication;


import com.huayuan.domain.member.Member;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "APPL")
public class Appl {
    @Id
    @Column(name = "APPL_NO")
    private String APPL_NO;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    private Member member;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "appl")
    private ApplTv applTv;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "appl")
    private AScore aScore;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "appl")
    private Approval approval;

    @Basic
    @Column(name = "ISNEW")
    private boolean isNew;

    @Basic
    @Column(name = "ID_ID")
    private Long idId;

    @Basic
    @Column(name = "CC_ID")
    private Long ccId;

    @Basic
    @Column(name = "BILL_ID")
    private Long billId;

    @Basic
    @Column(name = "AMT")
    private Double amt;

    @Basic
    @Column(name = "TERM")
    private int term;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "REPAY_TYPE")
    private RepaymentModeEnum repayType;

    @Basic
    @Column(name = "TITLE")
    private String title;

    @Basic
    @Column(name = "PRE_SCORE")
    private String preScore;

    @Basic
    @Column(name = "STATUS")
    private int status;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public String getAPPL_NO() {
        return APPL_NO;
    }

    public void setAPPL_NO(String APPL_NO) {
        this.APPL_NO = APPL_NO;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
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

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
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

    public String getPreScore() {
        return preScore;
    }

    public void setPreScore(String preScore) {
        this.preScore = preScore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ApplTv getApplTv() {
        return applTv;
    }

    public void setApplTv(ApplTv applTv) {
        this.applTv = applTv;
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
