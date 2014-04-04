package com.huayuan.domain.loanapplication;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "A_SCORE")
public class AScore implements Serializable {
    private static final long serialVersionUID = 7012088286194895365L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPL_NO")
    private Appl appl;

    @Basic
    @Column(name = "PBOC_TIME")
    private Date pbocTime;

    @Basic
    @Column(name = "PBOC_BACK_TIME")
    private Date pbocBackTime;

    @Basic
    @Column(name = "SCORE")
    private Integer score;

    @Basic
    @Column(name = "RATING")
    private String rating;

    @Basic
    @Column(name = "RISK_REMIND")
    private String riskRemind;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appl getAppl() {
        return appl;
    }

    public void setAppl(Appl appl) {
        this.appl = appl;
    }

    public Date getPbocTime() {
        return pbocTime;
    }

    public void setPbocTime(Date pbocTime) {
        this.pbocTime = pbocTime;
    }


    public Date getPbocBackTime() {
        return pbocBackTime;
    }

    public void setPbocBackTime(Date pbocBackTime) {
        this.pbocBackTime = pbocBackTime;
    }


    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    public String getRiskRemind() {
        return riskRemind;
    }

    public void setRiskRemind(String riskRemind) {
        this.riskRemind = riskRemind;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
