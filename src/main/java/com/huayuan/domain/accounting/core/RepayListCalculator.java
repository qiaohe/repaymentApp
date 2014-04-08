package com.huayuan.domain.accounting.core;

import org.joda.time.DateTime;

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

    public List<RepayItem> calculate() {
        List<RepayItem> result = new ArrayList<>();
//        for(int i = 1; i <= loan.getTerm(); i++) {
//            RepayItem pi = new RepayItem();
//            pi.setTermNo(i);
//            pi.setDueDate(new DateTime().plusMonths(1).toDate());
//            return null;
////            Math.pow()
////
////
////                    =ROUND((C4*C7*POWER(1+C7,C5))/(POWER(1+C7,C5)-1),2)
//        }
        return null;

    }



}
