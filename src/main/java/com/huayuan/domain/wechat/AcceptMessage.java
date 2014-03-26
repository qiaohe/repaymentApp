package com.huayuan.domain.wechat;

import java.io.Serializable;

/**
 * Created by Li JiWang on 14-3-25.
 */
public class AcceptMessage implements Serializable {

    private String toUserName;      //开发者微信号
    private String fromUserName;    //发送方帐号（一个OpenID）
    private Integer createTime;     //消息创建时间 （整型）
    private String msgType;         //消息类型，event
    private String content;         //消息内容
    private String event;           //自定义按钮事件请求
    private String eventKey;        //事件请求key值
    private String ticket;          //二维码的ticket，可用来换取二维码图片

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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
