package com.flab.mars.db.repository;

import com.flab.mars.db.entity.StockPriceChartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceChartRepository extends JpaRepository<StockPriceChartEntity, Long> {
}
