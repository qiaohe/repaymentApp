package com.huayuan.domain.dictionary;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by dell on 14-4-27.
 */
public class ValueMobileArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "AREA_CODE")
    private String areaCode;
    @Column(name = "CREATE_TIME")
    private Date createTime;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "CITY")
    private String city;
    @Column(name = "N1_7")
    private String sevenPrefix;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "UPDATOR")
    private String updator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getSevenPrefix() {
        return sevenPrefix;
    }

    public void setSevenPrefix(String sevenPrefix) {
        this.sevenPrefix = sevenPrefix;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }
}
