package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISConfig;
import com.flab.mars.domain.vo.MemberInfoVO;
import com.flab.mars.domain.vo.StockPrice;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.domain.vo.response.AccessTokenResponseVO;
import com.flab.mars.domain.vo.response.StockFluctuationResponseVO;
import com.flab.mars.exception.AuthException;
import com.flab.mars.exception.BadWebClientRequestException;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final KISClient kisClient;

    private final KISConfig kisConfig;


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

    public StockPrice getStockPrice(String stockCode, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        if(sessionLoginUser == null || sessionLoginUser.getAccessToken() == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

        return kisClient.getStockPrice(sessionLoginUser.getAccessToken(), sessionLoginUser.getAppKey(), sessionLoginUser.getAppSecret(), stockCode);
    }

    public StockFluctuationResponseVO getFluctuationRanking(String url, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        if(sessionLoginUser == null || sessionLoginUser.getAccessToken() == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

        return kisClient.getFluctuationRanking(sessionLoginUser.getAccessToken(), sessionLoginUser.getAppKey(), sessionLoginUser.getAppSecret(), url);

    }
}
