package com.huayuan.domain.loanapplication;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPL_NO")
    @JsonIgnore
    private Application application;


    @Column(name = "PBOC_TIME")
    private Date pbocTime;


    @Column(name = "PBOC_BACK_TIME")
    private Date pbocBackTime;


    @Column(name = "SCORE")
    private Double score;


    @Column(name = "RATING")
    private String rating;


    @Column(name = "RISK_REMIND")
    private String riskRemind;


    @Column(name = "CREATE_TIME")
    private Date createTime = new Date();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
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


    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
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
