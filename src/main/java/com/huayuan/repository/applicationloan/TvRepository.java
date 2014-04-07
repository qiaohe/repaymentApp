package com.huayuan.repository.applicationloan;

import com.huayuan.domain.loanapplication.TelephoneVerification;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Johnson on 4/7/14.
 */
public interface TvRepository extends CrudRepository<TelephoneVerification, Long> {
}
