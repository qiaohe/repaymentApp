package com.huayuan.repository.integration;

import com.huayuan.integration.wechat.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-4-22.
 */
public interface MenuRepository  extends JpaRepository<Menu, Long> {
}
