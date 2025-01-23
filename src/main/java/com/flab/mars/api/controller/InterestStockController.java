package com.flab.mars.api.controller;


import com.flab.mars.api.dto.request.AddStockRequest;
import com.flab.mars.api.dto.response.InterestStockDto;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.domain.service.InterestStockService;
import com.flab.mars.domain.vo.response.InterestStockVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interest-stocks")
public class InterestStockController {

    private final InterestStockService interestStockService;

    /**
     * 관심 종목 등록하기
     * @param request
     * @return 성공 여부, 관심 종목 PK
     */
    @PostMapping
    public ResponseEntity<ResultAPIDto<Long>> registerInterestStock(@RequestBody @Valid AddStockRequest request) {

        long interestStockId = interestStockService.registerInterestStock(request.getMemberId(), request.getStockCode(), request.getStockName());

        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "Success", interestStockId ));
    }

    /**
     * 로그인한 user 관심 종목 가져오기
     * @param memberId
     * @return 등록된 관심 종목리스트
     */
    @GetMapping
    public ResponseEntity<ResultAPIDto<List<InterestStockDto>>> getInterestStocks(@RequestParam("memberId") Long memberId) {
        // member_id 로 관심 주식 가져오기
        List<InterestStockVO> interestStocks = interestStockService.getInterestStocks(memberId);

        // DTO 변환
        List<InterestStockDto> list = interestStocks.stream()
                .map(stock -> new InterestStockDto(
                        stock.getStockName(),
                        stock.getStockCode(),
                        stock.getCurrentPrice(),
                        stock.getPrdyVrss(), // 전일 대비
                        stock.getPrdyCtrt() // 전일 대비률
                ))
                .toList();


        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "관심 종목 가져오기 Success", list));
    }

}
