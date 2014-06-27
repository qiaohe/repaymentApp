package com.huayuan.repository;

import com.huayuan.domain.wechat.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Johnson on 6/27/14.
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
