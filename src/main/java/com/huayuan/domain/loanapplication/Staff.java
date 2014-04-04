package com.huayuan.domain.loanapplication;

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

    @Basic
    @Column(name = "ROLE")
    private int role;

    @Basic
    @Column(name = "ENROLL_TIME")
    @Temporal(TemporalType.DATE)
    private Date enrollTime;

    @Basic
    @Column(name = "QUIT_TIME")
    @Temporal(TemporalType.DATE)
    private Date quitTime;

    @Basic
    @Column(name = "STATUS")
    private String status;

    @Basic
    @Column(name = "CREATOR")
    private String creator;

    @Basic
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.DATE)
    private Date createTime;


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
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

}
