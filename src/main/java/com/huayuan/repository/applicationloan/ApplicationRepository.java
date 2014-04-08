package com.huayuan.repository.applicationloan;

import com.huayuan.domain.loanapplication.Application;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Johnson on 4/7/14.
 */
public interface ApplicationRepository extends CrudRepository<Application, String> {
}
