package com.huayuan.domain.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huayuan.domain.crawler.BillEmail;
import com.huayuan.domain.crawler.billparser.BillValue;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "BILL")
public class CreditCardBill implements Serializable {
    private static final long serialVersionUID = -890460871287898338L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BANK")
    private Integer bank;

    @Column(name = "SOURCE")
    @Enumerated
    private BillSourceEnum source;

    @Column(name = "CRL")
    private Double crl;

    @Column(name = "PAY_DUE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDue;

    @Column(name = "AMT_RMB")
    private Double amtRmb;

    @Column(name = "AMT_USD")
    private Double amtUsd;

    @Column(name = "CYCLE_FROM")
    private Date cycleFrom;

    @Column(name = "CYCLE_THRU")
    private Date cycleThru;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(Integer Long) {
        this.id = id;
    }

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public BillSourceEnum getSource() {
        return source;
    }

    public void setSource(BillSourceEnum source) {
        this.source = source;
    }

    public Double getCrl() {
        return crl;
    }

    public void setCrl(Double crl) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public static CreditCardBill valueOf(final BillValue billValue, final BillEmail billEmail, final Integer bankId) {
        CreditCardBill result = new CreditCardBill();
        result.setCrl(billValue.getCrl());
        result.setAmtUsd(billValue.getAmtUsd());
        result.setAmtRmb(billValue.getAmtRmb());
        result.setCycleThru(billValue.getCycleThru());
        result.setCycleFrom(billValue.getCycleFrom());
        result.setSource(BillSourceEnum.CRAWLER);
        result.setCreateTime(new Date());
        result.setPayDue(result.getPayDue());
        result.setEmail(billEmail.getEmail());
        result.setBank(bankId);
        return result;

    }
}
