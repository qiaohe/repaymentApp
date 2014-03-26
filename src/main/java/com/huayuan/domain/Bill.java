package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
public class Bill implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Basic
    @Column(name = "BANK")
    private Integer bank;

    @Basic
    @Column(name = "TYPE")
    private Enum type;

    @Basic
    @Column(name = "CRL")
    private Integer crl;

    @Basic
    @Column(name = "PAY_DUE")
    private Date payDue;

    @Basic
    @Column(name = "AMT_RMB")
    private Double amtRmb;

    @Basic
    @Column(name = "AMT_USD")
    private Double amtUsd;

    @Basic
    @Column(name = "CYCLE_FROM")
    private Date cycleFrom;

    @Basic
    @Column(name = "CYCLE_THRU")
    private Date cycleThru;

    @Basic
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "IMAGE")
    private String image;

    @Basic
    @Column(name = "APPL_NO")
    private String applNo;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public Bill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public Enum getType() {
        return type;
    }

    public void setType(Enum type) {
        this.type = type;
    }

    public Integer getCrl() {
        return crl;
    }

    public void setCrl(Integer crl) {
        this.crl = crl;
    }

    public Date getPayDue() {
        return payDue;
    }

    public void setPayDue(Date payDue) {
        this.payDue = payDue;
    }

    public Double getAmtRmb() {
        return amtRmb;
    }

    public void setAmtRmb(Double amtRmb) {
        this.amtRmb = amtRmb;
    }

    public Double getAmtUsd() {
        return amtUsd;
    }

    public void setAmtUsd(Double amtUsd) {
        this.amtUsd = amtUsd;
    }

    public Date getCycleFrom() {
        return cycleFrom;
    }

    public void setCycleFrom(Date cycleFrom) {
        this.cycleFrom = cycleFrom;
    }

    public Date getCycleThru() {
        return cycleThru;
    }

    public void setCycleThru(Date cycleThru) {
        this.cycleThru = cycleThru;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
