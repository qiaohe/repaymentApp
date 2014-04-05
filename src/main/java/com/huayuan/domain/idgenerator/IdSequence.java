package com.huayuan.domain.idgenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Johnson on 4/4/14.
 */
@Entity
@Table(name = "ID_SEQUENCE")
public class IdSequence {
    @Id
    @Column(name = "NAME", length = 20)
    private String name;
    @Column(name = "NEXT_VALUE")
    private Long nextValue;

    public IdSequence() {
    }
    public IdSequence(String name, Long nextValue) {
        this();
        this.name = name;
        this.nextValue = nextValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNextValue() {
        return nextValue;
    }

    public void setNextValue(Long nextValue) {
        this.nextValue = nextValue;
    }
}
