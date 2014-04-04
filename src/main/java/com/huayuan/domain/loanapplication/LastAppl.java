package com.huayuan.domain.loanapplication;

import com.huayuan.domain.member.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "LAST_APPL")
public class LastAppl implements Serializable {
    private static final long serialVersionUID = -3751309910672351082L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Id
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LAST_APPL_NO")
    private Appl lastApplNo;

    @Basic
    @Column(name = "LAST_SCORE")
    private Integer lastScore;

    @Basic
    @Column(name = "LAST_RATING")
    private String lastRating;

    @Basic
    @Column(name = "LAST_DECISION")
    private Byte lastDecision;

    @Basic
    @Column(name = "LAST_REASON_1")
    private String lastReason1;

    @Basic
    @Column(name = "LAST_REASON_2")
    private String lastReason2;

    @Basic
    @Column(name = "LAST_REASON_3")
    private String lastReason3;

    @Basic
    @Column(name = "LAST_PBOC_BACK_TIME")
    @Temporal(TemporalType.DATE)
    private Date lastPbocBackTime;

    @Basic
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.DATE)
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Appl getLastApplNo() {
        return lastApplNo;
    }

    public void setLastApplNo(Appl lastApplNo) {
        this.lastApplNo = lastApplNo;
    }

    public void setLastPbocBackTime(Date lastPbocBackTime) {
        this.lastPbocBackTime = lastPbocBackTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLastScore() {
        return lastScore;
    }

    public void setLastScore(Integer lastScore) {
        this.lastScore = lastScore;
    }


    public String getLastRating() {
        return lastRating;
    }

    public void setLastRating(String lastRating) {
        this.lastRating = lastRating;
    }


    public Byte getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(Byte lastDecision) {
        this.lastDecision = lastDecision;
    }


    public String getLastReason1() {
        return lastReason1;
    }

    public void setLastReason1(String lastReason1) {
        this.lastReason1 = lastReason1;
    }


    public String getLastReason2() {
        return lastReason2;
    }

    public void setLastReason2(String lastReason2) {
        this.lastReason2 = lastReason2;
    }


    public String getLastReason3() {
        return lastReason3;
    }

    public void setLastReason3(String lastReason3) {
        this.lastReason3 = lastReason3;
    }

    public Date getLastPbocBackTime() {
        return lastPbocBackTime;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
