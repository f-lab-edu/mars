package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISConfig;
import com.flab.mars.domain.vo.StockPrice;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.exception.AuthException;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StockService {

    private final KISClient kisClient;

    private final KISConfig kisConfig;


    public void getAccessToken(TokenInfo tokenInfo, HttpSession session) {
        String accessToken = kisClient.getAccessToken(tokenInfo.getAppKey(), tokenInfo.getAppSecret(), kisConfig.getGrantType());
        tokenInfo.setAccessToken(accessToken);
        SessionUtil.setSessionAccessToKenValue(session, tokenInfo);
    }

    public StockPrice getStockPrice(String stockCode, HttpSession session) {
        TokenInfo tokenInfo = SessionUtil.getSessionAccessToKenValue(session);

        if(tokenInfo == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

        return kisClient.getStockPrice(tokenInfo.getAccessToken(), tokenInfo.getAppKey(), tokenInfo.getAppSecret(), stockCode);
    }
}
