package com.flab.mars.db.repository;

import com.flab.mars.db.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockInfoRepository extends JpaRepository<StockInfoEntity, Long> {
}
