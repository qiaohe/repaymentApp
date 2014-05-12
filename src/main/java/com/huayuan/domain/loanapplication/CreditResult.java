package com.huayuan.domain.loanapplication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huayuan.domain.member.Member;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "LAST_CREDIT")
public class CreditResult implements Serializable {
    private static final String[] DECLINED_REASON_CODES = new String[]{"D100", "D102", "D108"};
    private static final long serialVersionUID = -3751309910672351082L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    private Member member;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LAST_APPL_NO")
    @JsonIgnore
    private Application lastApplicationNo;


    @Column(name = "LAST_SCORE")
    private Integer lastScore;


    @Column(name = "LAST_RATING")
    private String lastRating;


    @Column(name = "LAST_DECISION")
    private String lastDecision;


    @Column(name = "LAST_REASON_1")
    private String lastReason1;


    @Column(name = "LAST_REASON_2")
    private String lastReason2;


    @Column(name = "LAST_REASON_3")
    private String lastReason3;


    @Column(name = "LAST_PBOC_BACK_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPbocBackTime;


    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public CreditResult() {
        createTime = new Date();
    }

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

    public Application getLastApplicationNo() {
        return lastApplicationNo;
    }

    public void setLastApplicationNo(Application lastApplicationNo) {
        this.lastApplicationNo = lastApplicationNo;
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


    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
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

    public boolean isDeclined() {
        return StringUtils.endsWithAny(lastReason1, DECLINED_REASON_CODES)
                || StringUtils.endsWithAny(lastReason2, DECLINED_REASON_CODES)
                || StringUtils.endsWithAny(lastReason3, DECLINED_REASON_CODES);
    }
}
