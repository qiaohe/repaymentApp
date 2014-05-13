package com.huayuan.web;

import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.credit.PbocSummary;
import com.huayuan.domain.member.IdCard;
import com.huayuan.repository.credit.PbocRepository;
import com.huayuan.repository.member.IdCardRepository;
import com.huayuan.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dell on 14-4-28.
 */
@Controller
@RequestMapping(value = "/pboc")
public class PbocController {
    @Inject
    private PbocRepository pbocRepository;
    @Inject
    private IdCardRepository idCardRepository;

    @Inject
    private MemberService memberService;

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    @ResponseBody
    public List<PbocSummary> getSummary() {
        return pbocRepository.findByStatus();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "pbocSummary";
    }

    @RequestMapping(value = "/{id}/idCard", method = RequestMethod.GET)
    @ResponseBody
    public String getIdCardImage(@PathVariable Long id) {
        return pbocRepository.getIdCardImage(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Pboc getPbocBy(@PathVariable Long id) {
        return pbocRepository.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Pboc updatePbocBy(@RequestBody Pboc pboc) {
        if (pboc.getStatus().equals(Pboc.CHANGE_ID)) {
            IdCard idCard = idCardRepository.findByIdNo(pboc.getCertNo()).get(0);
            idCard.setIdNo(pboc.getNewCertNo());
            idCard.setName(pboc.getNewName());
            pboc.setName(pboc.getNewName());
            pboc.setCertNo(pboc.getNewCertNo());
            idCardRepository.save(idCard);
        }
        return pbocRepository.save(pboc);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<PbocSummary> search(@RequestParam("q") String query) {
        return pbocRepository.search(query);
    }

    @RequestMapping(value = "/{idCardNo}/phone/{type}", method = RequestMethod.GET)
    @ResponseBody
    public String getMobilePhone(@PathVariable String idCardNo, @PathVariable Integer type) {
        if (type == 5) return memberService.getPhone(idCardNo);
        Pboc pboc = pbocRepository.findByCertNo(idCardNo);
        if (type == 1) return pboc.getMobile();
        if (type == 2) return pboc.getOfficeTelephoneNo();
        if (type == 3) return pboc.getHomeTelephoneNo();
        if (type == 4) return pboc.getPartnerTelephoneNo();
        return null;
    }
}
