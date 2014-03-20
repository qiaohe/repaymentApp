package com.huayuan.service;

import com.huayuan.domain.Member;
import com.huayuan.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell on 14-3-19.
 */
@Service(value = "memberService")
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void register(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void update(Member member) {

    }

    @Override
    public void remove(Member member) {

    }

    @Override
    public Iterable<Member> getMembers() {
        return memberRepository.findAll();
    }


}
