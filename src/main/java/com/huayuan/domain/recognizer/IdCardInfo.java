package com.huayuan.domain.recognizer;

import com.huayuan.domain.IdCard;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by dell on 14-3-21.
 */
public final class IdCardInfo {
    private String name;
    private String nationality;
    private String address;
    private String idCardNumber;
    private Enum sex;
    public String birthday;
    public String issueAuthority;
    public String validPeriod;

    public IdCardInfo() {

    }

    public IdCardInfo(String name, String nationality, String address, String idCardNumber,String birthday) {
        this();
        this.name = name;
        this.nationality = nationality;
        this.address = address;
        this.idCardNumber = idCardNumber;
        this.birthday = birthday;
    }

    public IdCardInfo(String issueAuthority,String validPeriod){
        this.issueAuthority = issueAuthority;
        this.validPeriod = validPeriod;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIssueAuthority() {
        return issueAuthority;
    }

    public void setIssueAuthority(String issueAuthority) {
        this.issueAuthority = issueAuthority;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public Enum getSex() {
        return sex;
    }

    public void setSex(Enum sex) {
        this.sex = sex;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Date getValidateFrom() {
        return new Date();
    }

    public  Date getValidateThru() {
        return new Date();
    }

    public IdCard getIdCard() {
        IdCard result = new IdCard();
        result.setNationality(nationality);
        result.setAddress(address);
        result.setIdNo(idCardNumber);
        result.setName(name);
        result.setSex(sex);
        result.setIssuer(issueAuthority);
        try {
            if(!StringUtils.isEmpty(validPeriod)){
                String validFrom = validPeriod.substring(validPeriod.indexOf("-")+1,validPeriod.length());
                String validThru = validPeriod.substring(0,validPeriod.indexOf("-"));
                result.setValidFrom(DateUtils.parseDate(validFrom, "yyyy.MM.dd"));
                result.setValidThru(DateUtils.parseDate(validThru, "yyyy.MM.dd"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
