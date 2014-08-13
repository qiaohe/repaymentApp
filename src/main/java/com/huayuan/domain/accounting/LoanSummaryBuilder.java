package com.huayuan.domain.accounting;

import com.huayuan.repository.account.PricingRepository;
import com.huayuan.repository.account.RepayOffsetRepository;
import com.huayuan.repository.account.RepayPlanRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;
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
        List<RepayPlan> plans = repayPlanRepository.findByLoanIdAndDueDateLessThan(loanId);
        if(plans == null || plans.isEmpty()) {
           return 0d;
        }
        Date curDate = new Date();
        Double result = 0d;
        for(int i = 0; i < plans.size(); i++) {
            RepayPlan plan = plans.get(i);
            if(i == 0 || plan.getDueDate().compareTo(curDate) < 1) {
                result += plan.getDueAmt() + plan.getOverDue_Interest();
            }
        }
        return result;
    }

    private Date getLastRepayDate(Long loanId) {
        List<RepayPlan> plans = repayPlanRepository.findByLoanIdAndDueDateLessThan(loanId);
        if(plans == null || plans.isEmpty()) {
            return null;
        }
        Date curDate = new Date();
        Date lastRepayDate = null;
        for(int i = 0; i < plans.size(); i++) {
            RepayPlan plan = plans.get(i);
            if(i == 0 || plan.getDueDate().compareTo(curDate) < 1) {
                lastRepayDate = plan.getDueDate();
            }
        }
        return lastRepayDate;
    }

    public LoanSummary build(List<Loan> loans) {
        LoanSummary summary = new LoanSummary();
        for (Loan loan : loans) {
            final Double savedCost = getSavedCost(loan);
            summary.addLoan(new LoanSummary.LoanItem(loan.getId(), getLastRepayDate(loan.getId()),
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
