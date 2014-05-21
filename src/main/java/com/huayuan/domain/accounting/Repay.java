package com.huayuan.domain.accounting;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Johnson on 4/23/14.
 */
@Entity
@Table(name = "REPAY")
public class Repay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "REPAY_SN")
    private String repayNo;
    private Long memberId;
    @Column(name = "REPAY_DATE")
    private Date repayDate;
    @Column(name = "AMT")
    private Double amt;
    @Column(name = "SOURCE")
    private Integer source;
    @Column(name = "REF_NO")
    private String refNo;
    @Column(name = "IS_SUCCESS")
    private int isSuccess;

    public String getRepayNo() {
        return repayNo;
    }

    public void setRepayNo(String repayNo) {
        this.repayNo = repayNo;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public int isSuccess() {
        return isSuccess;
    }

    public void setSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
