package com.huayuan.repository.credit;

import com.huayuan.domain.credit.Pboc;
import com.huayuan.domain.credit.PbocSummary;

import java.util.List;

/**
 * Created by dell on 14-4-28.
 */
public interface PbocRepositoryCustom {
   public List<PbocSummary> findByStatus();

    public List<PbocSummary> search(String query);

    public String getIdCardImage(Long id);
}
