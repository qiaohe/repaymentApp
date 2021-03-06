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
    private Double totalDueAmt = DEFAULT_DOUBLE;
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

    public void setTotalDueAmt(Double totalDueAmt) {
        this.totalDueAmt = totalDueAmt;
    }

    public Double getTotalDueAmt() {
        return totalDueAmt;
    }

    public void addLoan(LoanItem loanItem) {
        if (!loans.contains(loanItem)) {
            totalAmount += loanItem.getAmount();
            totalPrincipal += loanItem.getPrincipal();
            totalSavedCost += loanItem.getSavedCost();
            totalDueAmt += loanItem.getDueAmt();
            loans.add(loanItem);
        }
    }

    public void removeLoan(LoanItem loanItem) {
        if (loans.contains(loanItem)) {
            loans.remove(loanItem);
        }
    }

    public static class LoanItem {
        private Long loanId;
        private Date applyDate;
        private String creditCardNo;
        private Double amount;
        private Integer term;
        private Date startDate;
        private Double principal;
        private Double restPrincipal;
        private Integer paidTerm;
        private Double savedCost;
        private Double dueAmt;
        private Double curDueAmt;
        private Double lastDueAmt;
        private boolean isOverDue;
        private Integer status;
        private List<RepayOffset> repayList;

        public LoanItem() {
        }

        public LoanItem(Long loanId, Date applyDate, String creditCardNo, Double amount, Integer term, Date startDate,
                        Double principal, Double restPrincipal, Integer paidTerm, Double savedCost,
                        Double dueAmt,Double curDueAmt,Double lastDueAmt, boolean isOverDue, Integer status,List<RepayOffset> repayList) {
            this.loanId = loanId;
            this.applyDate = applyDate;
            this.creditCardNo = creditCardNo;
            this.amount = amount;
            this.term = term;
            this.startDate = startDate;
            this.principal = principal;
            this.restPrincipal = restPrincipal;
            this.paidTerm = paidTerm;
            this.savedCost = savedCost;
            this.dueAmt = dueAmt;
            this.curDueAmt = curDueAmt;
            this.lastDueAmt = lastDueAmt;
            this.isOverDue = isOverDue;
            this.status = status;
            this.repayList = repayList;
        }

        public Long getLoanId() {
            return loanId;
        }

        public void setLoanId(Long loanId) {
            this.loanId = loanId;
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

        public Integer getPaidTerm() {
            return paidTerm;
        }

        public void setPaidTerm(Integer paidTerm) {
            this.paidTerm = paidTerm;
        }

        public Double getSavedCost() {
            return savedCost;
        }

        public void setSavedCost(Double savedCost) {
            this.savedCost = savedCost;
        }

        public Double getDueAmt() {
            return dueAmt;
        }

        public void setDueAmt(Double dueAmt) {
            this.dueAmt = dueAmt;
        }

        public Double getCurDueAmt() {
            return curDueAmt;
        }

        public void setCurDueAmt(Double curDueAmt) {
            this.curDueAmt = curDueAmt;
        }

        public Double getLastDueAmt() {
            return lastDueAmt;
        }

        public void setLastDueAmt(Double lastDueAmt) {
            this.lastDueAmt = lastDueAmt;
        }

        public boolean isOverDue() {
            return isOverDue;
        }

        public void setOverDue(boolean isOverDue) {
            this.isOverDue = isOverDue;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public List<RepayOffset> getRepayList() {
            return repayList;
        }

        public void setRepayList(List<RepayOffset> repayList) {
            this.repayList = repayList;
        }
    }
}
