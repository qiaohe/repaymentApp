package com.huayuan.domain.accounting;

import com.huayuan.repository.account.PricingRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
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


    public LoanSummary build(List<Loan> loans) {
        LoanSummary summary = new LoanSummary();
        for (Loan loan : loans) {
            final Double savedCost = getSavedCost(loan);
            summary.addLoan(new LoanSummary.LoanItem(loan.getStartDate(),
                    loan.getApplication().getCreditCard().getCardNo(),
                    loan.getAmt(),
                    loan.getTerm(),
                    loan.getStartDate(),
                    loan.getPrincipal(),
                    loan.getPrincipal() - loan.getPaidPrincipal(),
                    loan.getPaidTerm(),
                    savedCost,
                    loan.getAmt() + loan.getInterest(),
                    loan.isOverDue()));
        }
        return summary;
    }
}
