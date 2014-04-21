package com.huayuan.web.dto;

/**
 * Created by dell on 14-4-21.
 */
public class SavedCostDto {
    private Double savedCost;
    private Double payBackEachTerm;

    public SavedCostDto() {

    }

    public SavedCostDto(Double savedCost, Double payBackEachTerm) {
        this.savedCost = savedCost;
        this.payBackEachTerm = payBackEachTerm;
    }

    public Double getSavedCost() {
        return savedCost;
    }

    public void setSavedCost(Double savedCost) {
        this.savedCost = savedCost;
    }

    public Double getPayBackEachTerm() {
        return payBackEachTerm;
    }

    public void setPayBackEachTerm(Double payBackEachTerm) {
        this.payBackEachTerm = payBackEachTerm;
    }
}
