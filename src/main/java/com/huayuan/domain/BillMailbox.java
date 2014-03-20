package com.huayuan.domain;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
@Table(name = "BILL_MAILBOX", schema = "dbo", catalog = "MEMBER")
public class BillMailbox {
    private Long id;
    private Integer memberId;
    private String email;
    private String password;
    private String applNo;
    private Byte status;
    private Timestamp createTime;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MEMBER_ID")
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "APPL_NO")
    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }

    @Basic
    @Column(name = "STATUS")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "CREATE_TIME")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberId, email);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BillMailbox) {
            final BillMailbox other = (BillMailbox) obj;
            return Objects.equal(memberId, other.memberId) && Objects.equal(email, other.email);
        } else {
            return false;
        }
    }
}
