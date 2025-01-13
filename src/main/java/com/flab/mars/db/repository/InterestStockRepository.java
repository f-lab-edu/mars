package com.flab.mars.db.repository;

import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.domain.vo.response.InterestStockResponseVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InterestStockRepository extends JpaRepository<InterestStockEntity, Long> {
    Optional<InterestStockEntity> findByMemberAndStockInfo(MemberEntity member, StockInfoEntity stockInfo);

    @Query("""
    SELECT new com.flab.mars.domain.vo.response.InterestStockResponseVO(
        s.stockCode,
        s.stockName,
        pd.currentPrice,
        pd.prdyVrss,
        pd.prdyVrssSign,
        pd.prdyCtrt
    )
    FROM InterestStockEntity is
    LEFT JOIN StockInfoEntity s
        ON is.stockInfo.id = s.id
    LEFT JOIN PriceDataEntity pd
        ON is.stockInfo.id = pd.stockInfoEntity.id
        AND pd.dateTime =  (
            SELECT MAX(pd2.dateTime)
            FROM PriceDataEntity pd2
            WHERE pd2.stockInfoEntity.id = pd.stockInfoEntity.id
            AND pd2.dateTime > current date
        )
    WHERE is.member.id = :memberId
""")
    List<InterestStockResponseVO> findInterestStocksWithLatestPrice(@Param("memberId") Long memberId);

}
