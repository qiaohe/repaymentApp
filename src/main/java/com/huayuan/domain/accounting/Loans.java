package com.huayuan.domain.accounting;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 14-5-13.
 */
public class Loans {
    private static final List<Integer> STATUS_IND = Arrays.asList(10, 8, 9, 0, 1, 2);
    private Integer count;
    private Integer status = 0;
    private Double amount = 0d;
    private Integer curDelq = 0;
    private Integer maxDelq = 0;
    private Map<Integer, Integer> statusCountMap = new ConcurrentHashMap<>();
    private List<Loan> loans;

    public Loans() {
    }

    public Loans(final List<Loan> loans) {
        this.loans = loans;
        count = loans.size();
        for (Loan loan : loans) {
            if (STATUS_IND.indexOf(status) < STATUS_IND.indexOf(loan.getStatus())) status = loan.getStatus();
            if (curDelq < loan.getCurDelq()) curDelq = loan.getCurDelq();
            if (maxDelq < loan.getMaxDelq()) curDelq = loan.getMaxDelq();
            if(loan.getStatus() != 10) {
                amount += loan.getAmt();
            }
            if (!statusCountMap.containsKey(loan.getStatus())) statusCountMap.put(loan.getStatus(), 1);
            statusCountMap.put(loan.getStatus(), statusCountMap.get(loan.getStatus()) + 1);
        }
    }

    public Integer getCount() {
        return count;
    }

    public Integer getStatus() {
        return status;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getCurDelq() {
        return curDelq;
    }

    public Integer getMaxDelq() {
        return maxDelq;
    }

    public Map<Integer, Integer> getStatusCountMap() {
        return statusCountMap;
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
