package com.huayuan.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @Column(name = "ID_NO")
    private String idNo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SEX")
    private SexEnum sex;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ISSUER")
    private String issuer;

    @Column(name = "VALID_FROM")
    private Date validFrom;

    @Column(name = "VALID_THRU")
    private Date validThru;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "NAME_ENG")
    private String nameEng;

    @Column(name = "IMAGE_FRONT")
    private String imageFront;

    @Column(name = "IMAGE_BACK")
    private String imageBack;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    private Member member;

    public IdCard() {
        this.createTime = new Date();
    }

    public IdCard(String issueAuthority, Date validFrom, Date validThru, String imageBack) {
        this();
        this.issuer = issueAuthority;
        this.validFrom = validFrom;
        this.validThru = validThru;
        this.imageBack = imageBack;
    }

    public IdCard(String cardNo, String name, SexEnum sexEnum, Date birthday, String folk, String address, String imageFront) {
        this();
        this.idNo = cardNo;
        this.name = name;
        this.sex = sexEnum;
        this.birthday = birthday;
        this.nationality = folk;
        this.address = address;
        this.imageFront = imageFront;
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
