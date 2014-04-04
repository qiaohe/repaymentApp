package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "LAST_APPL", schema = "dbo", catalog = "REPAYMENTDB")
public class LastAppl {

    @Id
    @Column(name = "ID")
    private Long id;

    @Basic
    @Column(name = "MEMBER_ID")
    private String memberId;

    @Basic
    @Column(name = "LAST_APPL_NO")
    private Integer lastApplNo;

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
    private Timestamp lastPbocBackTime;

    @Basic
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }


    public Integer getLastApplNo() {
        return lastApplNo;
    }

    public void setLastApplNo(Integer lastApplNo) {
        this.lastApplNo = lastApplNo;
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


    public Timestamp getLastPbocBackTime() {
        return lastPbocBackTime;
    }

    public void setLastPbocBackTime(Timestamp lastPbocBackTime) {
        this.lastPbocBackTime = lastPbocBackTime;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


}
