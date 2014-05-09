package com.huayuan.repository.applicationloan;

import com.huayuan.domain.loanapplication.Application;

import java.util.List;

/**
 * Created by dell on 14-4-13.
 */
public interface ApplicationRepositoryCustom {
    public void execute(Application application);

    public List<Object> findAllApplications();

    public Object findApplicationBy(final String appNo);

}
