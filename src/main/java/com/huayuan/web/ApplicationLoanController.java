package com.huayuan.web;

import com.huayuan.domain.idgenerator.IdSequenceGenerator;
import com.huayuan.domain.loanapplication.Application;
import com.huayuan.service.ApplicationService;
import com.huayuan.web.dto.ApplicationDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;

/**
 * Created by Johnson on 4/7/14.
 */
@Controller(value = "applicationLoanController")
@RequestMapping("/app")
public class ApplicationLoanController {
    @Inject
    private IdSequenceGenerator idSequenceGenerator;

    @Inject
    private ApplicationService applicationService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    private String applyLoan(@RequestBody ApplicationDto applicationDto) {
        Application application = new Application();
        application.setAmt(applicationDto.getAmt());
        application.setRepayType(applicationDto.getRepaymentMode());
        application.setTerm(applicationDto.getTerm());
        application.setTitle(applicationDto.getTitle());
        application.setApplicationNo(idSequenceGenerator.getApplicationNo());
        applicationService.applyLoan(application);
        return null;
    }
}
