package com.huayuan.web;

import com.huayuan.domain.loanapplication.TelephoneTV;
import com.huayuan.service.ApplicationService;
import com.huayuan.service.CreditService;
import com.huayuan.web.dto.TelephoneTvDto;
import org.springframework.stereotype.Controller;
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
}
