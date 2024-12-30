package com.flab.mars.db.repository;

import com.flab.mars.db.entity.PriceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceDataRepository extends JpaRepository<PriceDataEntity, Long> {
}
