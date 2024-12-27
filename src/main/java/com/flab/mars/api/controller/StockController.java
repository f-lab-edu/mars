package com.flab.mars.api.controller;

import com.flab.mars.api.dto.request.StockFluctuationRequestDto;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.api.dto.response.StockFluctuationDto;
import com.flab.mars.domain.service.StockService;
import com.flab.mars.domain.vo.StockPrice;
import com.flab.mars.domain.vo.request.StockFluctuationRequestVO;
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

    @GetMapping("/domestic-stock/ranking/fluctuation")
    public ResponseEntity<ResultAPIDto<StockFluctuationDto>> getFluctuationRanking(@ModelAttribute @Valid StockFluctuationRequestDto request, HttpSession session) {

        StockFluctuationRequestVO vo = StockFluctuationRequestVO.builder()
                .fidInputIscd(request.getFidInputIscd())  // 기본값: "0001"
                .fidRankSortClsCode(request.getFidRankSortClsCode())  // 기본값: "0"
                .fidInputCnt1(request.getFidInputCnt1())  // 기본값: "0"
                .fidPrcClsCode(request.getFidPrcClsCode())  // 기본값: "0"
                .fidInputPrice1(request.getFidInputPrice1())  // 기본값: ""
                .fidInputPrice2(request.getFidInputPrice2())  // 기본값: ""
                .fidVolCnt(request.getFidVolCnt())  // 기본값: ""
                .build();

        String uri = vo.generateFluctuationRankingUri();

        StockFluctuationResponseVO response = stockService.getFluctuationRanking(uri, session);
        StockFluctuationDto dto = StockFluctuationDto.toDTO(response);

        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "Success", dto));

    }

}