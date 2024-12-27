package com.flab.mars.api.controller;

import com.flab.mars.api.dto.request.StockFluctuationRequestDto;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.api.dto.response.StockFluctuationDto;
import com.flab.mars.domain.service.StockService;
import com.flab.mars.domain.vo.StockPrice;
import com.flab.mars.domain.vo.response.StockFluctuationResponseVO;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<ResultAPIDto<StockPrice>> getStockPrice(@RequestParam(name = "stockCode") String stockCode, HttpSession session) {
        StockPrice stockPrice = stockService.getStockPrice(stockCode, session);
        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "Success", stockPrice));
    }

    /**
     * 국내주식 등락률 순위
     * 최대 30건 확인 가능하며, 다음 조회가 불가합니다.
     * @param request
     * @param session
     * @return
     */
    @GetMapping("/domestic-stock/ranking/fluctuation")
    public ResponseEntity<ResultAPIDto<StockFluctuationDto>> getFluctuationRanking(@ModelAttribute @Valid StockFluctuationRequestDto request, HttpSession session) {

        String uri = request.generateFluctuationRankingUri();
        StockFluctuationResponseVO response = stockService.getFluctuationRanking(uri, session);
        StockFluctuationDto dto = StockFluctuationDto.toDTO(response);

        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "Success", dto));

    }

}