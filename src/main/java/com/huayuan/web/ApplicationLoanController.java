package com.huayuan.web;

import com.huayuan.domain.accounting.Pricing;
import com.huayuan.domain.accounting.core.LoanRequest;
import com.huayuan.domain.idgenerator.IdSequenceGenerator;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.Approval;
import com.huayuan.domain.loanapplication.RepaymentModeEnum;
import com.huayuan.domain.member.Member;
import com.huayuan.repository.account.PricingRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.service.AccountService;
import com.huayuan.service.ApplicationService;
import com.huayuan.service.MemberService;
import com.huayuan.domain.credit.ApplicationSummary;
import com.huayuan.web.dto.LoanApplicationDto;
import com.huayuan.web.dto.LoanRequestDto;
import com.huayuan.web.dto.SavedCostDto;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Johnson on 4/7/14.
 */
@Controller(value = "applicationLoanController")
@RequestMapping(value = "/app")
public class ApplicationLoanController {
    @Inject
    private IdSequenceGenerator idSequenceGenerator;
    @Inject
    private ApplicationRepository applicationRepository;
    @Inject
    private ApplicationService applicationService;
    @Inject
    private MemberService memberService;
    @Inject
    private PricingRepository pricingRepository;
    @Inject
    private AccountService accountService;

    @RequestMapping(value = "/saveCost", method = RequestMethod.POST)
    @ResponseBody
    public SavedCostDto getSavedCost(@RequestBody LoanRequestDto applicationDto) {
        final String rating = memberService.getRating(applicationDto.getMemberId());
        Pricing pricing = pricingRepository.findByRatingAndTerm(rating, applicationDto.getTerm());
        final Double saved = pricing.getSavedPerOneHundred() * applicationDto.getAmt() / 100;
        final double payBackEachTerm = new LoanRequest(applicationDto.getAmt(), pricing.getApr(), applicationDto.getTerm(), null).getMonthlyRepay();
        return new SavedCostDto(saved, payBackEachTerm);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String applyLoan(@RequestBody LoanRequestDto applicationDto) {
        Application application = new Application();
        application.setAmt(applicationDto.getAmt());
        application.setRepayType(RepaymentModeEnum.AVERAGE_CAPITAL_INTEREST);
        application.setTerm(applicationDto.getTerm());
        application.setApplicationNo(idSequenceGenerator.getApplicationNo());
        Member member = memberService.find(applicationDto.getMemberId());
        application.setMember(member);
        application.setIdCard(member.getIdCard());
        application.setExistingFlag(memberService.getApplicationStatus(applicationDto.getMemberId()));
        application = applicationService.applyLoan(application);
        applicationRepository.execute(application);
        return application.getApplicationNo();
    }

    @RequestMapping(value = "/members/{memberId}", method = RequestMethod.POST)
    @ResponseBody
    public LoanApplicationDto getApplication(@PathVariable Long memberId) {
        Application application = applicationService.getApplicationBy(memberId);
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setAppNo(application.getApplicationNo());
        loanApplicationDto.setAmt(application.getAmt());
        loanApplicationDto.setTerm(application.getTerm());
        SavedCostDto sc = getSavedCost(new LoanRequestDto(memberId, application.getAmt(), application.getTerm()));
        loanApplicationDto.setRepayPerTerm(sc.getPayBackEachTerm());
        loanApplicationDto.setSaveCost(sc.getSavedCost());
        return loanApplicationDto;
    }

    @RequestMapping(value = "/members/{memberId}/creditCard/{creditCardNo}", method = RequestMethod.POST)
    @ResponseBody
    public boolean bindCreditCard(@PathVariable Long memberId, @PathVariable String creditCardNo) {
        Application application = applicationService.bindCreditCard(memberId, creditCardNo);
        accountService.createLoanBy(application);
        return true;
    }

    @RequestMapping(value = "/members/{memberId}/progress", method = RequestMethod.GET)
    @ResponseBody
    public double getProgressOfApplication(@PathVariable Long memberId) {
        Application application = applicationService.getApplicationBy(memberId);
        DateTime t1 = new DateTime(application.getCreateTime());
        DateTime t2 = new DateTime();
        return Hours.hoursBetween(t1, t2).getHours() / 48;
    }

    @RequestMapping(value = "/members/{memberId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean hasApplicationInProgress(@PathVariable Long memberId) {
        Application application = applicationService.getApplicationBy(memberId);
        return application != null;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ApplicationSummary> getApps() {
        return applicationService.getApplicationSummaries();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<ApplicationSummary> searchApps(@RequestParam("q") String query) {
        return applicationService.getApplicationSummaries(query);
    }


    @RequestMapping(value = "/{appNo}", method = RequestMethod.GET)
    @ResponseBody
    public Object getApplicationBy(@PathVariable String appNo) {
        return null;
//        return applicationService.getApplicationBy(appNo);
    }

}
