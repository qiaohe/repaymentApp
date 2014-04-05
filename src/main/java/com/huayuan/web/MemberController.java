package com.huayuan.web;

import com.huayuan.domain.member.IdCard;
import com.huayuan.domain.member.Member;
import com.huayuan.domain.recognizer.IdCardRecognizer;
import com.huayuan.service.MemberService;
import com.huayuan.web.vo.MemberVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by Johnson on 3/19/14.
 */
@Controller
@RequestMapping("/members")
public class MemberController {
    @Inject
    MemberService memberService;

    @RequestMapping(value = "/{id}/testCreditLimit", method = RequestMethod.GET)
    public ModelAndView register(@PathVariable Long id) {
        Member member = memberService.find(id);
        ModelAndView model = new ModelAndView("testCreditLimit");
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
        IdCard idCard = recognizer.recognize(true);
        Member member = memberService.find(id);
        if (member.getIdCard() != null) {
            memberService.removeIdCard(member.getIdCard());
        }
        memberService.addIdCard(member, idCard);
        return idCard.getIdNo();
    }

    @RequestMapping(value = "/{id}/idCardBack", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadIdCardBack(@PathVariable Long id, @RequestParam("idCardBackFile") MultipartFile idCardBackFile) throws IOException {
        if (idCardBackFile.isEmpty())
            throw new IllegalArgumentException("error.member.idCard.back.bad.argument.empty");
        IdCardRecognizer recognizer = new IdCardRecognizer(idCardBackFile.getBytes());
        IdCard idCard = recognizer.recognize(false);
        memberService.updateIdCard(memberService.find(id), idCard);
        return idCard.getValidThru().toString();
    }

    @RequestMapping(value = "/{id}/creditCard", method = RequestMethod.POST)
    public
    @ResponseBody
    String uploadCreditCard(@PathVariable Long id, @RequestParam("creditCardNo") String creditCardNo) {
        Member member = memberService.find(id);
        memberService.addCreditCard(member, creditCardNo);
        return creditCardNo;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void updateMember(@PathVariable Long id, @RequestBody MemberVO memberVO) {
        Member member = memberService.find(id);
        member.setEducation(memberVO.getEducation());
        member.setIndustry(memberVO.getIndustry());
        member.setEmail(member.getEmail());
        memberService.register(member);
    }

    @RequestMapping(value = "/{id}/billEmail", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void updateBillEmail(@PathVariable Long id, String billEmail, String password) {
        Member member = memberService.find(id);
        memberService.updateBillEmail(member, billEmail, password);
    }


    @RequestMapping(value = "/{id}/testResult", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView testResult(@PathVariable Long id, String crl) {
        ModelAndView model = new ModelAndView("testResult");
        model.addObject("crl", crl);
        model.addObject("id", id);
        return model;
    }
}
