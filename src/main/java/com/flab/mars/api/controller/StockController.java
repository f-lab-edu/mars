package com.flab.mars.api.controller;

import com.flab.mars.api.dto.request.StockFluctuationRequestDto;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.api.dto.response.StockFluctuationDto;
import com.flab.mars.api.dto.response.StockPriceDto;
import com.flab.mars.domain.service.StockService;
import com.flab.mars.domain.vo.TokenInfoVO;
import com.flab.mars.domain.vo.response.PriceDataVO;
import com.flab.mars.domain.vo.response.StockFluctuationVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock")
@Slf4j
public class StockController {

    private final StockService stockService;

    @GetMapping("/quotations/inquire-price")
    public ResponseEntity<ResultAPIDto<StockPriceDto>> getStockPrice(@RequestParam(name = "stockCode") String stockCode,
                                                                     @RequestParam(name = "appKey") String appKey,
                                                                     @RequestParam(name = "appSecret") String appSecret,
                                                                     @RequestParam(name = "accessToken") String accessToken) {

        TokenInfoVO tokenInfo = new TokenInfoVO(appKey, appSecret, accessToken);
        PriceDataVO stockPrice = stockService.getStockPrice(stockCode, tokenInfo);
        StockPriceDto stockPriceDto = StockPriceDto.from(stockPrice);
        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "Success", stockPriceDto));
    }

    /**
     * 국내주식 등락률 순위
     * 최대 30건 확인 가능하며, 다음 조회가 불가합니다.
     * @param request
     * @return
     */
    @GetMapping("/domestic-stock/ranking/fluctuation")
    public ResponseEntity<ResultAPIDto<StockFluctuationDto>> getFluctuationRanking(@ModelAttribute @Valid StockFluctuationRequestDto request) {

        String queryParams  = request.buildQueryParams();
        TokenInfoVO tokenInfoVO = new TokenInfoVO(request.getAccessToken(), request.getAppKey(), request.getAppSecret());
        StockFluctuationVO response = stockService.getFluctuationRanking(queryParams, tokenInfoVO);
        StockFluctuationDto dto = StockFluctuationDto.toDTO(response);

        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "Success", dto));

    }

}