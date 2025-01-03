package com.flab.mars.db.repository;

import com.flab.mars.db.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockInfoRepository extends JpaRepository<StockInfoEntity, Long> {
    boolean existsByStockCode(String stockCode);
    Optional<StockInfoEntity> findByStockCode(String stockCode);
}
