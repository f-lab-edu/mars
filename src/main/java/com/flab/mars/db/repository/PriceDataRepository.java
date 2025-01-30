package com.flab.mars.db.repository;

import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceDataRepository extends JpaRepository<PriceDataEntity, Long> {
    Optional<PriceDataEntity> findByStockInfoEntityAndDateTime(StockInfoEntity stockInfoEntity, LocalDateTime dateTime);
}
