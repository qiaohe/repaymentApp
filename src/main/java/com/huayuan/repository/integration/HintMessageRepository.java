package com.huayuan.repository.integration;

import com.huayuan.integration.wechat.domain.HintMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Johnson on 5/11/14.
 */
public interface HintMessageRepository extends JpaRepository<HintMessage, Long> {
}
