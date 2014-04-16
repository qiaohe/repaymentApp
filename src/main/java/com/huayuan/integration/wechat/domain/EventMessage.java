package com.huayuan.integration.wechat.domain;

import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Johnson on 4/15/14.
 */
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

    public boolean isSubscribeEvent() {
        return StringUtils.isNotEmpty(event) && event.equalsIgnoreCase(SUBSCRIBE_EVENT);
    }

    public boolean isCustomMenuEvent() {
        return StringUtils.isNotEmpty(event) && (event.equals("VIEW") || event.equals("CLICK"));
    }
}
