package com.huayuan.web;

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

    @RequestMapping(value = "/loan/{loanId}/review/{payAmt}", method = RequestMethod.GET)
    @ResponseBody
    public boolean reviewLoan(@PathVariable Long loanId, @PathVariable Double payAmt) {
        return accountService.review(loanId, payAmt);
    }

    @RequestMapping(value = "/loan/{loanId}/handle/{payAmt}/msg/{msg}", method = RequestMethod.GET)
    @ResponseBody
    public boolean handleLoan(@PathVariable Long loanId, @PathVariable Double payAmt, @PathVariable String msg) {
        return accountService.handleLoan(loanId, payAmt, msg);
    }

    @RequestMapping(value = "/loan/{loanId}/code/{transCode}", method = RequestMethod.GET)
    @ResponseBody
    public boolean transferLoan(@PathVariable Long loanId, @PathVariable String transCode) {
        return accountService.transferLoan(loanId, transCode);
    }

    @RequestMapping(value = "/loan/{loanId}/takeback", method = RequestMethod.GET)
    @ResponseBody
    public boolean takeBackLoan(@PathVariable Long loanId) {
        return accountService.takeBackLoan(loanId);
    }

    @RequestMapping(value = "/loan/{loanId}/cancel", method = RequestMethod.GET)
    @ResponseBody
    public boolean cancelLoan(@PathVariable Long loanId) {
        return accountService.cancelLoan(loanId);
    }

    @RequestMapping(value = "/loans/search", method = RequestMethod.GET)
    @ResponseBody
    public List<LoanCommonDto> findLoanTransDetails(@RequestParam("q") String query) {
        return accountRepository.findLoanTransDetails(query);
    }

    @RequestMapping(value = "/repay/{memberId}/{repayAmt}", method = RequestMethod.GET)
    @ResponseBody
    public String repay(@PathVariable Long memberId,@PathVariable Double repayAmt) {
        accountService.repay(memberId,repayAmt);
        accountService.offset(memberId);
        return "1";
    }
}
