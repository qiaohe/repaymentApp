package com.huayuan.repository;

import com.huayuan.domain.idgenerator.IdSequence;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Johnson on 4/4/14.
 */
public interface IdSequenceRepository extends JpaRepository<IdSequence, String> {
}
