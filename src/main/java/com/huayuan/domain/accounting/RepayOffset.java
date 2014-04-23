package com.huayuan.domain.accounting;

import javax.persistence.*;

/**
 * Created by Johnson on 4/23/14.
 */
@Entity
@Table(name = "REPAY_OFFFSET")
public class RepayOffset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TERM_NO")
    private Integer termNo;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REPAY_ID")
    private Repay repay;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BID")
    private Loan loan;
    @Column(name = "AMT")
    private Double amt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTermNo() {
        return termNo;
    }

    public void setTermNo(Integer termNo) {
        this.termNo = termNo;
    }

    public Repay getRepay() {
        return repay;
    }

    public void setRepay(Repay repay) {
        this.repay = repay;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }
}
