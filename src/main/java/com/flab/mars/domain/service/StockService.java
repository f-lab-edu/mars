package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.dto.KISFluctuationResponseDto;
import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.db.repository.StockInfoRepository;
import com.flab.mars.domain.vo.MemberInfoVO;
import com.flab.mars.domain.vo.response.AccessTokenVO;
import com.flab.mars.domain.vo.response.PriceDataVO;
import com.flab.mars.domain.vo.response.StockFluctuationVO;
import com.flab.mars.exception.BadWebClientRequestException;
import jakarta.servlet.http.HttpSession;
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


    @Transactional
    public PriceDataVO getStockPrice(String stockCode, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        // 등록된 주식만 조회가능
        StockInfoEntity stockInfo = stockInfoRepository.findByStockCode(stockCode).orElseThrow(() -> new IllegalArgumentException("조회할 수 없는 주식 코드입니다 : " + stockCode));

        // 현재 시간을 분 단위로 얻기
        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0); // 초 단위 제거

        Optional<PriceDataEntity> priceDataEntity = priceDataRepository.findByStockInfoEntityAndDateTime(stockInfo, currentTime);

        if(priceDataEntity.isPresent()) {
            // DB 에 값이 있는 경우
            return PriceDataVO.toVO(priceDataEntity.get());
        }

        KisStockPriceDto stockPrice = kisClient.getStockPrice(sessionLoginUser.getAccessToken(), sessionLoginUser.getAppKey(), sessionLoginUser.getAppSecret(), stockCode);

        return saveCurrentStockPrice(stockPrice, stockInfo, currentTime);
    }


    /**
     * 현재 주식 가격을 DB에 저장합니다. (분 단위로 저장됨)
     * @param stockPrice
     * @param stockInfoEntity
     * @param currentTime
     * @return
     */
    private PriceDataVO saveCurrentStockPrice(KisStockPriceDto stockPrice, StockInfoEntity stockInfoEntity, LocalDateTime currentTime) {

        KisStockPriceDto.Output output = stockPrice.getOutput();
        PriceDataEntity priceDataEntity = PriceDataEntity.builder()
                .stockInfoEntity(stockInfoEntity)
                .currentPrice(output.getStckPrpr())       // 현재가
                .openPrice(output.getStckOprc())       // 주식 시가
                .highPrice(output.getStckHgpr())       // 주식 최고가
                .lowPrice(output.getStckLwpr())        // 주식 최저가
                .acmlVol(output.getAcmlVol())          // 누적 거래량
                .acmlTrPbmn(output.getAcmlTrPbmn())    // 누적 거래 대금
                .prdyVrss(output.getPrdyVrss()) // 전일 대비 가격변화
                .prdyVrssSign(mapPrdyVrssSignToSymbol(output.getPrdyVrssSign())) // 전일 대비 부호
                .prdyCtrt(output.getPrdyCtrt()) // 전일 대비율
                .dateTime(currentTime)
                .build();
        PriceDataEntity savedPriceDataEntity = priceDataRepository.save(priceDataEntity);

        return PriceDataVO.toVO(savedPriceDataEntity);
    }

    public String mapPrdyVrssSignToSymbol(String prdyVrssSign) {
        return switch (prdyVrssSign) {
            case "1", "2" -> // 상승
                    "+";
            case "4", "5" ->  // 하락
                    "-";
            default -> ""; // 기호 없음
        };
    }

    public StockFluctuationVO getFluctuationRanking(String url, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        KISFluctuationResponseDto fluctuationRanking = kisClient.getFluctuationRanking(sessionLoginUser.getAccessToken(), sessionLoginUser.getAppKey(), sessionLoginUser.getAppSecret(), url);
        return StockFluctuationVO.dtoToVO(fluctuationRanking);

    }
}