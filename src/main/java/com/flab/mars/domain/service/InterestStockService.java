package com.flab.mars.domain.service;

import com.flab.mars.client.dto.KisStockResponseDto;
import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.InterestStockRepository;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.db.repository.StockInfoRepository;
import com.flab.mars.domain.StockCodeValidator;
import com.flab.mars.domain.vo.TokenInfoVO;
import com.flab.mars.domain.vo.response.InterestStockVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestStockService {

    private final InterestStockRepository interestStockRepository;
    private final StockInfoRepository stockInfoRepository;
    private final PriceDataRepository priceDataRepository;
    private final StockCodeValidator stockCodeValidator;

    @Transactional
    public Long registerInterestStock(Long userId, String stockCode, TokenInfoVO token) {

        // DB 에 해당 주식 코드 정보가 없는 경우
        StockInfoEntity stockInfoEntity = stockInfoRepository.findByStockCode(stockCode)
                .orElseGet(() -> {
                    String stockName = stockCodeValidator.validate(stockCode, token);
                    // 해당 주식이 저장되어 있지 않은 경우 insert
                    return stockInfoRepository.save(new StockInfoEntity(stockCode, stockName));
        });

        // 중복 관심 종목 등록 방지
        Optional<InterestStockEntity>  existingInterestStock  = interestStockRepository.findByMemberIdAndStockInfo(userId, stockInfoEntity);
        if (existingInterestStock.isPresent()) {
            return existingInterestStock.get().getId(); // 중복된 관심 종목 엔티티의 아이디를 리턴
        }
        InterestStockEntity interestStockEntity = InterestStockEntity.builder()
                .memberId(userId)
                .stockInfo(stockInfoEntity)
                .build();

        interestStockRepository.save(interestStockEntity);

        return interestStockEntity.getId();
    }


    public List<InterestStockVO> getInterestStocks(Long memberId) {

        List<InterestStockEntity> interestStockEntities = interestStockRepository.findByMemberId(memberId);
        LocalDate date = LocalDate.now();
        LocalDateTime dateTimeAfter = date.atStartOfDay();
        List<InterestStockVO> interestStockVOs = new ArrayList<>();
        for (InterestStockEntity interestStockEntity : interestStockEntities) {

            StockInfoEntity stockInfo = interestStockEntity.getStockInfo();
            // 오늘 날짜 이후의 가장 최근 가격 데이터 가져오기
            PriceDataEntity latestPriceData = priceDataRepository.findTopByStockInfoEntityIdAndDateTimeAfterOrderByDateTimeDesc(
                    stockInfo.getId(), dateTimeAfter
            ).orElse(null);

            InterestStockVO interestStockVO = InterestStockVO.builder()
                    .stockCode(stockInfo.getStockCode())
                    .stockName(stockInfo.getStockName())
                    .currentPrice(latestPriceData != null ? latestPriceData.getCurrentPrice() : "0")
                    .prdyCtrt(latestPriceData != null ? latestPriceData.getPrdyCtrt() : "0")
                    .prdyVrssSign(latestPriceData != null ? latestPriceData.getPrdyVrssSign() : "")
                    .prdyVrss(latestPriceData != null ? latestPriceData.getPrdyVrss() : "")
                    .prdyCtrt(latestPriceData != null ? latestPriceData.getPrdyCtrt() : "0")
                    .build();

            interestStockVOs.add(interestStockVO);
        }
        return interestStockVOs;
    }

}
