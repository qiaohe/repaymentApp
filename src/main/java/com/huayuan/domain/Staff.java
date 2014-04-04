package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 14-4-3.
 */
@Entity

@Table(name = "STAFF", schema = "dbo", catalog = "REPAYMENTDB")
public class Staff {

    @Id
    @Column(name = "STAFF_ID")
    private String staffId;

    @Basic
    @Column(name = "ROLE")
    private Byte role;

    @Basic
    @Column(name = "ENROLL_TIME")
    private Timestamp enrollTime;

    @Basic
    @Column(name = "QUIT_TIME")
    private Timestamp quitTime;

    @Basic
    @Column(name = "STATUS")
    private String status;

    @Basic
    @Column(name = "CREATOR")
    private String creator;

    @Basic
    @Column(name = "CREATE_TIME")
    private Timestamp createTime;


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }


    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }


    public Timestamp getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Timestamp enrollTime) {
        this.enrollTime = enrollTime;
    }


    public Timestamp getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Timestamp quitTime) {
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


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

}
