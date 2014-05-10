package com.huayuan.domain.loanapplication;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dell on 14-5-10.
 */
@Entity
@Table(name = "TELE_TV")
public class TelephoneTV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPL_NO")
    @JsonIgnore
    private Application application;
    @Column(name = "TYPE")
    private Integer type;
    @Column(name = "TELE_TYPE")
    private Integer telephoneType;
    @Column(name = "TELE_NO")
    private String telephoneNo;
    @Column(name = "QUESTION")
    private String question;
    @Column(name = "DECISION")
    private Integer decision;
    @Column(name = "CREATE_TIME")
    private Date createTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTelephoneType() {
        return telephoneType;
    }

    public void setTelephoneType(Integer telephoneType) {
        this.telephoneType = telephoneType;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public Date getCreateTime() {
        return createTime;
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
