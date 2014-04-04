package com.huayuan.domain;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "APPL", schema = "dbo", catalog = "REPAYMENTDB")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Basic
    @Column(name = "APPL_NO")
    private String applicationNo;

    @Basic
    @Column(name = "MEMBER_ID")
    private Integer memberId;

    @Basic
    @Column(name = "ISNEW")
    private boolean isNew;

    @Basic
    @Column(name = "ID_ID")
    private Long idId;

    @Basic
    @Column(name = "CC_ID")
    private Long ccId;

    @Basic
    @Column(name = "BILL_ID")
    private Long billId;

    @Basic
    @Column(name = "AMT")
    private Double amt;

    @Basic
    @Column(name = "TERM")
    private int term;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "REPAY_TYPE")
    private RepaymentModeEnum repayType;

    @Basic
    @Column(name = "TITLE")
    private String title;

    @Basic
    @Column(name = "PRE_SCORE")
    private String preScore;

    @Basic
    @Column(name = "STATUS")
    private Byte status;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME")
    private Date createTime;
}
