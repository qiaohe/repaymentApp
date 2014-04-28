package com.huayuan.domain.member;


import com.huayuan.domain.loanapplication.CreditResult;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
@Table(name = "MEMBER")
public class Member implements Serializable {

    private static final long serialVersionUID = 4541559421896160856L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ORG_FLAG")
    private Integer orgFlag;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SEX")
    @Enumerated
    private SexEnum sex;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "EDUCATION")
    private Integer education;

    @Column(name = "INDUSTRY")
    private Integer industry;

    @Column(name = "WC_NO")
    private String wcNo;

    @Column(name = "WC_USER_NAME")
    private String wcUserName;

    @Column(name = "WC_PROVINCE")
    private String wcProvince;

    @Column(name = "WC_CITY")
    private String wcCity;

    @Column(name = "WC_SIGNATURE")
    private String wcSignature;

    @Column(name = "TYPE")
    @Enumerated(EnumType.ORDINAL)
    private MemberKindEnum type;

    @Column(name = "POINTS")
    private Integer poIntegers;

    @Column(name = "STATUS")
    @Enumerated
    private MemberStatusEnum status;

    @Column(name = "BLOCK_CODE")
    private String blockCode;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "PRE_CRL")
    private Integer preCrl;

    @Column(name = "PRE_SCORE")
    private Double preScore;

    @Column(name = "PRE_RATING")
    private String preRating;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private Set<CreditCard> creditCards = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private Set<CreditCardBill> creditCardBills = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private Set<PreCredit> preCredits = new HashSet<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private IdCard idCard;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "member")
    private CreditResult creditResult;


    public Member() {
        createTime = new Date();
        this.status = MemberStatusEnum.NORMAL;
        this.createTime = new Date();
        this.blockCode = StringUtils.EMPTY;
    }

    public Member(String wcNo) {
        this();
        this.wcNo = wcNo;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getOrgFlag() {
        return orgFlag;
    }

    public void setOrgFlag(Integer orgFlag) {
        this.orgFlag = orgFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public String getWcNo() {
        return wcNo;
    }

    public void setWcNo(String wcNo) {
        this.wcNo = wcNo;
    }

    public String getWcUserName() {
        return wcUserName;
    }

    public void setWcUserName(String wcUserName) {
        this.wcUserName = wcUserName;
    }

    public String getWcProvince() {
        return wcProvince;
    }

    public void setWcProvince(String wcProvince) {
        this.wcProvince = wcProvince;
    }

    public String getWcCity() {
        return wcCity;
    }

    public void setWcCity(String wcCity) {
        this.wcCity = wcCity;
    }

    public String getWcSignature() {
        return wcSignature;
    }

    public void setWcSignature(String wcSignature) {
        this.wcSignature = wcSignature;
    }

    public MemberKindEnum getType() {
        return type;
    }

    public void setType(MemberKindEnum type) {
        this.type = type;
    }

    public Integer getPoIntegers() {
        return poIntegers;
    }

    public void setPoIntegers(Integer poIntegers) {
        this.poIntegers = poIntegers;
    }

    public MemberStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MemberStatusEnum status) {
        this.status = status;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }

    public Integer getPreCrl() {
        return preCrl;
    }

    public void setPreCrl(Integer preCrl) {
        this.preCrl = preCrl;
    }

    public Double getPreScore() {
        return preScore;
    }

    public void setPreScore(Double preScore) {
        this.preScore = preScore;
    }

    public String getPreRating() {
        return preRating;
    }

    public void setPreRating(String preRating) {
        this.preRating = preRating;
    }

    public void addCreditCard(CreditCard creditCard) {
        if (!creditCards.contains(creditCard)) {
            creditCards.add(creditCard);
        }
    }

    public void removeCreditCard(CreditCard creditCard) {
        if (creditCards.contains(creditCard)) {
            creditCards.remove(creditCard);
        }
    }

    public Set<CreditCardBill> getCreditCardBills() {
        return creditCardBills;
    }

    public void setCreditCardBills(Set<CreditCardBill> creditCardBills) {
        this.creditCardBills = creditCardBills;
    }


    public void addBill(CreditCardBill creditCardBill) {
        if (!creditCardBills.contains(creditCardBill)) {
            creditCardBills.add(creditCardBill);
        }
    }

    public void removeBill(CreditCardBill creditCardBill) {
        if (creditCardBills.contains(creditCardBill)) {
            creditCardBills.remove(creditCardBill);
        }
    }

    public Set<PreCredit> getPreCredits() {
        return preCredits;
    }

    public void setPreCredits(Set<PreCredit> preCredits) {
        this.preCredits = preCredits;
    }

    public void removePreCredit(PreCredit preCredit) {
        if (preCredits.contains(preCredit)) {
            preCredits.remove(preCredit);
        }
    }

    public void addPreCredit(PreCredit preCredit) {
        if (!preCredits.contains(preCredit)) {
            preCredits.add(preCredit);
        }
    }

    public CreditResult getCreditResult() {
        return creditResult;
    }

    public void setCreditResult(CreditResult creditResult) {
        this.creditResult = creditResult;
    }
}
