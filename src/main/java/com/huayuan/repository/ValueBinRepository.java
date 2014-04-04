package com.huayuan.repository;

import com.huayuan.domain.dictionary.ValueBin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-4-4.
 */
public interface ValueBinRepository extends JpaRepository<ValueBin, Long> {
    public ValueBin findByBinNo(String binNo);
}
