package com.flab.mars.db.repository;

import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestStockRepository extends JpaRepository<InterestStockEntity, Long> {
    boolean existsByMemberAndStockInfo(MemberEntity member, StockInfoEntity stockInfo);
}
