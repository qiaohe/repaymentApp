package com.huayuan.domain.member;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "CREDITCARD")
public class CreditCard implements Serializable {

    private static final long serialVersionUID = -2172211295659368573L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "BANK")
    private int bank;

    @Basic
    @Column(name = "CARD_NO")
    private String cardNo;

    @Basic
    @Column(name = "TYPE")
    private Byte type;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "VALID_FROM")
    private Date validFrom;

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
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "PASSWORD")
    private String password;

    @Basic
    @Column(name = "STATUS")
    private Byte status;

    @Basic
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }


    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Transient
    public String getBinCode() {
        return StringUtils.substring(cardNo, 0, 6);
    }
}
