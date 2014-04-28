package com.huayuan.domain.credit;

import java.util.Date;

/**
 * Created by dell on 14-4-28.
 */
public class PbocSummary {
    private Long id;
    private String certNo;
    private String name;
    private Date createTime;
    private String keyiner;
    private Integer status;

    public PbocSummary() {
    }

    public PbocSummary(Long id, String certNo, String name, Date createTime, String keyiner, Integer status) {
        this();
        this.id = id;
        this.certNo = certNo;
        this.name = name;
        this.createTime = createTime;
        this.keyiner = keyiner;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getKeyiner() {
        return keyiner;
    }

    public void setKeyiner(String keyiner) {
        this.keyiner = keyiner;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
