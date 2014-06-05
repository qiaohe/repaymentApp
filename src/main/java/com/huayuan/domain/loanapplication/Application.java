package com.huayuan.domain.loanapplication;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huayuan.domain.member.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "APPL")
public class Application {
    public static final Integer TEMPORARILY_STATUS = 99;
    public static final Integer APPROVED_STATUS = 5;
    @Id
    @Column(name = "APPL_NO")
    private String applicationNo;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    private Member member;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "application")
    private TelephoneVerification telephoneVerification;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "application")
    private AScore aScore;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "application")
    private Approval approval;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "application")
    private List<TelephoneTV> telephoneTVs = new ArrayList<>();

    @Column(name = "EXISTING_FLAG")
    private Integer existingFlag = 0;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ID")
    @JsonIgnore
    private IdCard idCard;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CC_ID")
    @JsonIgnore
    private CreditCard creditCard;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BILL_ID")
    private CreditCardBill creditCardBill;


    @Column(name = "AMT")
    private Double amt;


    @Column(name = "TERM")
    private Integer term;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "REPAY_TYPE")
    private RepaymentModeEnum repayType;


    @Column(name = "TITLE")
    private String title;

    @Column(name = "STATUS")
    private Integer status = 0;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPLY_TIME")
    private Date applyTime;

    public Application() {
        this.createTime = new Date();
        this.applyTime = new Date();
        status = 0;
    }

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

    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public CreditCardBill getCreditCardBill() {
        return creditCardBill;
    }

    public void setCreditCardBill(CreditCardBill creditCardBill) {
        this.creditCardBill = creditCardBill;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public List<TelephoneTV> getTelephoneTVs() {
        return telephoneTVs;
    }

    public void setTelephoneTVs(List<TelephoneTV> telephoneTVs) {
        this.telephoneTVs = telephoneTVs;
    }

    public boolean isApproved() {
        return getApproval().getDecision().equals("A");
    }

    public boolean isDeclined() {
        return getApproval().getDecision().equals("D");
    }

    @Transient
    @JsonIgnore
    public String getWeChatStatus(List<WhiteList> whiteList) {
        if (status < 5 || status == 99) return "4";
        if (status == 5) {
            if (isApproved()) return "5.1";
            if (isDeclined()) {
                for (WhiteList w : whiteList) {
                    if (w.getMemberId().equals(getMember().getId())) return "3.1";
                }
                return "5.2";
            }
        }
        return String.valueOf(status);
    }
}
