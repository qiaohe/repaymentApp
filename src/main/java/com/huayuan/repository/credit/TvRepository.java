package com.huayuan.repository.credit;

import com.huayuan.domain.loanapplication.TelephoneVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dell on 14-5-1.
 */
public interface TvRepository extends JpaRepository<TelephoneVerification, Long>, TvRepositoryCustom {
    List<TelephoneVerification> findByTypeAndDecision(Integer type, String decision);
}
