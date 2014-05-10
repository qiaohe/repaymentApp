package com.huayuan.web.dto;

import com.huayuan.domain.loanapplication.TelephoneTV;

/**
 * Created by dell on 14-5-10.
 */
public class TelephoneTvDto {
    private String appNo;
    private Integer type;
    private Integer telephoneType;
    private String telephoneNo;
    private String question;
    private Integer decision;

    public TelephoneTvDto() {

    }

    public TelephoneTvDto(String appNo, Integer type, Integer telephoneType, String telephoneNo, String question, Integer decision) {
        this.appNo = appNo;
        this.type = type;
        this.telephoneType = telephoneType;
        this.telephoneNo = telephoneNo;
        this.question = question;
        this.decision = decision;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTelephoneType() {
        return telephoneType;
    }

    public void setTelephoneType(Integer telephoneType) {
        this.telephoneType = telephoneType;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public TelephoneTV createEntity() {
        TelephoneTV telephoneTV = new TelephoneTV();
        telephoneTV.setQuestion(question);
        telephoneTV.setDecision(decision);
        telephoneTV.setTelephoneType(telephoneType);
        telephoneTV.setTelephoneNo(telephoneNo);
        telephoneTV.setType(type);
        return telephoneTV;
    }
}
