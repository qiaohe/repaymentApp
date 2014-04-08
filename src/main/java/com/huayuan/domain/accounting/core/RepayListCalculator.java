package com.huayuan.domain.accounting.core;

import org.apache.commons.math.util.MathUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 14-4-8.
 */
public class RepayListCalculator {
    private static final int ROUNDING_PATTERN = 7;
    private final LoanRequest loan;

    public RepayListCalculator(LoanRequest loan) {
        this.loan = loan;
    }

    private boolean isLastTerm(final int termNo) {
        return termNo == loan.getTerm();
    }

    public List<RepayItem> calculate() {
        Double restPrincipal = loan.getPrincipal();
        List<RepayItem> result = new ArrayList<>();
        for (int i = 1; i <= loan.getTerm(); i++) {
            Double amt = loan.getMonthlyRepay();
            Double dueInterest = MathUtils.round(restPrincipal * loan.getAprByMonth(), 2, ROUNDING_PATTERN);
            Double duePrincipal = MathUtils.round(amt - dueInterest, 2, ROUNDING_PATTERN);
            restPrincipal = MathUtils.round(restPrincipal - duePrincipal, 2, ROUNDING_PATTERN);
            if (isLastTerm(i)) {
                duePrincipal = result.get(result.size() - 1).getRestPrincipal();
                amt = dueInterest + duePrincipal;
                restPrincipal = 0d;
            }
            RepayItem pi = new RepayItem(i, new DateTime().plusMonths(1).toDate(), amt, duePrincipal, dueInterest, restPrincipal);
            result.add(pi);
        }
        return result;
    }

    public static void main(String[] args) {
        RepayListCalculator calculator = new RepayListCalculator(new LoanRequest(1002d, 0.15, 12));
        calculator.calculate();

    }
}
