package com.huayuan.repository;

import com.huayuan.domain.wechat.FeedbackArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dell on 14-6-5.
 */
public interface FeedbackArticleRepository extends JpaRepository<FeedbackArticle, Long> {
    public List<FeedbackArticle> findByMenuEvent(String menuEventId);
}
