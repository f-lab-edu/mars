package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISConfig;
import com.flab.mars.client.dto.KISFluctuationResponseDto;
import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.PriceDataType;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.domain.vo.MemberInfoVO;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.domain.vo.response.AccessTokenResponseVO;
import com.flab.mars.domain.vo.response.PriceDataResponseVO;
import com.flab.mars.domain.vo.response.StockFluctuationResponseVO;
import com.flab.mars.exception.AuthException;
import com.flab.mars.exception.BadWebClientRequestException;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final KISClient kisClient;

    private final KISConfig kisConfig;

    private final PriceDataRepository priceDataRepository;


    public AccessTokenResponseVO getAccessToken(TokenInfo tokenInfo) {

        try {
            String accessToken = kisClient.getAccessToken(tokenInfo.getAppKey(), tokenInfo.getAppSecret(), kisConfig.getGrantType());
            tokenInfo.setAccessToken(accessToken);
            return new AccessTokenResponseVO(true, "AccessToken 발급 성공", HttpStatus.OK, accessToken);
        } catch (BadWebClientRequestException e) {
            log.error("AccessToken 발급 실패 : {}", e.getErrorDescription());
            return new AccessTokenResponseVO(false, e.getErrorDescription(), e.getStatusCode(), null);
        }
    }

    public PriceDataResponseVO getStockPrice(String stockCode, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        if(sessionLoginUser == null || sessionLoginUser.getAccessToken() == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }
        KisStockPriceDto stockPrice = kisClient.getStockPrice(sessionLoginUser.getAccessToken(), sessionLoginUser.getAppKey(), sessionLoginUser.getAppSecret(), stockCode);

        return insertCurrentStockPrice(stockPrice, stockCode);
    }

    @Transactional
    public PriceDataResponseVO insertCurrentStockPrice(KisStockPriceDto stockPrice, String stockCode) {

        PriceDataEntity priceDataEntity = PriceDataEntity.builder()
                .dataType(PriceDataType.MINUTE)             // 분
                .currentPrice(stockPrice.getOutput().getStckPrpr())       // 현재가
                .openPrice(stockPrice.getOutput().getStckOprc())       // 주식 시가
                .highPrice(stockPrice.getOutput().getStckHgpr())       // 주식 최고가
                .lowPrice(stockPrice.getOutput().getStckLwpr())        // 주식 최저가
                .acmlVol(stockPrice.getOutput().getAcmlVol())          // 누적 거래량
                .acmlTrPbmn(stockPrice.getOutput().getAcmlTrPbmn())    // 누적 거래 대금
                .build();


        PriceDataEntity savedPriceDataEntity = priceDataRepository.save(priceDataEntity);

        return PriceDataResponseVO.toVO(savedPriceDataEntity);
    }

    public StockFluctuationResponseVO getFluctuationRanking(String url, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        if(sessionLoginUser == null || sessionLoginUser.getAccessToken() == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

        KISFluctuationResponseDto fluctuationRanking = kisClient.getFluctuationRanking(sessionLoginUser.getAccessToken(), sessionLoginUser.getAppKey(), sessionLoginUser.getAppSecret(), url);
        return StockFluctuationResponseVO.dtoToVO(fluctuationRanking);

    }
}
