package com.huayuan.domain.accounting;

import com.huayuan.domain.member.Member;

import javax.persistence.*;

/**
 * Created by dell on 14-4-8.
 */
@Entity
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @Column(name = "CRL")
    private Integer crl;
    @Column(name = "CRL_AVL")
    private Integer crlAvl;
    @Column(name = "CRL_USED")
    private Integer crlUsed;
    @Column(name = "DEBIT_AMT")
    private Double debit_amt;
    public Account() {
        debit_amt = 0d;
        crl =0;
        crlUsed = 0;
        crlAvl = 0;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getCrl() {
        return crl;
    }

    public void setCrl(Integer crl) {
        this.crl = crl;
    }

    public Integer getCrlAvl() {
        return crlAvl;
    }

    public void setCrlAvl(Integer crl_Avl) {
        this.crlAvl = crl_Avl;
    }

    public Integer getCrlUsed() {
        return crlUsed;
    }

    public void setCrlUsed(Integer crl_Used) {
        this.crlUsed = crl_Used;
    }

    public Double getDebit_amt() {
        return debit_amt;
    }

    public void setDebit_amt(Double debit_amt) {
        this.debit_amt = debit_amt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
