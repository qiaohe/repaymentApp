package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
public class CreditCard implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Basic
    @Column(name = "BANK")
    private Integer bank;

    @Basic
    @Column(name = "CARD_NO")
    private String cardNo;

    @Basic
    @Column(name = "TYPE")
    private Integer type;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "VALID_FROM")
    private Timestamp validFrom;

    @Basic
    @Column(name = "VALID_THRU")
    private Date validThru;

    @Basic
    @Column(name = "NAME_ENG")
    private String nameEng;

    @Basic
    @Column(name = "IMAGE")
    private String image;

    @Basic
    @Column(name = "APPL_NO")
    private String applNo;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public CreditCard() {
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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Timestamp validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidThru() {
        return validThru;
    }

    public void setValidThru(Date validThru) {
        this.validThru = validThru;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
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