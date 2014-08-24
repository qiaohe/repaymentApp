package com.huayuan.domain.loanapplication;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity
@Table(name = "STAFF")
public class Staff implements Serializable {
    private static final long serialVersionUID = -8453855512273993675L;

    @Id
    @Column(name = "STAFF_ID")
    private String staffId;


    @Column(name = "ROLE")
    private Integer role;


    @Column(name = "ENROLL_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollTime;


    @Column(name = "QUIT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date quitTime;


    @Column(name = "STATUS")
    private String status;


    @Column(name = "CREATOR")
    private String creator;


    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    public Staff() {
        createTime = new Date();
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }


    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }


    public Date getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Date enrollTime) {
        this.enrollTime = enrollTime;
    }


    public Date getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Date quitTime) {
        this.quitTime = quitTime;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
