package com.flab.mars.domain;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.dto.KisStockResponseDto;
import com.flab.mars.domain.vo.TokenInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockCodeValidator {

    private final KISClient kisClient;


    public KisStockResponseDto validate(String stockCode, TokenInfoVO token)  {

        // 주식 코드 유효성 검증
        KisStockResponseDto verifyStock = kisClient.getStockName(token.getAccessToken(), token.getAppKey(), token.getAppSecret(), stockCode);
        // 정상 응답이 아닌 경우
        if (!verifyStock.isSuccessfulResponse()) {
            throw new IllegalArgumentException("유효하지 않은 주식 코드입니다 : " + stockCode + " ,  응답 메시지:  " + verifyStock.getMsg1());
        }

        if (verifyStock.getStockInfo() == null) {
            throw new IllegalStateException(" 해당 주식 코드에 관련된 정보를 조회할 수 없습니다 : " + stockCode);
        }
        return verifyStock;
    }
}
