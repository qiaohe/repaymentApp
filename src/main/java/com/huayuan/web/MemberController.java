package com.huayuan.web;

import com.huayuan.domain.BillMailbox;
import com.huayuan.domain.Member;
import com.huayuan.domain.recognizer.IdCardInfo;
import com.huayuan.domain.recognizer.IdCardRecognizer;
import com.huayuan.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Created by Johnson on 3/19/14.
 */
@Controller
@RequestMapping("/members")
public class MemberController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
    @Autowired
    MemberService memberService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/{id}/testCreditLimit", method = RequestMethod.GET)
    public ModelAndView register(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("testCreditLimit");
        Member member = new Member();
        member.setId(id);
        model.addObject("member", member);
        return model;
    }

    @RequestMapping(value = "/{id}/idCardFront", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadIdCardFront(@PathVariable Long id, @RequestParam("idCardFrontFile") MultipartFile idCardFrontFile) throws IOException {
        if (idCardFrontFile.isEmpty())
            throw new IllegalArgumentException("error.member.idCard.front.bad.argument.empty");
        IdCardRecognizer recognizer = new IdCardRecognizer(idCardFrontFile.getBytes());
        IdCardInfo cardInfo = recognizer.recognize(true);
        Member member = memberService.find(id);
        if (member.getIdCard() != null) {
            memberService.removeIdCard(member.getIdCard());
        }
        memberService.addIdCard(member, cardInfo);
        return cardInfo.getIdCardNumber();
    }

    @RequestMapping(value = "/{id}/idCardBack", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadIdCardBack(@PathVariable Long id, @RequestParam("idCardBackFile") MultipartFile idCardBackFile) throws IOException {
        if (idCardBackFile.isEmpty())
            throw new IllegalArgumentException("error.member.idCard.back.bad.argument.empty");
        IdCardRecognizer recognizer = new IdCardRecognizer(idCardBackFile.getBytes());
        IdCardInfo idCardInfo = recognizer.recognize(false);
        memberService.updateIdCard(memberService.find(id), idCardInfo.getIdCard());
        return idCardInfo.getValidateThru().toString();
    }

    @RequestMapping(value = "/{id}/creditCard", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadCreditCard(@PathVariable Long id, @RequestParam("creditCardFile") MultipartFile creditCardFile) {
        return "1";
    }

    @RequestMapping(value = "/{id}/updateMember", method = RequestMethod.POST)
    @ResponseBody
    public String updateMember(@ModelAttribute Member member, @PathVariable Long id, String billEmail, String passwordOfBillEmail) {
        member.addBillMailbox(new BillMailbox(member, billEmail, passwordOfBillEmail));
        memberService.register(member);
        return "5000";
    }

    @RequestMapping(value = "/{id}/testResult", method = RequestMethod.GET)
    public ModelAndView testResult(@PathVariable Long id, String crl) {
        ModelAndView model = new ModelAndView("testResult");
        model.addObject("crl", crl);
        model.addObject("id",id);
        return model;
    }
}
