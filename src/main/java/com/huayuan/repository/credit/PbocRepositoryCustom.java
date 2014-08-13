package com.huayuan.repository.credit;

import com.huayuan.domain.credit.PbocSummary;
import com.huayuan.web.dto.PbocOutDto;

import java.util.List;

/**
 * Created by dell on 14-4-28.
 */
public interface PbocRepositoryCustom {
    public List<PbocSummary> findByStatus();

    public List<PbocSummary> search(Integer curPage,String query);

    public String getIdCardImage(Long id);

    public List<PbocOutDto> searchPbocOutList(String query);
}
