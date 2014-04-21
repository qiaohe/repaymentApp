package com.huayuan.web;

import com.huayuan.domain.accounting.Pricing;
import com.huayuan.domain.idgenerator.IdSequenceGenerator;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.RepaymentModeEnum;
import com.huayuan.repository.account.PricingRepository;
import com.huayuan.repository.applicationloan.ApplicationRepository;
import com.huayuan.service.ApplicationService;
import com.huayuan.service.MemberService;
import com.huayuan.web.dto.ApplicationDto;
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


    @RequestMapping(value = "/saveCost", method = RequestMethod.POST)
    public Double getSavedCost(@RequestBody SavedCostDto savedCostDto) {
        final String rating = memberService.getRating(savedCostDto.getMemberId());
        Pricing pricing = pricingRepository.findByRatingAndTerm(rating, savedCostDto.getTerm());
        return pricing.getSavedPerOneHundred() * savedCostDto.getAmt() / 100;
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String applyLoan(@PathVariable Long id, @RequestBody ApplicationDto applicationDto) {
        Application application = new Application();
        application.setAmt(applicationDto.getAmt());
        application.setRepayType(RepaymentModeEnum.AVERAGE_CAPITAL_INTEREST);
        application.setTerm(applicationDto.getTerm());
        application.setApplicationNo(idSequenceGenerator.getApplicationNo());
        application.setMember(memberService.find(id));
        application.setExistingFlag(memberService.getApplicationStatus(id));
        application = applicationService.applyLoan(application);
        applicationRepository.execute(application);
        return application.getApplicationNo();
    }
}
