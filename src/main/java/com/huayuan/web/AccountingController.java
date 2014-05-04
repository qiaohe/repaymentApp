package com.huayuan.web;

import com.huayuan.domain.accounting.Loan;
import com.huayuan.domain.accounting.Pricing;
import com.huayuan.repository.account.PricingRepository;
import com.huayuan.service.AccountService;
import com.huayuan.service.MemberService;
import com.huayuan.domain.accounting.LoanSummary;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/members/{memberId}", method = RequestMethod.GET)
    @ResponseBody
    public LoanSummary getLoans(@PathVariable Long memberId) {
        return accountService.getLoansSummary(memberId);
    }
}
