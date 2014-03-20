package com.huayuan.web;

import com.huayuan.domain.Member;
import com.huayuan.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Johnson on 3/19/14.
 */
@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @RequestMapping("/register")
    public ModelMap register(HttpServletRequest request, HttpServletResponse response, Member member) {

        ModelMap model = new ModelMap();
        model.addAttribute("member", member);
        return model;
    }

    @RequestMapping(value = "/register_save", method = RequestMethod.POST)
    public void register_save(HttpServletRequest request, HttpServletResponse response, Member member) {

        ModelMap model = new ModelMap();
//        memberService.register(member);

    }
}
