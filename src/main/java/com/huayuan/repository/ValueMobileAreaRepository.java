package com.huayuan.repository;

import com.huayuan.domain.dictionary.ValueMobileArea;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-4-27.
 */
public interface ValueMobileAreaRepository extends JpaRepository<ValueMobileArea, Long> {
    public ValueMobileArea findBySevenPrefix(String sevenOfMobilePhone);
}
