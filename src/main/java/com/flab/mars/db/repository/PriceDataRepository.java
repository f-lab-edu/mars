package com.flab.mars.db.repository;

import com.flab.mars.db.entity.PriceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceDataRepository extends JpaRepository<PriceDataEntity, Long> {

    @Query("SELECT p FROM PriceDataEntity p JOIN p.stockInfoEntity s WHERE s.stockCode = :stockCode AND p.dateTime = :dateTime")
    Optional<PriceDataEntity> findByStockCodeAndDateTime(@Param("stockCode") String stockCode, @Param("dateTime") LocalDateTime dateTime);
}
