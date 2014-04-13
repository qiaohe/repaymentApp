package com.huayuan.web;

import com.huayuan.domain.idgenerator.IdSequenceGenerator;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.domain.loanapplication.RepaymentModeEnum;
import com.huayuan.domain.member.Member;
import com.huayuan.service.ApplicationService;
import com.huayuan.service.MemberService;
import com.huayuan.web.dto.ApplicationDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by Johnson on 4/7/14.
 */
@Controller(value = "applicationLoanController")
public class ApplicationLoanController {
    @Inject
    private IdSequenceGenerator idSequenceGenerator;

    @Inject
    private ApplicationService applicationService;
    @Inject
    private MemberService memberService;


    @RequestMapping(value = "members/{id}/app", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    private String applyLoan(@PathVariable Long memberId, @RequestBody ApplicationDto applicationDto) {
        Application application = new Application();
        application.setAmt(applicationDto.getAmt());
        application.setRepayType(RepaymentModeEnum.AVERAGE_CAPITAL_INTEREST);
        application.setTerm(applicationDto.getTerm());
        application.setApplicationNo(idSequenceGenerator.getApplicationNo());
        application.setMember(memberService.find(memberId));
        application = applicationService.applyLoan(application);
        return application.getApplicationNo();
    }
}
