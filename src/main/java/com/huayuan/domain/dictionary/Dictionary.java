package com.huayuan.domain.dictionary;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 14-4-4.
 */
@Entity
@Table(name = "DICTIONARY")
public class Dictionary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "VALUE")
    private String value;
    @Basic
    @Column(name = "TYPE")
    private String type;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "STATUS")
    private int status;
    @Basic
    @Column(name = "CRL_INDEX")
    private Double crlIndex;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    public Dictionary() {
       createDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getCrlIndex() {
        return crlIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCrlIndex(Double crlIndex) {
        this.crlIndex = crlIndex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
