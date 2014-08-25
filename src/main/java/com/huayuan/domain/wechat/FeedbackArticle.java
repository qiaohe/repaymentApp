package com.huayuan.domain.wechat;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dell on 14-6-5.
 */
@Entity
@Table(name = "WECHAT_FEEDBACK_ARTICLE")
public class FeedbackArticle implements Serializable {
    private static final long serialVersionUID = 2607564421955610036L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PICURL")
    private String picUrl;
    @Column(name = "URL")
    private String url;
    @Column(name="MENU_EVENT_ID")
    private String menuEventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMenuEventId() {
        return menuEventId;
    }

    public void setMenuEventId(String menuEventId) {
        this.menuEventId = menuEventId;
    }
}
