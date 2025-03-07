package com.flab.mars.db.repository;

import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceDataRepository extends JpaRepository<PriceDataEntity, Long> {

    Optional<PriceDataEntity> findByStockInfoEntityAndDateTime(StockInfoEntity stockInfoEntity, LocalDateTime dateTime);

    Optional<PriceDataEntity> findTopByStockInfoEntityIdAndDateTimeAfterOrderByDateTimeDesc(Long stockInfoEntityId, LocalDateTime dateTimeAfter);

    // 변동률이 5 이상인 PriceData 가져오기
    @Query("select distinct p.stockInfoEntity.id from PriceDataEntity p where p.priceChangeRate >= :priceChangeRate")
    Page<Long> findDistinctStockInfoIdsByPriceChangeRateGreaterThanEqual(Double priceChangeRate, Pageable pageable);


}
