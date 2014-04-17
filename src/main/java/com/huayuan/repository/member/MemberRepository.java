package com.huayuan.repository.member;

import com.huayuan.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dell on 14-3-19.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByWcNo(String weChatNo);
}