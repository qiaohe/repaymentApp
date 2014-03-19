package com.huayuan.service;

import com.huayuan.domain.Member;

import java.util.List;

/**
 * Created by Johnson on 3/19/14.
 */
public interface MemberService {

    public void register(Member member);

    public void update(Member member);

    public void remove(Member member);

    public Iterable<Member> getMembers();

}
