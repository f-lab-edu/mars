package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISConfig;
import com.flab.mars.client.KisPriceResponseVO;
import com.flab.mars.db.entity.*;
import com.flab.mars.db.repository.StockPriceChartRepository;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.domain.vo.response.StockFluctuationResponseVO;
import com.flab.mars.domain.vo.response.StockPriceResponseVO;
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

    private final StockPriceChartRepository stockPriceChartRepository;


    public void getAccessToken(TokenInfo tokenInfo, HttpSession session) {
        String accessToken = kisClient.getAccessToken(tokenInfo.getAppKey(), tokenInfo.getAppSecret(), kisConfig.getGrantType());
        tokenInfo.setAccessToken(accessToken);
        SessionUtil.setSessionAccessToKenValue(session, tokenInfo);
    }

    // 실시간 주식 현재가 가져오기
    public StockPriceResponseVO getStockPrice(String stockCode, HttpSession session) {
        TokenInfo tokenInfo = SessionUtil.getSessionAccessToKenValue(session);


        // TODO userInterceptor 로 빼기
        if(tokenInfo == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }
        KisPriceResponseVO stockPrice = kisClient.getStockPrice(tokenInfo.getAccessToken(), tokenInfo.getAppKey(), tokenInfo.getAppSecret(), stockCode);

        return insertCurrentStockPrice(stockPrice, stockCode);
    }

    @Transactional
    public StockPriceResponseVO insertCurrentStockPrice(KisPriceResponseVO stockPrice, String stockCode) {
        // Output 객체 생성
        Output output = Output.builder()
                .stckPrpr(stockPrice.getOutput().getStckPrpr())        // 주식 현재가
                .prdyVrss(stockPrice.getOutput().getPrdyVrss())        // 전일 대비
                .prdyVrssSign(stockPrice.getOutput().getPrdyVrssSign()) // 전일 대비 부호
                .prdyCtrt(stockPrice.getOutput().getPrdyCtrt())        // 전일 대비율
                .acmlTrPbmn(stockPrice.getOutput().getAcmlTrPbmn())    // 누적 거래 대금
                .acmlVol(stockPrice.getOutput().getAcmlVol())          // 누적 거래량
                .stckOprc(stockPrice.getOutput().getStckOprc())        // 주식 시가
                .stckHgpr(stockPrice.getOutput().getStckHgpr())        // 주식 최고가
                .stckLwpr(stockPrice.getOutput().getStckLwpr())        // 주식 최저가
                .stckMxpr(stockPrice.getOutput().getStckMxpr())        // 주식 상한가
                .stckLlam(stockPrice.getOutput().getStckLlam())        // 주식 하한가
                .stckSdpr(stockPrice.getOutput().getStckSdpr())        // 주식 기준가
                .wghnAvrgStckPrc(stockPrice.getOutput().getWghnAvrgStckPrc()) // 가중 평균 주식 가격
                .htsFrgnEhrt(stockPrice.getOutput().getHtsFrgnEhrt())  // HTS 외국인 소진율
                .frgnNtbyQty(stockPrice.getOutput().getFrgnNtbyQty())  // 외국인 순매수 수량
                .pgtrNtbyQty(stockPrice.getOutput().getPgtrNtbyQty())  // 프로그램매매 순매수 수량
                .stckDryyHgpr(stockPrice.getOutput().getStckDryyHgpr()) // 주식 연중 최고가
                .stckDryyLwpr(stockPrice.getOutput().getStckDryyLwpr()) // 주식 연중 최저가
                .w52Hgpr(stockPrice.getOutput().getW52Hgpr())          // 52주 최고가
                .w52Lwpr(stockPrice.getOutput().getW52Lwpr())          // 52주 최저가
                .wholLoanRmndRate(stockPrice.getOutput().getWholLoanRmndRate()) // 전체 융자 잔고 비율
                .sstsYn(stockPrice.getOutput().getSstsYn())            // 공매도 가능 여부
                .stckShrnIscd(stockPrice.getOutput().getStckShrnIscd()) // 주식 단축 종목코드
                .invtCafulYn(stockPrice.getOutput().getInvtCafulYn())  // 투자유의 여부
                .mrktWarnClsCode(stockPrice.getOutput().getMrktWarnClsCode()) // 시장 경고 코드
                .shortOverYn(stockPrice.getOutput().getShortOverYn())  // 단기과열 여부
                .sltrYn(stockPrice.getOutput().getSltrYn())            // 정리매매 여부
                .build();


        StockPriceChartEntity stockPriceChartEntity = StockPriceChartEntity.builder()
                .stockCode(stockCode)             // 주식 코드
                .rtCd(stockPrice.getRtCd())       // 응답 코드
                .msg1(stockPrice.getMsg1())       // 응답 메시지
                .output(output)                   // 주식 데이터
                .build();


        StockPriceChartEntity savedEntity = stockPriceChartRepository.save(stockPriceChartEntity);

        return StockPriceResponseVO.toVO(savedEntity);
    }

    public StockFluctuationResponseVO getFluctuationRanking(String url, HttpSession session) {
        TokenInfo tokenInfo = SessionUtil.getSessionAccessToKenValue(session);

        if(tokenInfo == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

        return kisClient.getFluctuationRanking(tokenInfo.getAccessToken(), tokenInfo.getAppKey(), tokenInfo.getAppSecret(), url);

    }
}
