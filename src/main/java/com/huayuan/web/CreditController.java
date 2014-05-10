package com.huayuan.web;

import com.huayuan.domain.loanapplication.Approval;
import com.huayuan.domain.loanapplication.TelephoneTV;
import com.huayuan.repository.applicationloan.ApprovalRepository;
import com.huayuan.service.ApplicationService;
import com.huayuan.service.CreditService;
import com.huayuan.web.dto.ApplicationApproveDto;
import com.huayuan.web.dto.TelephoneTvDto;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dell on 14-5-10.
 */
@Controller(value = "creditController")
@RequestMapping(value = "/credit")
public class CreditController {
    @Inject
    private CreditService creditService;
    @Inject
    private ApplicationService applicationService;

    @Inject
    private ApprovalRepository approvalRepository;

    @RequestMapping(value = "/telephoneTV", method = RequestMethod.POST)
    @ResponseBody
    public TelephoneTV makeTelephoneTV(@RequestBody TelephoneTvDto telephoneTvDto) {
        TelephoneTV telephoneTV = telephoneTvDto.createEntity();
        telephoneTV.setApplication(applicationService.getApplication(telephoneTvDto.getAppNo()));
        return creditService.makeTelephoneTv(telephoneTV);
    }

    @RequestMapping(value = "/telephoneTV/{appNo}", method = RequestMethod.GET)
    @ResponseBody
    public List<TelephoneTV> makeTelephoneTV(@PathVariable String appNo) {
        return creditService.getTelephoneTVs(appNo);
    }


    @RequestMapping(value = "approve/{appNo}", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Approval approve(@PathVariable String appNo, @RequestBody ApplicationApproveDto applicationApproveDto) {
        Approval approval = applicationService.getApplication(appNo).getApproval();
        if (approval == null)
            throw new IllegalArgumentException("Application No" + appNo + " does not exists in Approval");
        approval.setApr(applicationApproveDto.getApr());
        approval.setSugCrl(applicationApproveDto.getSugCrl());
        approval.setAmt(applicationApproveDto.getAmt());
        approval.setReason1(applicationApproveDto.getReason1());
        approval.setReason2(applicationApproveDto.getReason2());
        approval.setReason3(applicationApproveDto.getReason3());
        approval.setOpinion(applicationApproveDto.getOpinion());
        approval.setDecision(applicationApproveDto.getDecision());
        approval.setProfile(applicationApproveDto.getProfile());
        approval.setCreditor(applicationApproveDto.getCreditor());
        return creditService.approve(approval);
    }
}
