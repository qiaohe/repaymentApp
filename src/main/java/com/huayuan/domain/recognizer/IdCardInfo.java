package com.huayuan.domain.recognizer;

import com.huayuan.domain.IdCard;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by dell on 14-3-21.
 */
public final class IdCardInfo {
    private static final String VALID_DATE_DELIMITER = "-";
    private static final String VALID_DATE_PATTERN = "yyyy.MM.dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(VALID_DATE_PATTERN);


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

    public IdCardInfo(String name, String nationality, String address, String idCardNumber, String birthday) {
        this();
        this.name = name;
        this.nationality = nationality;
        this.address = address;
        this.idCardNumber = idCardNumber;
        this.birthday = birthday;
    }

    public IdCardInfo(String issueAuthority, String validPeriod) {
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

    public Date getValidateThru() {
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
        if (!StringUtils.isEmpty(validPeriod)) {
            String[] vs = StringUtils.split(validPeriod, VALID_DATE_DELIMITER);
            result.setValidFrom(DATE_FORMATTER.parseDateTime(vs[0]).toDate());
            result.setValidThru(DATE_FORMATTER.parseDateTime(vs[1]).toDate());
        }
        return result;
    }
}
