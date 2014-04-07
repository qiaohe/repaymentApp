package com.huayuan.domain.member;


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
    private Short bank;

    @Column(name = "SOURCE")
    @Enumerated
    private BillSourceEnum source;

    @Column(name = "CRL")
    private Long crl;

    @Column(name = "PAY_DUE")
    @Temporal(TemporalType.DATE)
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
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(int Long) {
        this.id = id;
    }

    public Short getBank() {
        return bank;
    }

    public void setBank(Short bank) {
        this.bank = bank;
    }

    public BillSourceEnum getSource() {
        return source;
    }

    public void setSource(BillSourceEnum source) {
        this.source = source;
    }

    public Long getCrl() {
        return crl;
    }

    public void setCrl(Long crl) {
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
}
