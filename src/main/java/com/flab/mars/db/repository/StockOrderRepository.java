package com.flab.mars.db.repository;

import com.flab.mars.db.entity.StockOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockOrderRepository extends JpaRepository<StockOrderEntity, Long> {
    StockOrderEntity findByIdempotencyKey(String idempotencyKey);
}
