package com.huayuan.domain;


import org.hibernate.engine.spi.CascadeStyle;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
public class Member implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ORG_FLAG")
    private Integer orgFlag;

    @Column(name = "NAME")
    private String name;

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
    private Enum type;

    @Column(name = "CRL")
    private Integer crl;

    @Column(name = "CRL_AVL")
    private Integer crlAvl;

    @Column(name = "LAST_APPL_NO")
    private Integer lastApplNo;

    @Column(name = "LAST_SCORE")
    private Integer lastScore;

    @Column(name = "LAST_RATING")
    private String lastRating;

    @Column(name = "LAST_DECISION")
    private Integer lastDecision;

    @Column(name = "LAST_REASON_1")
    private String lastReason1;

    @Column(name = "LAST_REASON_2")
    private String lastReason2;

    @Column(name = "LAST_REASON_3")
    private String lastReason3;

    @Column(name = "LAST_PBOC_BACK_TIME")
    private Date lastPbocBackTime;

    @Column(name = "POINTS")
    private Integer points;

    @Column(name = "STATUS")
    private Enum status;

    @Column(name = "BLOCK_CODE")
    private String blockCode;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "member")
    private Set<CreditCard> creditCards = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "member")
    private Set<Bill> bills = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "member")
    private Set<BillMailbox> billMailboxes = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "member")
    private Set<PreCredit> preCredits = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
    private IdCard idCard;

    public Member() {
    }

    public Member(String wcNo) {
        this.wcNo = wcNo;
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

    public Integer getCrlAvl() {
        return crlAvl;
    }

    public void setCrlAvl(Integer crlAvl) {
        this.crlAvl = crlAvl;
    }

    public Integer getLastApplNo() {
        return lastApplNo;
    }

    public void setLastApplNo(Integer lastApplNo) {
        this.lastApplNo = lastApplNo;
    }

    public Integer getLastScore() {
        return lastScore;
    }

    public void setLastScore(Integer lastScore) {
        this.lastScore = lastScore;
    }

    public String getLastRating() {
        return lastRating;
    }

    public void setLastRating(String lastRating) {
        this.lastRating = lastRating;
    }

    public Integer getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(Integer lastDecision) {
        this.lastDecision = lastDecision;
    }

    public String getLastReason1() {
        return lastReason1;
    }

    public void setLastReason1(String lastReason1) {
        this.lastReason1 = lastReason1;
    }

    public String getLastReason2() {
        return lastReason2;
    }

    public void setLastReason2(String lastReason2) {
        this.lastReason2 = lastReason2;
    }

    public String getLastReason3() {
        return lastReason3;
    }

    public void setLastReason3(String lastReason3) {
        this.lastReason3 = lastReason3;
    }

    public Date getLastPbocBackTime() {
        return lastPbocBackTime;
    }

    public void setLastPbocBackTime(Date lastPbocBackTime) {
        this.lastPbocBackTime = lastPbocBackTime;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
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

    public Set<Bill> getBills() {
        return bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    public void addBillMailbox(BillMailbox billMailbox) {
        if (!billMailboxes.contains(billMailbox)) {
            billMailboxes.add(billMailbox);
        }
    }

    public void removeMailbox(BillMailbox billMailbox) {
        if (billMailboxes.contains(billMailbox)) {
            billMailboxes.remove(billMailbox);
        }
    }

    public Set<BillMailbox> getBillMailboxes() {
        return billMailboxes;
    }

    public void setBillMailboxes(Set<BillMailbox> billMailboxes) {
        this.billMailboxes = billMailboxes;
    }

    public void addBill(Bill bill) {
        if (!bills.contains(bill)) {
            bills.add(bill);
        }
    }

    public void removeBill(Bill bill) {
        if (bills.contains(bill)) {
            bills.remove(bill);
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
}
