package com.huayuan.domain.accounting;

import com.huayuan.common.util.Day;
import com.huayuan.repository.account.PricingRepository;
import com.huayuan.repository.account.RepayOffsetRepository;
import com.huayuan.repository.account.RepayPlanRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Created by dell on 14-5-4.
 */
@Component(value = "loanSummaryBuilder")
public class LoanSummaryBuilder {
    @Inject
    private PricingRepository pricingRepository;
    @Inject
    private RepayOffsetRepository repayOffsetRepository;
    @Inject
    private RepayPlanRepository repayPlanRepository;


    private Double getSavedCost(Loan loan) {
        Pricing pricing = pricingRepository.findByRatingAndTerm(loan.getRating(), loan.getTerm());
        return pricing.getSavedPerOneHundred() * loan.getAmt() / 100;
    }

    private Double getAmtWithinThisPeriod(Long loanId) {
        List<RepayPlan> plans = repayPlanRepository.findByLoanIdAndDueDateLessThan(loanId, Day.TODAY.nextMonth());
        Double result = 0d;
        for (RepayPlan plan : plans) {
            result += plan.getDueAmt() + plan.getOverDue_Interest();
        }
        return result;
    }

    public LoanSummary build(List<Loan> loans) {
        LoanSummary summary = new LoanSummary();
        for (Loan loan : loans) {
            final Double savedCost = getSavedCost(loan);
            summary.addLoan(new LoanSummary.LoanItem(loan.getId(), loan.getStartDate(),
                    loan.getApplication().getCreditCard().getCardNo(),
                    loan.getAmt(),
                    loan.getTerm(),
                    loan.getStartDate(),
                    loan.getPrincipal(),
                    loan.getPrincipal() - loan.getPaidPrincipal(),
                    loan.getPaidTerm(),
                    savedCost,
                    loan.getPrincipal() + loan.getInterest(),
                    getAmtWithinThisPeriod(loan.getId()),
                    loan.getAmt() + loan.getInterest() - loan.getPaidPrincipal() - loan.getPaidInterest(),
                    loan.isOverDue(),
                    loan.getStatus(),
                    repayOffsetRepository.findByLoan_IdOrderByTermNoDesc(loan.getId())));
        }
        List<LoanSummary.LoanItem> loanItemList = summary.getLoans();
        Collections.sort(loanItemList,new LoanComparator());
        summary.setLoans(loanItemList);
        return summary;
    }
}
