package com.huayuan.web;

import com.huayuan.domain.accounting.Loan;
import com.huayuan.domain.accounting.LoanSummary;
import com.huayuan.domain.accounting.RepayPlan;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.service.AccountService;
import com.huayuan.web.dto.LoanCommonDto;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dell on 14-5-4.
 */
@Controller(value = "accountingController")
@RequestMapping(value = "/account")
@Transactional
public class AccountingController {
    @Inject
    private AccountService accountService;
    @Inject
    private AccountRepository accountRepository;

    @RequestMapping(value = "/members/{memberId}", method = RequestMethod.GET)
    @ResponseBody
    public LoanSummary getLoans(@PathVariable Long memberId) {
        return accountService.getLoansSummary(memberId);
    }

    @RequestMapping(value = "/loans/{loanId}", method = RequestMethod.GET)
    @ResponseBody
    public List<RepayPlan> getLoanBy(@PathVariable Long loanId) {
        return accountService.getRepayPlan(loanId);
    }

    @RequestMapping(value = "/loans/{loanId}/review", method = RequestMethod.GET)
    @ResponseBody
    public boolean reviewLoan(@PathVariable Long loanId) {
        return accountService.review(loanId);
    }

    @RequestMapping(value = "/loan/{loanId}/code/{transCode}", method = RequestMethod.GET)
    @ResponseBody
    public boolean transferLoan(@PathVariable Long loanId,@PathVariable String transCode) {
        return accountService.transferLoan(loanId,transCode);
    }

    @RequestMapping(value = "/loans/search", method = RequestMethod.GET)
    @ResponseBody
    public List<LoanCommonDto> findLoanTransDetails(@RequestParam("q") String query) {
        return accountRepository.findLoanTransDetails(query);
    }
}
