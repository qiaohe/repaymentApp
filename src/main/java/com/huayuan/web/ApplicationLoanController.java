package com.huayuan.web;

import com.huayuan.domain.accounting.Pricing;
import com.huayuan.domain.accounting.core.LoanRequest;
import com.huayuan.domain.idgenerator.IdSequenceGenerator;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.repository.account.PricingRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.repository.member.CreditCardRepository;
import com.huayuan.service.AccountService;
import com.huayuan.service.ApplicationService;
import com.huayuan.service.MemberService;
import com.huayuan.web.dto.LoanApplicationDto;
import com.huayuan.web.dto.LoanRequestDto;
import com.huayuan.web.dto.SavedCostDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

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
    @Inject
    private CreditCardRepository creditCardRepository;

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
        return null;
//        Application application = new Application();
//        application.setAmt(applicationDto.getAmt());
//        application.setRepayType(RepaymentModeEnum.AVERAGE_CAPITAL_INTEREST);
//        application.setTerm(applicationDto.getTerm());
//        application.setApplicationNo(idSequenceGenerator.getApplicationNo());
//        Member member = memberService.find(applicationDto.getMemberId());
//        application.setMember(member);
//        application.setIdCard(member.getIdCard());
//        application.setExistingFlag(memberService.getApplicationStatus(applicationDto.getMemberId()));
//        if (!StringUtils.isEmpty(applicationDto.getCreditCarNo())) {
//            CreditCard creditCard = creditCardRepository.findByCardNo(applicationDto.getCreditCarNo()).get(0);
//            application.setCreditCard(creditCard);
//        }
//        application = applicationService.applyLoan(application);
//        member.setExistingFlag(application.getExistingFlag());
//        memberService.update(member);
//        applicationRepository.execute(application);
//        if (StringUtils.isNotEmpty(applicationDto.getCreditCarNo())) {
//            accountService.createLoanBy(applicationRepository.findOne(application.getApplicationNo()));
//        }
//        return application.getApplicationNo();
    }

    @RequestMapping(value = "/members/{memberId}", method = RequestMethod.POST)
    @ResponseBody
    public LoanApplicationDto getApplication(@PathVariable Long memberId) {
        Application application = applicationService.getApplicationBy(memberId);
        return createLoanApplicationDto(application);
    }

    @RequestMapping(value = "/members/{memberId}/appNo", method = RequestMethod.GET)
    @ResponseBody
    public String getApprovedAppNo(@PathVariable Long memberId) {
        Application application = applicationRepository.findByMemberIdAndStatusAndApproval_Decision(memberId, 5, "A");
        if (application != null) return application.getApplicationNo();
        return null;
    }

    @RequestMapping(value = "/members/{memberId}/loanable", method = RequestMethod.POST)
    @ResponseBody
    public boolean getApplicationBy(@PathVariable Long memberId) {
        Double avl = memberService.getAvlCrl(memberId);
        return avl != null && avl > 1000 && !hasApplicationInProgress(memberId);
    }


    @RequestMapping(value = "/{appNo}", method = RequestMethod.GET)
    @ResponseBody
    public LoanApplicationDto getApplication(@PathVariable String appNo) {
        Application application = applicationService.getApplication(appNo);
        return createLoanApplicationDto(application);
    }

    private LoanApplicationDto createLoanApplicationDto(Application application) {
        LoanApplicationDto result = new LoanApplicationDto();
        result.setAppNo(application.getApplicationNo());
        result.setAmt(application.getAmt());
        result.setTerm(application.getTerm());
        SavedCostDto sc = getSavedCost(new LoanRequestDto(application.getMember().getId(), application.getAmt(), application.getTerm()));
        result.setRepayPerTerm(sc.getPayBackEachTerm());
        result.setSaveCost(sc.getSavedCost());
        result.setIsFullyApproved(application.isFullyApproved());
        return result;
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
    public Integer getProgressOfApplication(@PathVariable Long memberId) {
        Application application = applicationService.getApplicationBy(memberId);
        return application.remainingHoursBasedOnStatus();
    }

    @RequestMapping(value = "/members/{memberId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean hasApplicationInProgress(@PathVariable Long memberId) {
        Application application = applicationService.getApplicationBy(memberId);
        return application != null;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseBody
    public Integer getApplicationCount() {
        return 50;
    }

}
