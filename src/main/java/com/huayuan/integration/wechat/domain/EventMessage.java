package com.huayuan.integration.wechat.domain;

import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.*;

/**
 * Created by Johnson on 4/15/14.
 */
@XmlType(name = "eventMessage")
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventMessage {
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

    public EventMessage() {

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

    public boolean isSubscribeEvent() {
        return StringUtils.isNotEmpty(event) && event.equalsIgnoreCase(SUBSCRIBE_EVENT);
    }

    public boolean isCustomMenuEvent() {
        return StringUtils.isNotEmpty(event) && (event.equals("VIEW") || event.equals("CLICK"));
    }

    public boolean isTvMessage() {
        return StringUtils.isNotEmpty(content) && (content.contains("#") || content.contains("＃"));
    }
}
