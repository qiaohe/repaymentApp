package com.huayuan.domain.credit;

import java.util.Date;

/**
 * Created by dell on 14-5-9.
 */
public class ApplicationSummary {
    private String appNo;
    private String name;
    private String idCardNo;
    private Integer existingFlag;
    private String applyDate;
    private String mobileCity;
    private Integer status;
    private String createDate;
    private String creditor;

    public ApplicationSummary() {
    }

    public ApplicationSummary(String appNo, String name, String idCardNo, Integer existingFlag, String applyDate, String mobileCity, Integer status, String createDate, String creditor) {
        this.applyDate = applyDate;
        this.appNo = appNo;
        this.status = status;
        this.existingFlag = existingFlag;
        this.idCardNo = idCardNo;
        this.name = name;
        this.mobileCity = mobileCity;
        this.createDate = createDate;
        this.creditor = creditor;
    }


    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Integer getExistingFlag() {
        return existingFlag;
    }

    public void setExistingFlag(Integer existingFlag) {
        this.existingFlag = existingFlag;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getMobileCity() {
        return mobileCity;
    }

    public void setMobileCity(String mobileCity) {
        this.mobileCity = mobileCity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }
}
