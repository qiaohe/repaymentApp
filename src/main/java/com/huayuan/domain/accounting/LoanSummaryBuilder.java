package com.huayuan.domain.accounting;

import com.huayuan.repository.account.PricingRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 14-5-4.
 */
@Component(value = "loanSummaryBuilder")
public class LoanSummaryBuilder {
    @Inject
    PricingRepository pricingRepository;

    private Double getSavedCost(Loan loan) {
        Pricing pricing = pricingRepository.findByRatingAndTerm(loan.getRating(), loan.getTerm());
        return pricing.getSavedPerOneHundred() * loan.getAmt() / 100;
    }


    public LoanSummary build(Long memberId, List<Loan> loans) {
        LoanSummary summary = new LoanSummary();
        for (Loan loan : loans) {
            createSummary(summary, loan);
        }
        return summary;
    }

    private void createSummary(LoanSummary summary, Loan loan) {
        summary.setTotalAmount(summary.getTotalAmount() + loan.getAmt());
        summary.setTotalPrincipal(summary.getTotalPrincipal() + loan.getAmt());
        summary.setTotalSavedCost(summary.getTotalSavedCost() + getSavedCost(loan));
        summary.setCurrentBalance(loan.currentBalance(new Date()));
        summary.setOverDue(loan.isOverDue());
        final Double savedCost = getSavedCost(loan);
        summary.setTotalSavedCost(summary.getTotalSavedCost() + savedCost);
        summary.addLoan(new LoanSummary.LoanItem(loan.getStartDate(),
                loan.getApplication().getCreditCard().getCardNo(),
                loan.getAmt(),
                loan.getTerm(),
                loan.getStartDate(),
                loan.getPrincipal(),
                loan.getPrincipal() - loan.getPaidPrincipal(),
                loan.getPaidTerm(),
                savedCost));
    }
}
