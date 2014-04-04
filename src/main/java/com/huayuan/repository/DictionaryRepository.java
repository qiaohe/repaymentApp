package com.huayuan.repository;

import com.huayuan.domain.dictionary.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-4-4.
 */
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
}
