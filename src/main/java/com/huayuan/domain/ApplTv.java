package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "APPL_TV", schema = "dbo", catalog = "REPAYMENTDB")
public class ApplTv {

    @Id
    @Column(name = "APPL_NO")
    private String applNo;
    @Basic
    @Column(name = "TYPE")
    private Byte type;

    @Basic
    @Column(name = "TV_QUES_1")
    private String tvQues1;

    @Basic
    @Column(name = "TV_MEM_ANS_1")
    private String tvMemAns1;

    @Basic
    @Column(name = "TV_QUES_2")
    private String tvQues2;

    @Basic
    @Column(name = "TV_MEM_ANS_2")
    private String tvMemAns2;

    @Basic
    @Column(name = "DECISION")
    private Byte decision;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;


    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }


    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }


    public String getTvQues1() {
        return tvQues1;
    }

    public void setTvQues1(String tvQues1) {
        this.tvQues1 = tvQues1;
    }


    public String getTvMemAns1() {
        return tvMemAns1;
    }

    public void setTvMemAns1(String tvMemAns1) {
        this.tvMemAns1 = tvMemAns1;
    }


    public String getTvQues2() {
        return tvQues2;
    }

    public void setTvQues2(String tvQues2) {
        this.tvQues2 = tvQues2;
    }


    public String getTvMemAns2() {
        return tvMemAns2;
    }

    public void setTvMemAns2(String tvMemAns2) {
        this.tvMemAns2 = tvMemAns2;
    }


    public Byte getDecision() {
        return decision;
    }

    public void setDecision(Byte decision) {
        this.decision = decision;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
