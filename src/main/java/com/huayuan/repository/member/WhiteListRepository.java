package com.huayuan.repository.member;

import com.huayuan.domain.member.WhiteList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-6-4.
 */
public interface WhiteListRepository extends JpaRepository<WhiteList, Long> {
    public WhiteList findByMemberId(Long memberId);
}
