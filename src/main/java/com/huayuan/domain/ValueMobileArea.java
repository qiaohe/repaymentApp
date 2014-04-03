package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "VALUE_MOBILE_AREA", schema = "dbo", catalog = "REPAYMENTDB")
public class ValueMobileArea {

    @Id
    @Column(name = "ID")
    private int id;

    @Basic
    @Column(name = "N1_7")
    private Long n17;

    @Basic
    @Column(name = "PROVINCE")
    private String province;

    @Basic
    @Column(name = "CITY")
    private String city;

    @Basic
    @Column(name = "TYPE")
    private String type;

    @Basic
    @Column(name = "AREA_CODE")
    private String areaCode;

    @Basic
    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Basic
    @Column(name = "UPDATOR")
    private String updator;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Long getN17() {
        return n17;
    }

    public void setN17(Long n17) {
        this.n17 = n17;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
