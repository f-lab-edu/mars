package com.flab.mars.domain.service;

import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.InterestStockRepository;
import com.flab.mars.db.repository.MemberRepository;
import com.flab.mars.db.repository.StockInfoRepository;
import com.flab.mars.domain.vo.response.InterestStockVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestStockService {

    private final InterestStockRepository interestStockRepository;
    private final MemberRepository memberRepository;
    private final StockInfoRepository stockInfoRepository;


    @Transactional
    public long registerInterestStock(Long userId, String stockCode, String stockName) {
        MemberEntity memberEntity = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 해당 주식이 저장되어 있지 않은 경우 insert
        StockInfoEntity stockInfoEntity = stockInfoRepository.findByStockCode(stockCode)
                .orElseGet(() -> {
                    StockInfoEntity stockInfo = new StockInfoEntity(stockCode, stockName);
                    return stockInfoRepository.save(stockInfo);
                });

        // 중복 관심 종목 등록 방지
        Optional<InterestStockEntity>  existingInterestStock  = interestStockRepository.findByMemberAndStockInfo(memberEntity, stockInfoEntity);
        if (existingInterestStock.isPresent()) {
            return existingInterestStock.get().getId(); // 중복된 관심 종목 엔티티의 아이디를 리턴
        }
        InterestStockEntity interestStockEntity = InterestStockEntity.builder()
                .member(memberEntity)
                .stockInfo(stockInfoEntity)
                .build();

        interestStockRepository.save(interestStockEntity);

        return interestStockEntity.getId();
    }


    public List<InterestStockVO> getInterestStocks(Long memberId) {
        return interestStockRepository.findInterestStocksWithLatestPrice(memberId);
    }

}
