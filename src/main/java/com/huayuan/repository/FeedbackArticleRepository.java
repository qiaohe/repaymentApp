package com.huayuan.repository;

import com.huayuan.domain.wechat.FeedbackArticle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-6-5.
 */
public interface FeedbackArticleRepository extends JpaRepository<FeedbackArticle, Long> {
}
