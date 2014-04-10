package com.huayuan.domain.dictionary;

import com.huayuan.repository.CreditLimitRangeRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by dell on 14-4-10.
 */
@Component(value = "creditLimitRanges")
public class CreditLimitRanges {
    @Inject
    private CreditLimitRangeRepository creditLimitRangeRepository;

    private  List<CreditLimitRange> ranges;

    public CreditLimitRanges(List<CreditLimitRange> ranges) {
        this.ranges = ranges;
    }

    public CreditLimitRanges() {
    }

    @PostConstruct
    public void init() {
        ranges = creditLimitRangeRepository.findAll();
    }

    public Double getScaleBy(Long number) {
        for (CreditLimitRange range : ranges) {
            if (range.contain(number)) return range.getScale();
        }
        return null;
    }
}
