package com.huayuan.repository.member;

import com.huayuan.domain.member.Member;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dell on 14-3-19.
 */
public interface MemberRepository extends CrudRepository<Member, Long>, MemberRepositoryCustom {
    public Member findByWcNo(String weChatNo);

    public Member findByIdCard_IdNo(String idCardNo);

    public Member findByMobile(String mobilePhone);

    public Member findByEmail(String email);
}