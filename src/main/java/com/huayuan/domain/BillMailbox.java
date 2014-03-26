package com.huayuan.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Johnson on 3/19/14.
 */
@Entity
@Table(name = "BILL_MAILBOX", schema = "dbo", catalog = "MEMBER")
public class BillMailbox implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Basic
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "PASSWORD")
    private String password;

    @Basic
    @Column(name = "APPL_NO")
    private String applNo;

    @Basic
    @Column(name = "STATUS")
    private Enum status;

    @Basic
    @Column(name = "CREATE_TIME")
    private Date createTime;

    public BillMailbox() {
    }

    public BillMailbox(Member member, String email, String password) {
        this.member = member;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
