package com.huayuan.repository.account;

import com.huayuan.domain.accounting.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dell on 14-4-21.
 */
public interface PricingRepository extends JpaRepository<Pricing, Long> {
    public Pricing findByRatingAndTerm(String rating, int term);
}
