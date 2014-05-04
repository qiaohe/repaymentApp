package com.huayuan.domain.accounting;

import com.huayuan.domain.loanapplication.CreditResult;
import com.huayuan.repository.account.PricingRepository;
import com.huayuan.repository.credit.CreditResultRepository;
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
    @Inject
    CreditResultRepository creditResultRepository;

    private String getRating(Long memberId) {
        CreditResult creditResult = creditResultRepository.findByMemberId(memberId);
        return creditResult.getLastRating();
    }

    public LoanSummary build(Long memberId, List<Loan> loans) {
        LoanSummary summary = new LoanSummary();
        final String rating = getRating(memberId);
        for (Loan loan : loans) {
            summary.setTotalAmount(summary.getTotalAmount() + loan.getAmt());
            summary.setTotalPrincipal(summary.getTotalPrincipal() + loan.getAmt());
            Pricing pricing = pricingRepository.findByRatingAndTerm(rating, loan.getTerm());
            summary.setTotalSavedCost(summary.getTotalSavedCost() + pricing.getSavedPerOneHundred() * loan.getAmt() / 100);
            summary.setCurrentBalance(loan.currentBalance(new Date()));
            summary.setOverDue(loan.isOverDue());
            summary.addLoan(new LoanSummary.LoanItem(loan.getStartDate(),
                    loan.getApplication().getCreditCard().getCardNo(),
                    loan.getAmt(),
                    loan.getTerm(),
                    loan.getStartDate(),
                    loan.getPrincipal(),
                    loan.getPrincipal() - loan.getPaidPrincipal()));
        }
        return summary;
    }
}
