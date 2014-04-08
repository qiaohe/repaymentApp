package com.huayuan.domain.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "VALUE_BIN")
public class ValueBin implements Serializable {
    private static final long serialVersionUID = 5095339860747619966L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;


    @Column(name = "BANK")
    private String bank;


    @Column(name = "BANK_NO")
    private int bankNo;


    @Column(name = "CARD_NAME")
    private String cardName;


    @Column(name = "FULL_NAME")
    private String fullName;


    @Column(name = "CARD_LEVEL")
    @JsonIgnore
    private String cardLevel;


    @Column(name = "LEN_CARD")
    @JsonIgnore
    private int lenCard;


    @Column(name = "CARD_NO")
    private String cardNo;


    @Column(name = "LEN_BIN")
    @JsonIgnore
    private int lenBin;


    @Column(name = "BIN_NO")
    private String binNo;


    @Column(name = "TYPE")
    @JsonIgnore
    private String type;


    @Column(name = "IS_VALID")
    @JsonIgnore
    private Boolean isValid;


    @Column(name = "ICON")
    private String icon;


    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Date createTime;

    public ValueBin() {
        createTime = new Date();
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }


    public int getBankNo() {
        return bankNo;
    }

    public void setBankNo(int bankNo) {
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


    public int getLenCard() {
        return lenCard;
    }

    public void setLenCard(int lenCard) {
        this.lenCard = lenCard;
    }


    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


    public int getLenBin() {
        return lenBin;
    }

    public void setLenBin(int lenBin) {
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
