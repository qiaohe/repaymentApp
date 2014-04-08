package com.huayuan.domain.accounting.core;

import com.huayuan.common.Day;
import com.huayuan.common.Number;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 14-4-8.
 */
public class RepayListCalculator {
    private final LoanRequest loan;

    public RepayListCalculator(LoanRequest loan) {
        this.loan = loan;
    }

    private boolean isLastTerm(final int termNo) {
        return termNo == loan.getTerm();
    }

    public List<RepayItem> calculate() {
        List<RepayItem> result = new ArrayList<>();
        Double restPrincipal = loan.getPrincipal();
        Double amt = loan.getMonthlyRepay();
        double monthlyApr = loan.getAprByMonth();
        for (int termNo = 1; termNo <= loan.getTerm(); termNo++) {
            Double dueInterest = new Number(restPrincipal * monthlyApr).getValue();
            Double duePrincipal = isLastTerm(termNo) ? restPrincipal : new Number(amt - dueInterest).getValue();
            restPrincipal = new Number(restPrincipal - duePrincipal).getValue();
            RepayItem pi = new RepayItem(termNo, new Day(loan.getStartDate()).plusMonths(termNo), amt, duePrincipal, dueInterest, restPrincipal);
            result.add(pi);
        }
        return result;
    }
}
