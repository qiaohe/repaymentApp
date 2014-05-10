package com.huayuan.repository.credit;

import com.huayuan.domain.credit.Pboc;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dell on 14-4-28.
 */
public interface PbocRepository extends CrudRepository<Pboc, Long>, PbocRepositoryCustom {
    public Pboc findByCertNo(final String certNo);
}
