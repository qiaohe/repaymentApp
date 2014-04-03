package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity

@Table(name = "PRECREDIT", schema = "dbo", catalog = "REPAYMENTDB")
public class PreCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "ID_ID")
    private Integer idId;

    @Basic
    @Column(name = "CC_ID")
    private Integer ccId;

    @Basic
    @Column(name = "BILL_ID")
    private Integer billId;

    @Basic
    @Column(name = "BMB_ID")
    private Integer bmbId;

    @Basic
    @Column(name = "CRL")
    private Integer crl;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


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


    public Integer getIdId() {
        return idId;
    }

    public void setIdId(Integer idId) {
        this.idId = idId;
    }


    public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }


    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }


    public Integer getBmbId() {
        return bmbId;
    }

    public void setBmbId(Integer bmbId) {
        this.bmbId = bmbId;
    }


    public Integer getCrl() {
        return crl;
    }

    public void setCrl(Integer crl) {
        this.crl = crl;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
