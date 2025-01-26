package com.flab.mars.db.repository;

import com.flab.mars.db.entity.StockInfoEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockInfoRepository extends JpaRepository<StockInfoEntity, Long> {
    Optional<StockInfoEntity> findByStockCode(String stockCode);

    @Nonnull
    List<StockInfoEntity> findAll();
}
