package com.huayuan.repository.integration;

import com.huayuan.domain.wechat.ReplyMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-8-28.
 */
public interface ReplyMessageRepository extends JpaRepository<ReplyMessage, Long> {
}
