package com.huayuan.web;

import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.credit.PbocSummary;
import com.huayuan.domain.member.IdCard;
import com.huayuan.repository.credit.PbocRepository;
import com.huayuan.repository.member.IdCardRepository;
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
            IdCard idCard = idCardRepository.findByIdNo(pboc.getCertNo());
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
}
