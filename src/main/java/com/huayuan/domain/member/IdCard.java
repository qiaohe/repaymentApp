package com.huayuan.domain.member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "ID_CARD")
public class IdCard implements Serializable {

    private static final long serialVersionUID = -5246285602045762161L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "ID_NO")
    private String idNo;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "SEX")
    private SexEnum sex;

    @Basic
    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Basic
    @Column(name = "NATIONALITY")
    private String nationality;

    @Basic
    @Column(name = "ADDRESS")
    private String address;

    @Basic
    @Column(name = "ISSUER")
    private String issuer;

    @Basic
    @Column(name = "VALID_FROM")
    private Date validFrom;

    @Basic
    @Column(name = "VALID_THRU")
    private Date validThru;

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
    @Column(name = "NAME_ENG")
    private String nameEng;

    @Basic
    @Column(name = "IMAGE_FRONT")
    private String imageFront;

    @Basic
    @Column(name = "IMAGE_BACK")
    private String imageBack;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public IdCard() {
        this.createTime = new Date();
    }

    public IdCard(String issueAuthority, Date validFrom, Date validThru) {
        this();
        this.issuer = issueAuthority;
        this.validFrom = validFrom;
        this.validThru = validThru;
    }

    public IdCard(String cardNo, String name, SexEnum sexEnum, Date birthday, String folk, String address) {
        this();
        this.idNo = cardNo;
        this.name = name;
        this.sex = sexEnum;
        this.birthday = birthday;
        this.nationality = folk;
        this.address = address;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }


    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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


    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }


    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }


    public Date getValidThru() {
        return validThru;
    }

    public void setValidThru(Date validThru) {
        this.validThru = validThru;
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


    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }


    public String getImageFront() {
        return imageFront;
    }

    public void setImageFront(String imageFront) {
        this.imageFront = imageFront;
    }


    public String getImageBack() {
        return imageBack;
    }

    public void setImageBack(String imageBack) {
        this.imageBack = imageBack;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}