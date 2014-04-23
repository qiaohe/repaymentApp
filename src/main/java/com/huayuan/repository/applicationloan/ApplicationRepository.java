package com.huayuan.repository.applicationloan;

import com.huayuan.domain.loanapplication.Application;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Johnson on 4/7/14.
 */
public interface ApplicationRepository extends CrudRepository<Application, String>, ApplicationRepositoryCustom {
    public List<Application> findByMemberIdOrderByApplicationNoDesc(Long memberId);
}
