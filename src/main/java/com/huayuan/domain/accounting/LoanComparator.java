package com.huayuan.domain.accounting;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Richard Xue on 14-5-27.
 */
public class LoanComparator implements Comparator<LoanSummary.LoanItem> {

    @Override
    public int compare(LoanSummary.LoanItem loanItem1, LoanSummary.LoanItem loanItem2) {
        // 0-正常 1-逾期 2-打呆 8-等待放款 9-结清 10-取消
        if(loanItem1.getStatus() == 9 || loanItem1.getStatus() == 10) {
            return 1;
        }
        Date startDate1 = loanItem1.getStartDate();
        Date startDate2 = loanItem2.getStartDate();
        int paidTerm1 = 0;
        int paidTerm2 = 0;
        List<RepayOffset> repayOffsetList1 = loanItem1.getRepayList();
        List<RepayOffset> repayOffsetList2 = loanItem2.getRepayList();
        if(repayOffsetList1 != null) {
            paidTerm1 = repayOffsetList1.size();
        }
        if(repayOffsetList2 != null) {
            paidTerm2 = repayOffsetList2.size();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate1);
        calendar.add(Calendar.MONTH,paidTerm1);
        startDate1 = calendar.getTime();

        calendar.setTime(startDate2);
        calendar.add(Calendar.MONTH,paidTerm2);
        startDate2 = calendar.getTime();
        return startDate1.compareTo(startDate2);
    }

}
