package com.huayuan.domain.credit;

import com.huayuan.domain.loanapplication.Application;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Johnson on 5/1/14.
 */
@Entity
@Table(name = "TV_EXECUTION")
public class TvExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPL_NO")
    private Application application;
    @Column(name = "QUESTION")
    private String question;
    @Column(name = "ANSWER1")
    private String answer1;
    @Column(name = "ANSWER2")
    private String answer2;
    @Column(name = "STATUS")
    private int status;
    @Column(name = "CREATETIME")
    private Date createTime = new Date();
    @Column(name = "REPLYDATE")
    private Date replyDate;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public boolean ignoreReplyIfNeeded() {
        return StringUtils.isNotEmpty(getAnswer1()) || StringUtils.isNotEmpty(getAnswer2());
    }
}
