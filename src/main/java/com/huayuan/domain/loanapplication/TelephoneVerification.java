package com.huayuan.domain.loanapplication;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "APPL_TV")
public class TelephoneVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPL_NO")
    @JsonIgnore
    private Application application;


    @Column(name = "TYPE")
    private Integer type;


    @Column(name = "TV_QUES_1")
    private String tvQues1;


    @Column(name = "TV_MEM_ANS_1")
    private String tvMemAns1;


    @Column(name = "TV_QUES_2")
    private String tvQues2;


    @Column(name = "TV_MEM_ANS_2")
    private String tvMemAns2;


    @Column(name = "DECISION")
    private String decision;


    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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


    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
