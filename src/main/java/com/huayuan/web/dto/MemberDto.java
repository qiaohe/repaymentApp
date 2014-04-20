package com.huayuan.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by dell on 14-4-5.
 */
public class MemberDto implements Serializable {
    private static final long serialVersionUID = 6133417800869029252L;
    private Long memberId;
    private String creditCarNo;
    private int education;
    private String email;
    private int industry;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String billEmail;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String billPassword;

    public MemberDto() {
    }

    @JsonIgnore
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setCreditCarNo(String creditCarNo) {
        this.creditCarNo = creditCarNo;
    }

    public MemberDto(int education, String email, int industry, String creditCarNo) {
        this();
        this.education = education;
        this.email = email;
        this.industry = industry;
        this.creditCarNo = creditCarNo;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public String getCreditCarNo() {
        return creditCarNo;
    }

    public String getBillEmail() {
        return billEmail;
    }

    public void setBillEmail(String billEmail) {
        this.billEmail = billEmail;
    }

    public String getBillPassword() {
        return billPassword;
    }

    public void setBillPassword(String billPassword) {
        this.billPassword = billPassword;
    }

    public boolean crawlBillIfNeeded() {
       return StringUtils.isNotEmpty(billEmail) && StringUtils.isNotEmpty(billPassword);
    }
}
