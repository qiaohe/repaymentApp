package com.huayuan.web;

import com.huayuan.common.App;
import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.credit.PbocSummary;
import com.huayuan.domain.member.IdCard;
import com.huayuan.repository.credit.PbocRepository;
import com.huayuan.repository.member.IdCardRepository;
import com.huayuan.service.MemberService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.FileOutputStream;
import java.net.URL;
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

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void exportIdCardAsPdf() {
        final String path = App.getInstance().getIdCardImageBase() + "/";
        for (IdCard idCard : idCardRepository.findFromPbocOut()) {
            final String front = path + idCard.getImageFront();
            final String back = path + idCard.getImageBack();
            exportToPdf(path + idCard.getIdNo() + ".pdf", new String[]{front, back});
        }
    }

    private void exportToPdf(String pdfFileName, String[] images) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
            document.open();
            for (String imageFileName : images) {
                Image image = Image.getInstance(imageFileName);
//                image.setAbsolutePosition(500f, 650f);
                image.scaleAbsolute(550f, 350f);
                document.add(image);
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
