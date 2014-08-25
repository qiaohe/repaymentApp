package com.huayuan.domain.wechat;

import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnson on 4/15/14.
 */
@XmlType(name = "eventMessage")
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    private static final String SUBSCRIBE_EVENT = "subscribe";
    @XmlElement(name = "ToUserName")
    private String toUserName;
    @XmlElement(name = "FromUserName")
    private String fromUserName;
    @XmlElement(name = "CreateTime")
    private Long createTime;
    @XmlElement(name = "MsgType")
    private String msgType;
    @XmlElement(name = "Event")
    private String event;
    @XmlElement(name = "EventKey")
    private String eventKey;
    @XmlElement(name = "Content")
    private String content;
    @XmlElement(name = "FuncFlag")
    private String funcFlag;
    @XmlElement(name = "ArticleCount")
    private Integer articleCount;
    @XmlElement(name = "Articles")
    private Articles articles;

    public Message() {

    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getFuncFlag() {
        return funcFlag;
    }

    public void setFuncFlag(String funcFlag) {
        this.funcFlag = funcFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public Articles getArticles() {
        return articles;
    }

    public void setArticles(Articles articles) {
        this.articles = articles;
    }

    public boolean isSubscribe() {
        return StringUtils.isNotEmpty(event) && event.equalsIgnoreCase(SUBSCRIBE_EVENT);
    }

    public boolean isCustomMenuEvent() {
        return StringUtils.isNotEmpty(event) && (event.equals("VIEW") || event.equals("CLICK"));
    }

    public boolean isTelephoneVerification() {
        return StringUtils.isNotEmpty(content) && StringUtils.startsWithAny(content, new String[]{"#", "＃"});
    }

    public boolean isAbout() {
        return "M_003_ABOUT".equals(eventKey);
    }

    public boolean isFeedback() {
        return "M_003_FEEDBACK".equals(eventKey);
    }

    public boolean isAboutTeam() {
        return "M_003_ABOUT_TEAM".equals(eventKey);
    }

    @XmlRootElement(name = "Articles")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Articles {
        public Articles() {
        }

        @XmlElement(name = "item")
        private List<Article> articles = new ArrayList<>();

        public List<Article> getArticles() {
            return articles;
        }

        public void setArticles(List<Article> articles) {
            this.articles = articles;
        }

        public void addArticle(Article article) {
            articles.add(article);
        }

        public void removeArticle(Article article) {
            articles.remove(article);
        }
    }

    @XmlType(name = "article")
    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Article {
        @XmlElement(name = "Title")
        private String title;
        @XmlElement(name = "Description")
        private String description;
        @XmlElement(name = "PicUrl")
        private String picUrl;
        @XmlElement(name = "Url")
        private String url;

        public Article() {
        }

        public Article(String title, String description, String picUrl, String url) {
            this.title = title;
            this.description = description;
            this.picUrl = picUrl;
            this.url = url;
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
    }
}
