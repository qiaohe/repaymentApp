package com.huayuan.web.dto;

/**
 * Created by dell on 14-4-14.
 */
public class CreditLimitDto {
    private Double creditLimit;
    private String rankOfLimit;

    public CreditLimitDto() {
    }

    public CreditLimitDto(Double creditLimit, String rankOfLimit) {
        this.creditLimit = creditLimit;
        this.rankOfLimit = rankOfLimit;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getRankOfLimit() {
        return rankOfLimit;
    }

    public void setRankOfLimit(String rankOfLimit) {
        this.rankOfLimit = rankOfLimit;
    }
}
