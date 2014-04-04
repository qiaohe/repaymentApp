package com.huayuan.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "VALUE_ID_AREA", schema = "dbo", catalog = "REPAYMENTDB")
public class ValueIdArea {

    @Id
    @Column(name = "ID")
    private short id;

    @Basic
    @Column(name = "N1_2")
    private String n12;

    @Basic
    @Column(name = "N3_4")
    private String n34;

    @Basic
    @Column(name = "N5_6")
    private String n56;

    @Basic
    @Column(name = "AREA")
    private String area;

    @Basic
    @Column(name = "PROVINCE")

    private String province;

    @Basic
    @Column(name = "CITY")
    private String city;

    @Basic
    @Column(name = "XIAN")
    private String xian;

    @Basic
    @Column(name = "CREATOR")
    private String creator;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getN12() {
        return n12;
    }

    public void setN12(String n12) {
        this.n12 = n12;
    }


    public String getN34() {
        return n34;
    }

    public void setN34(String n34) {
        this.n34 = n34;
    }


    public String getN56() {
        return n56;
    }

    public void setN56(String n56) {
        this.n56 = n56;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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


    public String getXian() {
        return xian;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
