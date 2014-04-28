package com.huayuan.domain.member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity

@Table(name = "PRECREDIT")
public class PreCredit implements Serializable {

    private static final long serialVersionUID = 7095907989201261838L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ID")
    private IdCard idCard;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CC_ID")
    private CreditCard creditCard;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "BILL_ID")
    private CreditCardBill creditCardBill;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    @Column(name = "CRL")
    private Integer crl;

    @Column(name = "CREATE_TIME")
    private Date createTime = new Date();

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

    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public CreditCardBill getCreditCardBill() {
        return creditCardBill;
    }

    public void setCreditCardBill(CreditCardBill creditCardBill) {
        this.creditCardBill = creditCardBill;
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
