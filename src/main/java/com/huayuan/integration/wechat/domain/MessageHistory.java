package com.huayuan.integration.wechat.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 14-4-29.
 */
@Entity
@Table(name = "WECHAT_MESSAGE")
public class MessageHistory implements Serializable {
    private static final long serialVersionUID = -3626919354698433538L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FROM_USER")
    private String fromUser;
    @Column(name = "TO_USER")
    private String toUser;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "ISREPLY")
    private int isReply;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "STATUS")
    private int status;

    public MessageHistory() {
        this.status = 0;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
