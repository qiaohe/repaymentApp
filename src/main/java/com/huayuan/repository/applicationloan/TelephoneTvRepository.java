package com.huayuan.repository.applicationloan;

import com.huayuan.domain.loanapplication.TelephoneTV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dell on 14-5-10.
 */
public interface TelephoneTvRepository extends JpaRepository<TelephoneTV, Long> {
    public List<TelephoneTV> findByApplication_ApplicationNo(String appNo);
}
