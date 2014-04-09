package com.huayuan.service;

import com.huayuan.common.BaseTest;
import com.huayuan.domain.member.Member;
import org.junit.Test;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Created by Administrator on 14-3-20.
 */
public class MemberServiceTest extends BaseTest {

    @Inject
    MemberService memberService;

    @Test
    public void save() {
        Member member = new Member();
        member.setName("dd");
        memberService.update(member);
    }
}
