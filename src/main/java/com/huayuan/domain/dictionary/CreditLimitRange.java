package com.huayuan.domain.dictionary;

import org.apache.commons.lang.math.LongRange;

import javax.persistence.*;

/**
 * Created by dell on 14-4-10.
 */

@Entity
@Table(name = "CREDIT_LIMIT_RANGE")
public class CreditLimitRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FROM_VALUE")
    private Long from;
    @Column(name = "TO_VALUE")
    private Long to;
    @Column(name = "scale")
    private Double scale;

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean contain(Long number) {
        return new LongRange(from, to).containsLong(number);
    }
}
