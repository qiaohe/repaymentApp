package com.huayuan.web;

import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.credit.PbocSummary;
import com.huayuan.repository.credit.PbocRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    @ResponseBody
    public List<PbocSummary> getMember() {
        return pbocRepository.findByStatus();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Pboc getPbocBy(Long id) {
        return pbocRepository.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Pboc updatePbocBy(@RequestBody Pboc pboc) {
        return pbocRepository.save(pboc);
    }
}
