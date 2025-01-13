package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISConfig;
import com.flab.mars.client.dto.KISFluctuationResponseDto;
import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.db.repository.StockInfoRepository;
import com.flab.mars.domain.vo.MemberInfoVO;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.domain.vo.response.AccessTokenVO;
import com.flab.mars.domain.vo.response.PriceDataVO;
import com.flab.mars.domain.vo.response.StockFluctuationVO;
import com.flab.mars.exception.AuthException;
import com.flab.mars.exception.BadWebClientRequestException;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StockService {

    private final KISClient kisClient;

    private final KISConfig kisConfig;

    private final PriceDataRepository priceDataRepository;

    private final StockInfoRepository stockInfoRepository;


    public AccessTokenVO getAccessToken(TokenInfo tokenInfo) {

        try {
            String accessToken = kisClient.getAccessToken(tokenInfo.getAppKey(), tokenInfo.getAppSecret(), kisConfig.getGrantType());
            tokenInfo.setAccessToken(accessToken);
            return new AccessTokenVO(true, "AccessToken 발급 성공", HttpStatus.OK, accessToken);
        } catch (BadWebClientRequestException e) {
            log.error("AccessToken 발급 실패 : {}", e.getErrorDescription());
            return new AccessTokenVO(false, e.getErrorDescription(), e.getStatusCode(), null);
        }
    }

    @Transactional
    public PriceDataVO getStockPrice(String stockCode, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        if(sessionLoginUser == null || sessionLoginUser.getAccessToken() == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

        // 등록된 주식만 조회가능
        if(stockInfoRepository.existsByStockCode(stockCode)) {
            throw new IllegalArgumentException("조회할 수 없는 주식 코드입니다.");
        }
        // 현재 시간을 분 단위로 얻기
        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0); // 초 단위 제거

        Optional<PriceDataEntity> priceDataEntity = priceDataRepository.findByStockCodeAndDateTime(stockCode, currentTime);

        if(priceDataEntity.isPresent()) {
            // DB 에 값이 있는 경우
            return PriceDataVO.toVO(priceDataEntity.get());
        }

        KisStockPriceDto stockPrice = kisClient.getStockPrice(sessionLoginUser.getAccessToken(), sessionLoginUser.getAppKey(), sessionLoginUser.getAppSecret(), stockCode);

        return insertCurrentStockPrice(stockPrice, stockCode, currentTime);
    }


    public PriceDataVO insertCurrentStockPrice(KisStockPriceDto stockPrice, String stockCode, LocalDateTime currentTime) {

        StockInfoEntity stockInfoEntity = stockInfoRepository.findByStockCode(stockCode)
                .orElseThrow(() -> new IllegalArgumentException("StockCode not found: " + stockCode));

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
        switch (prdyVrssSign) {
            case "1": // 상한
            case "2": // 상승
                return "+";
            case "4": // 하한
            case "5": // 하락
                return "-";
            case "3": // 보합
            default:
                return ""; // 기호 없음
        }
    }

    public StockFluctuationVO getFluctuationRanking(String url, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        if(sessionLoginUser == null || sessionLoginUser.getAccessToken() == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

        KISFluctuationResponseDto fluctuationRanking = kisClient.getFluctuationRanking(sessionLoginUser.getAccessToken(), sessionLoginUser.getAppKey(), sessionLoginUser.getAppSecret(), url);
        return StockFluctuationVO.dtoToVO(fluctuationRanking);

    }
}
