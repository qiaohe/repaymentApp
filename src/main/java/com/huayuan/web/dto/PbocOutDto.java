package com.huayuan.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Richard Xue on 14-6-19.
 */
public class PbocOutDto implements Serializable {
    private Integer id;
    private String idNo;
    private String name;
    private Date createTime;
    private String imageFront;
    private String imageBack;

    public PbocOutDto(Integer id, String idNo, String name, Date createTime, String imageFront, String imageBack) {
        this.id = id;
        this.idNo = idNo;
        this.name = name;
        this.createTime = createTime;
        this.imageFront = imageFront;
        this.imageBack = imageBack;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImageFront() {
        return imageFront;
    }

    public void setImageFront(String imageFront) {
        this.imageFront = imageFront;
    }

    public String getImageBack() {
        return imageBack;
    }

    public void setImageBack(String imageBack) {
        this.imageBack = imageBack;
    }
}
