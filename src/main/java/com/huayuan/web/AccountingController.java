package com.huayuan.web;

import com.huayuan.common.util.Constants;
import com.huayuan.domain.accounting.LoanSummary;
import com.huayuan.domain.accounting.PaymentList;
import com.huayuan.domain.accounting.RepayPlan;
import com.huayuan.repository.account.AccountRepository;
import com.huayuan.service.AccountService;
import com.huayuan.web.dto.LoanCommonDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Value("${weChat.baseUrl}")
    private String baseUrl;

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

    @RequestMapping(value = "/repay/{memberId}/{repayAmt:.+}", method = RequestMethod.GET)
    public String repay(@PathVariable Long memberId, @PathVariable Double repayAmt) {
        final String paymentGateway = accountService.getPaymentGateway(memberId, repayAmt);
        return "redirect:" + paymentGateway;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.LONG_DATE_PATTERN);
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/paymentCallback/{memberId}", method = RequestMethod.GET)
    public String repay(@PathVariable Long memberId, PaymentList paymentList) {
        accountService.addPaymentList(paymentList);
        accountService.repay(memberId, paymentList.getPayAmount());
        accountService.offset(memberId);
        final String redirectUrl = paymentList.isPaymentSuccess() ? baseUrl + "#pay-success" : baseUrl + "#pay-fail";
        return "redirect:" + redirectUrl;
    }
}