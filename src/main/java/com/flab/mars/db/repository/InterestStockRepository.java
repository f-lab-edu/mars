package com.flab.mars.db.repository;

import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestStockRepository extends JpaRepository<InterestStockEntity, Long> {
    Optional<InterestStockEntity> findByMemberAndStockInfo(MemberEntity member, StockInfoEntity stockInfo);

    List<InterestStockEntity> findByMember(MemberEntity member);

}
