package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISConfig;
import com.flab.mars.client.KisPriceResponseVO;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.PriceDataType;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.domain.vo.response.PriceDataResponseVO;
import com.flab.mars.domain.vo.response.StockFluctuationResponseVO;
import com.flab.mars.exception.AuthException;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final KISClient kisClient;

    private final KISConfig kisConfig;

    private final PriceDataRepository priceDataRepository;


    public void getAccessToken(TokenInfo tokenInfo, HttpSession session) {
        String accessToken = kisClient.getAccessToken(tokenInfo.getAppKey(), tokenInfo.getAppSecret(), kisConfig.getGrantType());
        tokenInfo.setAccessToken(accessToken);
        SessionUtil.setSessionAccessToKenValue(session, tokenInfo);
    }

    // 실시간 주식 현재가 가져오기
    public PriceDataResponseVO getStockPrice(String stockCode, HttpSession session) {
        TokenInfo tokenInfo = SessionUtil.getSessionAccessToKenValue(session);


        // TODO userInterceptor 로 빼기
        if(tokenInfo == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }
        KisPriceResponseVO stockPrice = kisClient.getStockPrice(tokenInfo.getAccessToken(), tokenInfo.getAppKey(), tokenInfo.getAppSecret(), stockCode);

        return insertCurrentStockPrice(stockPrice, stockCode);
    }

    @Transactional
    public PriceDataResponseVO insertCurrentStockPrice(KisPriceResponseVO stockPrice, String stockCode) {

        PriceDataEntity priceDataEntity = PriceDataEntity.builder()
                .dataType(PriceDataType.REALTIME)             // 실시간
                .currentPrice(stockPrice.getOutput().getStckPrpr())       // 현재가
                .openPrice(stockPrice.getOutput().getStckOprc())       // 주식 시가
                .highPrice(stockPrice.getOutput().getStckHgpr())       // 주식 최고가
                .lowPrice(stockPrice.getOutput().getStckLwpr())        // 주식 최저가
                .acmlVol(stockPrice.getOutput().getAcmlVol())          // 누적 거래량
                .acmlTrPbmn(stockPrice.getOutput().getAcmlTrPbmn())    // 누적 거래 대금
                .build();

        PriceDataEntity savedpriceDataEntity = priceDataRepository.save(priceDataEntity);

        return PriceDataResponseVO.toVO(savedpriceDataEntity);
    }

    public StockFluctuationResponseVO getFluctuationRanking(String url, HttpSession session) {
        TokenInfo tokenInfo = SessionUtil.getSessionAccessToKenValue(session);

        if(tokenInfo == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

        return kisClient.getFluctuationRanking(tokenInfo.getAccessToken(), tokenInfo.getAppKey(), tokenInfo.getAppSecret(), url);

    }
}
