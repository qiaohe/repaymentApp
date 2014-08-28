package com.huayuan.domain.wechat;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 14-8-28.
 */
@Entity
@Table(name = "WECHAT_REPLY_MESSAGE")
public class ReplyMessage implements Serializable {
    private static final long serialVersionUID = -1102015151264998628L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "KEYWORDS")
    private String keywords;
    @Column(name = "REPLY_MESSAGE")
    private String replyMessage;
    @Column(name = "ACTIVE")
    private Boolean active;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
