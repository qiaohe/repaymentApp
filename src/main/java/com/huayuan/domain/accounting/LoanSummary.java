package com.huayuan.domain.accounting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 14-5-4.
 */
public class LoanSummary {
    private static final double DEFAULT_DOUBLE = 0d;
    private Double totalAmount = DEFAULT_DOUBLE;
    private Double totalPrincipal = DEFAULT_DOUBLE;
    private Double totalSavedCost = DEFAULT_DOUBLE;
    private Double currentBalance;
    private boolean isOverDue = false;
    private List<LoanItem> loans = new ArrayList<>();

    public LoanSummary() {
    }

    public Integer getLoanCount() {
        return loans.size();
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalPrincipal() {
        return totalPrincipal;
    }

    public void setTotalPrincipal(Double totalPrincipal) {
        this.totalPrincipal = totalPrincipal;
    }

    public Double getTotalSavedCost() {
        return totalSavedCost;
    }

    public void setTotalSavedCost(Double totalSavedCost) {
        this.totalSavedCost = totalSavedCost;
    }

    public List<LoanItem> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanItem> loans) {
        this.loans = loans;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public boolean isOverDue() {
        return isOverDue;
    }

    public void setOverDue(boolean isOverDue) {
        this.isOverDue = isOverDue;
    }

    public void addLoan(LoanItem loanItem) {
        if (!loans.contains(loanItem)) {
            loans.add(loanItem);
        }
    }

    public void removeLoan(LoanItem loanItem) {
        if (loans.contains(loanItem)) {
            loans.remove(loanItem);
        }
    }

    public static class LoanItem {
        private Date applyDate;
        private String creditCardNo;
        private Double amount;
        private Integer term;
        private Date startDate;
        private Double principal;
        private Double restPrincipal;

        public LoanItem() {
        }

        public LoanItem(Date applyDate, String creditCardNo, Double amount, Integer term, Date startDate, Double principal, Double restPrincipal) {
            this.applyDate = applyDate;
            this.creditCardNo = creditCardNo;
            this.amount = amount;
            this.term = term;
            this.startDate = startDate;
            this.principal = principal;
            this.restPrincipal = restPrincipal;
        }

        public Date getApplyDate() {
            return applyDate;
        }

        public void setApplyDate(Date applyDate) {
            this.applyDate = applyDate;
        }

        public String getCreditCardNo() {
            return creditCardNo;
        }

        public void setCreditCardNo(String creditCardNo) {
            this.creditCardNo = creditCardNo;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Integer getTerm() {
            return term;
        }

        public void setTerm(Integer term) {
            this.term = term;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Double getPrincipal() {
            return principal;
        }

        public void setPrincipal(Double principal) {
            this.principal = principal;
        }

        public Double getRestPrincipal() {
            return restPrincipal;
        }

        public void setRestPrincipal(Double restPrincipal) {
            this.restPrincipal = restPrincipal;
        }
    }
}