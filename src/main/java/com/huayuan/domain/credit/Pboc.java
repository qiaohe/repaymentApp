package com.huayuan.domain.credit;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 14-4-28.
 */
@Entity
@Table(name = "PBOC")
public class Pboc implements Serializable {
    public static final int CHANGE_ID = 5;
    private static final long serialVersionUID = -8746160739489972542L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")

    private String name;

    @Column(name = "CERTNO")
    private String certNo;

    @Column(name = "REPORTCREATETIME")
    private String reportCreateTime;

    @Column(name = "NEW_NAME")
    private String newName;

    @Column(name = "NEW_CERTNO")
    private String newCertNo;

    @Column(name = "NEW_REPORTCREATETIME")
    private String newReportCreateTime;

    @Column(name = "MARITALSTATE")
    private String maritalState;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "MOBILE_CITY")
    private String mobileCity;

    @Column(name = "OFFICETELEPHONENO")
    private String officeTelephoneNo;

    @Column(name = "HOMETELEPHONENO")
    private String homeTelephoneNo;

    @Column(name = "EDUDEGREE")
    private String eduDegree;

    @Column(name = "ID_IMAGE")
    private String idImage;

    @Column(name = "REGISTEREDADDRESS")
    private String registeredAddress;

    @Column(name = "PARTNER_NAME")
    private String partnerName;

    @Column(name = "PARTNER_CERTNO")
    private String partnerCertNo;

    @Column(name = "PARTNER_TELEPHONENO")
    private String partnerTelephoneNo;

    @Column(name = "HOME_CITY")
    private String homeCity;

    @Column(name = "HOME_ADDRESS")
    private String homeAddress;

    @Column(name = "HOME_RESIDENCETYPE")
    private String homeResidenceType;

    @Column(name = "ADDRESS_GETTIME")
    private String addressGetTime;

    @Column(name = "EMPLOYER")
    private String employer;

    @Column(name = "EMPLOYERCITY")
    private String employerCity;

    @Column(name = "EMPLOYERADDRESS")
    private String employerAddress;

    @Column(name = "OCCUPATION")
    private String occupation;

    @Column(name = "INDUSTRY")
    private String industry;

    @Column(name = "INDUSTRY_ASSESS")
    private String industryAddress;

    @Column(name = "DUTY")
    private String duty;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "STARTYEAR")
    private String startYear;

    @Column(name = "INDUSTRY_YEAR")
    private String industryYear;

    @Column(name = "EMPLOYER_GETTIME")
    private String employerGetTime;

    @Column(name = "HISGETTING")
    private String hisGetting;

    @Column(name = "Y_REGISTERDATE")
    private String yRegisterDate;

    @Column(name = "Y_WORKDATE")
    private String yWorkDate;

    @Column(name = "Y_OWNBASICMONEY")
    private Double yOwnBasicMoney;

    @Column(name = "Y_STATE")
    private String yState;

    @Column(name = "Y_MONEY")
    private Double yMoney;

    @Column(name = "Y_ORGANNAME")
    private Integer yOrganName = 0;

    @Column(name = "PAUSEREASON")
    private String pauseReason;

    @Column(name = "Y_GETTIME")
    private String yGetTime;

    @Column(name = "REGISTERDATE")
    private String registerDate;

    @Column(name = "FIRSTMONTH")
    private String firstMonth;

    @Column(name = "TOMONTH")
    private String toMonth;

    @Column(name = "STATE")
    private String state;

    @Column(name = "PAY")
    private Double pay;

    @Column(name = "ORGANNAME")
    private Integer organName = 0;

    @Column(name = "GETTIME")
    private String getTime;

    @Column(name = "FIRSTLOANMONTH")
    private String firstLoanMonth;

    @Column(name = "LOAN_COUNT")
    private Integer loanCount;

    @Column(name = "LOANHIGHESTOVERDUEAMOUNTPERMON")
    private Double loanHighestOverdueAmountPerMon;

    @Column(name = "LOAN_MAXDURATION")
    private Integer loanMaxDuration;

    @Column(name = "CARD_COUNT")
    private Integer cardCount;

    @Column(name = "CARD_HIGHESTOVERDUEAMOUNTPERMON")
    private Integer cardHighestOverdueAmountPerMon;

    @Column(name = "CARD_MAXDURATION")
    private Integer cardMaxDuration;

    @Column(name = "CARD_OVERDUEPERYEAR")
    private Integer cardOverDuePerYear;

    @Column(name = "CARD_OVERDUENUM")
    private Integer cardOverDueNum;

    @Column(name = "SEMICARD_COUNT")
    private Integer semiCardCount;

    @Column(name = "SEMICARD_HIGHESTOVERDUEAMOUNTPERMON")
    private Double semiCardHighestOverdueAmountPerMon;

    @Column(name = "SEMICARD_MAXDURATION")
    private Double semiCardMaxDuration;

    @Column(name = "LOAN_ACCOUNTCOUNT")
    private Double loanAccountCount;

    @Column(name = "LOAN_CREDITLIMIT")
    private Double loanCreditLimit;

    @Column(name = "LOAN_BALANCE")
    private Double loanBalance;

    @Column(name = "LOAN_LATEST6MONTHUSEDAVGAMOUNT")
    private Double loanLatest6MonthUsedAvgAmount;

    @Column(name = "CARD_ORG")
    private Integer cardOrg;

    @Column(name = "CARD_ACCOUNTCOUNT")
    private Integer cardAccountCount;

    @Column(name = "CARD_CREDITLIMIT")
    private Integer cardCreditLimit;

    @Column(name = "CARD_AVG_CREDITLIMIT")
    private Double cardAvgCreditLimit;

    @Column(name = "CARD_MAXCREDITLIMITPERORG")
    private Double cardMaxCreditLimitPerOrg;

    @Column(name = "CARD_USEDCREDITLIMIT")
    private Double cardUsedCreditLimit;

    @Column(name = "CARD_LATEST6MONTHUSEDAVGAMOUNT")
    private Double cardLatest6MonthUsedAvgAmount;

    @Column(name = "SEMICARD_ORG")
    private Double semiCardOrg;

    @Column(name = "SEMICARD_ACCOUNTCOUNT")
    private Double semiCardAccountCount;

    @Column(name = "SEMICARD_CREDITLIMIT")
    private Double semicardCreditLimit;

    @Column(name = "SEMICARD_AVG_CREDITLIMIT")
    private Double semiCardAvgCreditlimit;

    @Column(name = "SEMICARD_MAXCREDITLIMITPERORG")
    private Double semiCardMaxCreditLimitPerOrg;

    @Column(name = "SEMICARD_MINCREDITLIMITPERORG")
    private Double semicardMinCreditLimitPerOrg;

    @Column(name = "CARD_MINCREDITLIMITPERORG")
    private Double cardMinCreditLimitPerOrg;

    @Column(name = "SEMICARD_USEDCREDITLIMIT")
    private Double semiCardUsedCreditLimit;

    @Column(name = "SEMICARD_LATEST6MONTHUSEDAVGAMOUNT")
    private Double semiCardLatest6MonthUsedAvgAmount;

    @Column(name = "RH_1")
    private Integer rh_1 = 0;

    @Column(name = "RH_2")
    private Integer rh_2 = 0;

    @Column(name = "RH_3")
    private Integer rh_3 = 0;

    @Column(name = "RH_4")
    private Integer rh_4 = 0;

    @Column(name = "RH_5")
    private Integer RH_5 = 0;

    @Column(name = "RH_6")
    private Integer RH_6 = 0;

    @Column(name = "RH_7")
    private Integer RH_7 = 0;

    @Column(name = "RH_8")
    private Integer RH_8 = 0;

    @Column(name = "RH_9")
    private Integer RH_9 = 0;

    @Column(name = "RH_10")
    private Integer RH_10 = 0;

    @Column(name = "RH_11")
    private Integer RH_11 = 0;


    @Column(name = "RH_12")
    private Integer RH_12 = 0;


    @Column(name = "RH_13")
    private Integer RH_13 = 0;


    @Column(name = "RH_14")
    private Integer RH_14 = 0;


    @Column(name = "CARD_QUERYLATEST6MONTH")
    private String cardQueryLatest6Month;


    @Column(name = "KEYINER")
    private String keyiner;


    @Column(name = "CREATE_TIME")
    private Date createTime;


    @Column(name = "RH_CRL")
    private Integer rh_crl;


    @Column(name = "FLAG")
    private Integer flag;


    @Column(name = "RISK")
    private Integer risk;


    @Column(name = "STATUS")
    private Integer status;

    public Pboc() {
        createTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getReportCreateTime() {
        return reportCreateTime;
    }

    public void setReportCreateTime(String reportCreateTime) {
        this.reportCreateTime = reportCreateTime;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewCertNo() {
        return newCertNo;
    }

    public void setNewCertNo(String newCertNo) {
        this.newCertNo = newCertNo;
    }

    public String getNewReportCreateTime() {
        return newReportCreateTime;
    }

    public void setNewReportCreateTime(String newReportCreateTime) {
        this.newReportCreateTime = newReportCreateTime;
    }

    public String getMaritalState() {
        return maritalState;
    }

    public void setMaritalState(String maritalState) {
        this.maritalState = maritalState;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileCity() {
        return mobileCity;
    }

    public void setMobileCity(String mobileCity) {
        this.mobileCity = mobileCity;
    }

    public String getOfficeTelephoneNo() {
        return officeTelephoneNo;
    }

    public void setOfficeTelephoneNo(String officeTelephoneNo) {
        this.officeTelephoneNo = officeTelephoneNo;
    }

    public String getEduDegree() {
        return eduDegree;
    }

    public void setEduDegree(String eduDegree) {
        this.eduDegree = eduDegree;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public String getHomeTelephoneNo() {
        return homeTelephoneNo;
    }

    public void setHomeTelephoneNo(String homeTelephoneNo) {
        this.homeTelephoneNo = homeTelephoneNo;
    }

    public Integer getCardOverDueNum() {
        return cardOverDueNum;
    }

    public void setCardOverDueNum(Integer cardOverDueNum) {
        this.cardOverDueNum = cardOverDueNum;
    }

    public Double getCardMinCreditLimitPerOrg() {
        return cardMinCreditLimitPerOrg;
    }

    public void setCardMinCreditLimitPerOrg(Double cardMinCreditLimitPerOrg) {
        this.cardMinCreditLimitPerOrg = cardMinCreditLimitPerOrg;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerCertNo() {
        return partnerCertNo;
    }

    public void setPartnerCertNo(String partnerCertNo) {
        this.partnerCertNo = partnerCertNo;
    }

    public String getPartnerTelephoneNo() {
        return partnerTelephoneNo;
    }

    public void setPartnerTelephoneNo(String partnerTelephoneNo) {
        this.partnerTelephoneNo = partnerTelephoneNo;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomeResidenceType() {
        return homeResidenceType;
    }

    public void setHomeResidenceType(String homeResidenceType) {
        this.homeResidenceType = homeResidenceType;
    }

    public String getAddressGetTime() {
        return addressGetTime;
    }

    public void setAddressGetTime(String addressGetTime) {
        this.addressGetTime = addressGetTime;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmployerCity() {
        return employerCity;
    }

    public void setEmployerCity(String employerCity) {
        this.employerCity = employerCity;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryAddress() {
        return industryAddress;
    }

    public void setIndustryAddress(String industryAddress) {
        this.industryAddress = industryAddress;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getIndustryYear() {
        if (StringUtils.isEmpty(hisGetting)) return StringUtils.EMPTY;
        final Integer ht = Integer.valueOf(StringUtils.substring(hisGetting, 0, 4));
        return String.valueOf(new LocalDate(new Date()).getYear() - ht);
    }

    public void setIndustryYear(String industryYear) {
        this.industryYear = industryYear;
    }

    public String getEmployerGetTime() {
        return employerGetTime;
    }

    public void setEmployerGetTime(String employerGetTime) {
        this.employerGetTime = employerGetTime;
    }

    public String getHisGetting() {
        return hisGetting;
    }

    public void setHisGetting(String hisGetting) {
        this.hisGetting = hisGetting;
    }

    public String getyRegisterDate() {
        return yRegisterDate;
    }

    public void setyRegisterDate(String yRegisterDate) {
        this.yRegisterDate = yRegisterDate;
    }

    public String getyWorkDate() {
        return yWorkDate;
    }

    public void setyWorkDate(String yWorkDate) {
        this.yWorkDate = yWorkDate;
    }

    public Double getyOwnBasicMoney() {
        return yOwnBasicMoney;
    }

    public void setyOwnBasicMoney(Double yOwnBasicMoney) {
        this.yOwnBasicMoney = yOwnBasicMoney;
    }

    public String getyState() {
        return yState;
    }

    public void setyState(String yState) {
        this.yState = yState;
    }

    public Double getyMoney() {
        return yMoney;
    }

    public void setyMoney(Double yMoney) {
        this.yMoney = yMoney;
    }

    public Integer getyOrganName() {
        return yOrganName;
    }

    public void setyOrganName(Integer yOrganName) {
        this.yOrganName = yOrganName;
    }

    public String getPauseReason() {
        return pauseReason;
    }

    public void setPauseReason(String pauseReason) {
        this.pauseReason = pauseReason;
    }

    public String getyGetTime() {
        return yGetTime;
    }

    public void setyGetTime(String yGetTime) {
        this.yGetTime = yGetTime;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getFirstMonth() {
        return firstMonth;
    }

    public void setFirstMonth(String firstMonth) {
        this.firstMonth = firstMonth;
    }

    public String getToMonth() {
        return toMonth;
    }

    public void setToMonth(String toMonth) {
        this.toMonth = toMonth;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public Integer getOrganName() {
        return organName;
    }

    public void setOrganName(Integer organName) {
        this.organName = organName;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getFirstLoanMonth() {
        return firstLoanMonth;
    }

    public void setFirstLoanMonth(String firstLoanMonth) {
        this.firstLoanMonth = firstLoanMonth;
    }

    public Integer getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(Integer loanCount) {
        this.loanCount = loanCount;
    }

    public Double getLoanHighestOverdueAmountPerMon() {
        return loanHighestOverdueAmountPerMon;
    }

    public void setLoanHighestOverdueAmountPerMon(Double loanHighestOverdueAmountPerMon) {
        this.loanHighestOverdueAmountPerMon = loanHighestOverdueAmountPerMon;
    }

    public Integer getLoanMaxDuration() {
        return loanMaxDuration;
    }

    public void setLoanMaxDuration(Integer loanMaxDuration) {
        this.loanMaxDuration = loanMaxDuration;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    public Integer getCardHighestOverdueAmountPerMon() {
        return cardHighestOverdueAmountPerMon;
    }

    public void setCardHighestOverdueAmountPerMon(Integer cardHighestOverdueAmountPerMon) {
        this.cardHighestOverdueAmountPerMon = cardHighestOverdueAmountPerMon;
    }

    public Integer getCardMaxDuration() {
        return cardMaxDuration;
    }

    public void setCardMaxDuration(Integer cardMaxDuration) {
        this.cardMaxDuration = cardMaxDuration;
    }

    public Integer getCardOverDuePerYear() {
        return cardOverDuePerYear;
    }

    public void setCardOverDuePerYear(Integer cardOverDuePerYear) {
        this.cardOverDuePerYear = cardOverDuePerYear;
    }

    public Integer getSemiCardCount() {
        return semiCardCount;
    }

    public void setSemiCardCount(Integer semiCardCount) {
        this.semiCardCount = semiCardCount;
    }

    public Double getSemiCardHighestOverdueAmountPerMon() {
        return semiCardHighestOverdueAmountPerMon;
    }

    public void setSemiCardHighestOverdueAmountPerMon(Double semiCardHighestOverdueAmountPerMon) {
        this.semiCardHighestOverdueAmountPerMon = semiCardHighestOverdueAmountPerMon;
    }

    public Double getSemiCardMaxDuration() {
        return semiCardMaxDuration;
    }

    public void setSemiCardMaxDuration(Double semiCardMaxDuration) {
        this.semiCardMaxDuration = semiCardMaxDuration;
    }

    public Double getLoanAccountCount() {
        return loanAccountCount;
    }

    public void setLoanAccountCount(Double loanAccountCount) {
        this.loanAccountCount = loanAccountCount;
    }

    public Double getLoanCreditLimit() {
        return loanCreditLimit;
    }

    public void setLoanCreditLimit(Double loanCreditLimit) {
        this.loanCreditLimit = loanCreditLimit;
    }

    public Double getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(Double loanBalance) {
        this.loanBalance = loanBalance;
    }

    public Double getLoanLatest6MonthUsedAvgAmount() {
        return loanLatest6MonthUsedAvgAmount;
    }

    public void setLoanLatest6MonthUsedAvgAmount(Double loanLatest6MonthUsedAvgAmount) {
        this.loanLatest6MonthUsedAvgAmount = loanLatest6MonthUsedAvgAmount;
    }

    public Integer getCardOrg() {
        return cardOrg;
    }

    public void setCardOrg(Integer cardOrg) {
        this.cardOrg = cardOrg;
    }

    public Integer getCardAccountCount() {
        return cardAccountCount;
    }

    public void setCardAccountCount(Integer cardAccountCount) {
        this.cardAccountCount = cardAccountCount;
    }

    public Integer getCardCreditLimit() {
        return cardCreditLimit;
    }

    public void setCardCreditLimit(Integer cardCreditLimit) {
        this.cardCreditLimit = cardCreditLimit;
    }

    public Double getCardAvgCreditLimit() {
        if (cardOrg == null || cardOrg < 0) return null;
        return cardCreditLimit / (double) cardOrg;
    }

    public void setCardAvgCreditLimit(Double cardAvgCreditLimit) {
        this.cardAvgCreditLimit = cardAvgCreditLimit;
    }

    public Double getCardMaxCreditLimitPerOrg() {
        return cardMaxCreditLimitPerOrg;
    }

    public void setCardMaxCreditLimitPerOrg(Double cardMaxCreditLimitPerOrg) {
        this.cardMaxCreditLimitPerOrg = cardMaxCreditLimitPerOrg;
    }

    public Double getCardUsedCreditLimit() {
        return cardUsedCreditLimit;
    }

    public void setCardUsedCreditLimit(Double cardUsedCreditLimit) {
        this.cardUsedCreditLimit = cardUsedCreditLimit;
    }

    public Double getCardLatest6MonthUsedAvgAmount() {
        return cardLatest6MonthUsedAvgAmount;
    }

    public void setCardLatest6MonthUsedAvgAmount(Double cardLatest6MonthUsedAvgAmount) {
        this.cardLatest6MonthUsedAvgAmount = cardLatest6MonthUsedAvgAmount;
    }

    public Double getSemiCardOrg() {
        return semiCardOrg;
    }

    public void setSemiCardOrg(Double semiCardOrg) {
        this.semiCardOrg = semiCardOrg;
    }

    public Double getSemiCardAccountCount() {
        return semiCardAccountCount;
    }

    public void setSemiCardAccountCount(Double semiCardAccountCount) {
        this.semiCardAccountCount = semiCardAccountCount;
    }

    public Double getSemicardCreditLimit() {
        return semicardCreditLimit;
    }

    public void setSemicardCreditLimit(Double semicardCreditLimit) {
        this.semicardCreditLimit = semicardCreditLimit;
    }

    public Double getSemiCardAvgCreditlimit() {
        if (semiCardOrg == null || semiCardOrg < 0) return null;
        return semicardCreditLimit / (double) semiCardOrg;
    }

    public void setSemiCardAvgCreditlimit(Double semiCardAvgCreditlimit) {
        this.semiCardAvgCreditlimit = semiCardAvgCreditlimit;
    }

    public Double getSemiCardMaxCreditLimitPerOrg() {
        return semiCardMaxCreditLimitPerOrg;
    }

    public void setSemiCardMaxCreditLimitPerOrg(Double semiCardMaxCreditLimitPerOrg) {
        this.semiCardMaxCreditLimitPerOrg = semiCardMaxCreditLimitPerOrg;
    }

    public Double getSemicardMinCreditLimitPerOrg() {
        return semicardMinCreditLimitPerOrg;
    }

    public void setSemicardMinCreditLimitPerOrg(Double semicardMinCreditLimitPerOrg) {
        this.semicardMinCreditLimitPerOrg = semicardMinCreditLimitPerOrg;
    }

    public Double getSemiCardUsedCreditLimit() {
        return semiCardUsedCreditLimit;
    }

    public void setSemiCardUsedCreditLimit(Double semiCardUsedCreditLimit) {
        this.semiCardUsedCreditLimit = semiCardUsedCreditLimit;
    }

    public Double getSemiCardLatest6MonthUsedAvgAmount() {
        return semiCardLatest6MonthUsedAvgAmount;
    }

    public void setSemiCardLatest6MonthUsedAvgAmount(Double semiCardLatest6MonthUsedAvgAmount) {
        this.semiCardLatest6MonthUsedAvgAmount = semiCardLatest6MonthUsedAvgAmount;
    }

    public Integer getRh_1() {
        return rh_1;
    }

    public void setRh_1(Integer rh_1) {
        this.rh_1 = rh_1;
    }

    public Integer getRh_2() {
        return rh_2;
    }

    public void setRh_2(Integer rh_2) {
        this.rh_2 = rh_2;
    }

    public Integer getRh_3() {
        return rh_3;
    }

    public void setRh_3(Integer rh_3) {
        this.rh_3 = rh_3;
    }

    public Integer getRh_4() {
        return rh_4;
    }

    public void setRh_4(Integer rh_4) {
        this.rh_4 = rh_4;
    }

    public Integer getRH_5() {
        return RH_5;
    }

    public void setRH_5(Integer RH_5) {
        this.RH_5 = RH_5;
    }

    public Integer getRH_6() {
        return RH_6;
    }

    public void setRH_6(Integer RH_6) {
        this.RH_6 = RH_6;
    }

    public Integer getRH_7() {
        return RH_7;
    }

    public void setRH_7(Integer RH_7) {
        this.RH_7 = RH_7;
    }

    public Integer getRH_8() {
        return RH_8;
    }

    public void setRH_8(Integer RH_8) {
        this.RH_8 = RH_8;
    }

    public Integer getRH_9() {
        return RH_9;
    }

    public void setRH_9(Integer RH_9) {
        this.RH_9 = RH_9;
    }

    public Integer getRH_10() {
        return RH_10;
    }

    public void setRH_10(Integer RH_10) {
        this.RH_10 = RH_10;
    }

    public Integer getRH_11() {
        return RH_11;
    }

    public void setRH_11(Integer RH_11) {
        this.RH_11 = RH_11;
    }

    public Integer getRH_12() {
        return RH_12;
    }

    public void setRH_12(Integer RH_12) {
        this.RH_12 = RH_12;
    }

    public Integer getRH_13() {
        return RH_13;
    }

    public void setRH_13(Integer RH_13) {
        this.RH_13 = RH_13;
    }

    public Integer getRH_14() {
        return RH_14;
    }

    public void setRH_14(Integer RH_14) {
        this.RH_14 = RH_14;
    }

    public String getCardQueryLatest6Month() {
        return cardQueryLatest6Month;
    }

    public void setCardQueryLatest6Month(String cardQueryLatest6Month) {
        this.cardQueryLatest6Month = cardQueryLatest6Month;
    }

    public String getKeyiner() {
        return keyiner;
    }

    public void setKeyiner(String keyiner) {
        this.keyiner = keyiner;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRh_crl() {
        return rh_crl;
    }

    public void setRh_crl(Integer rh_crl) {
        this.rh_crl = rh_crl;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getRisk() {
        return risk;
    }

    public void setRisk(Integer risk) {
        this.risk = risk;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
