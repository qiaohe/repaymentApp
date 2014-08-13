package com.huayuan.web.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Johnson on 5/18/14.
 */
public class MemberLoanSummaryDto implements Serializable {
    private static final long serialVersionUID = -1684394100433560026L;
    private BigInteger id;
    private Integer sex;
    private String mobile;
    private String email;
    private Integer education;
    private Integer industry;
    private String wcName;
    private String idNo;
    private String name;
    private String mobileCity;
    private Integer countOfLoan;
    private String blockCode;

    public MemberLoanSummaryDto(BigInteger id, Integer sex, String mobile, String email, Integer education, Integer industry,
                                String wcName, String idNo, String name, String mobileCity, Integer countOfLoan,String blockCode) {
        this.id = id;
        this.sex = sex;
        this.mobile = mobile;
        this.email = email;
        this.education = education;
        this.industry = industry;
        this.wcName = wcName;
        this.idNo = idNo;
        this.name = name;
        this.mobileCity = mobileCity;
        this.countOfLoan = countOfLoan == null? 0:countOfLoan;
        this.blockCode = blockCode;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getWcName() {
        return wcName;
    }

    public void setWcName(String wcName) {
        this.wcName = wcName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileCity() {
        return mobileCity;
    }

    public void setMobileCity(String mobileCity) {
        this.mobileCity = mobileCity;
    }

    public Integer getCountOfLoan() {
        return countOfLoan;
    }

    public void setCountOfLoan(Integer countOfLoan) {
        this.countOfLoan = countOfLoan;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }
}
