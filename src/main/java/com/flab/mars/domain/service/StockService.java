package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.dto.KISFluctuationResponseDto;
import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.db.repository.StockInfoRepository;
import com.flab.mars.domain.vo.TokenInfoVO;
import com.flab.mars.domain.vo.response.PriceDataVO;
import com.flab.mars.domain.vo.response.StockFluctuationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final KISClient kisClient;

    private final PriceDataRepository priceDataRepository;

    private final StockInfoRepository stockInfoRepository;

    private final StockPriceService stockPriceService;


    @Transactional
    public PriceDataVO getStockPrice(String stockCode, TokenInfoVO tokenInfo) {
        // 등록된 주식만 조회가능
        StockInfoEntity stockInfo = stockInfoRepository.findByStockCode(stockCode).orElseThrow(() -> new IllegalArgumentException("조회할 수 없는 주식 코드입니다 : " + stockCode));

        // 현재 시간을 분 단위로 얻기
        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0); // 초 단위 제거

        Optional<PriceDataEntity> priceDataEntity = priceDataRepository.findByStockInfoEntityAndDateTime(stockInfo, currentTime);

        if(priceDataEntity.isPresent()) {
            // DB 에 값이 있는 경우
            return PriceDataVO.toVO(priceDataEntity.get());
        }

        KisStockPriceDto stockPrice = kisClient.getStockPrice(tokenInfo.getAccessToken(), tokenInfo.getAppKey(), tokenInfo.getAppSecret(), stockCode);

        return stockPriceService.saveCurrentStockPrice(stockPrice, stockInfo, currentTime);
    }

    public StockFluctuationVO getFluctuationRanking(String url, TokenInfoVO tokenInfo) {
        KISFluctuationResponseDto fluctuationRanking = kisClient.getFluctuationRanking(tokenInfo.getAccessToken(), tokenInfo.getAppKey(), tokenInfo.getAppSecret(), url);
        return StockFluctuationVO.dtoToVO(fluctuationRanking);

    }
}