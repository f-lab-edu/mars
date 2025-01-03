package com.flab.mars.domain.service;

import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.InterestStockRepository;
import com.flab.mars.db.repository.MemberRepository;
import com.flab.mars.db.repository.StockInfoRepository;
import com.flab.mars.domain.vo.response.InterestStockResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestStockService {

    private final InterestStockRepository interestStockRepository;
    private final MemberRepository memberRepository;
    private final StockInfoRepository stockInfoRepository;


    @Transactional
    public Long registerInterestStock(Long userId, String stockCode, String stockName) {
        MemberEntity memberEntity = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 해당 주식이 저장되어 있지 않은 경우 insert
        StockInfoEntity stockInfoEntity = stockInfoRepository.findByStockCode(stockCode)
                .orElseGet(() -> {
                    StockInfoEntity stockInfo = new StockInfoEntity(stockCode, stockName);
                    return stockInfoRepository.save(stockInfo);
                });

        // 중복 관심 종목 등록 방지
        boolean exists = interestStockRepository.existsByMemberAndStockInfo(memberEntity, stockInfoEntity);
        if (exists) {
            return -1L;
        }
        InterestStockEntity interestStockEntity = InterestStockEntity.builder()
                .member(memberEntity)
                .stockInfo(stockInfoEntity)
                .build();

        interestStockRepository.save(interestStockEntity);

        return interestStockEntity.getId();
    }


    public List<InterestStockResponseVO> getInterestStocks(Long memberId) {
        return interestStockRepository.findInterestStocksWithLatestPrice(memberId);
    }

}
