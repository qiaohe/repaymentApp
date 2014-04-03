package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "VALUE_BIN", schema = "dbo", catalog = "REPAYMENTDB")
public class ValueBin {

    @Id
    @Column(name = "ID")
    private int id;

    @Basic
    @Column(name = "BANK")
    private String bank;

    @Basic
    @Column(name = "BANK_NO")
    private String bankNo;

    @Basic
    @Column(name = "CARD_NAME")
    private String cardName;

    @Basic
    @Column(name = "FULL_NAME")
    private String fullName;

    @Basic
    @Column(name = "CARD_LEVEL")
    private String cardLevel;

    @Basic
    @Column(name = "LEN_CARD")
    private Byte lenCard;

    @Basic
    @Column(name = "CARD_NO")
    private String cardNo;

    @Basic
    @Column(name = "LEN_BIN")
    private Byte lenBin;

    @Basic
    @Column(name = "BIN_NO")
    private String binNo;

    @Basic
    @Column(name = "TYPE")
    private String type;

    @Basic
    @Column(name = "IS_VALID")
    private Boolean isValid;

    @Basic
    @Column(name = "ICON")
    private String icon;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }


    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }


    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(String cardLevel) {
        this.cardLevel = cardLevel;
    }


    public Byte getLenCard() {
        return lenCard;
    }

    public void setLenCard(Byte lenCard) {
        this.lenCard = lenCard;
    }


    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


    public Byte getLenBin() {
        return lenBin;
    }

    public void setLenBin(Byte lenBin) {
        this.lenBin = lenBin;
    }


    public String getBinNo() {
        return binNo;
    }

    public void setBinNo(String binNo) {
        this.binNo = binNo;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
