package com.huayuan.domain.accounting;

import javax.persistence.*;

/**
 * Created by dell on 14-4-14.
 */
@Entity
@Table(name = "PRICING")
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "RATING")
    private String rating;
    @Column(name = "TERM")
    private int term;
    @Column(name = "APR")
    private Double apr;
    @Column(name = "SAVED")
    private Double savedPerOneHundred;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
    }

    public Double getSavedPerOneHundred() {
        return savedPerOneHundred;
    }

    public void setSavedPerOneHundred(Double savedPerOneHundred) {
        this.savedPerOneHundred = savedPerOneHundred;
    }
}
