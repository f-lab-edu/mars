package com.flab.mars.api.controller;


import com.flab.mars.api.dto.request.AddStockRequest;
import com.flab.mars.api.dto.response.InterestStockDto;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.domain.service.InterestStockService;
import com.flab.mars.domain.vo.MemberInfoVO;
import com.flab.mars.domain.vo.response.InterestStockVO;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;
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
     * @param session
     * @return
     */
    @PostMapping
    public ResponseEntity<ResultAPIDto<Long>> registerInterestStock(@RequestBody @Valid AddStockRequest request, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        if(sessionLoginUser == null) return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.UNAUTHORIZED, "로그인 후 이용하여 주세요.", null ));

        long interestStockId = interestStockService.registerInterestStock(sessionLoginUser.getId(), request.getStockCode(), request.getStockName());

        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "Success", interestStockId ));
    }

    /**
     * 로그인한 user 관심 종목 가져오기
     * @param session
     * @return
     */
    @GetMapping
    public ResponseEntity<ResultAPIDto<List<InterestStockDto>>> getInterestStocks(HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        if(sessionLoginUser == null) return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.UNAUTHORIZED, "로그인 후 이용하여 주세요.", null ));

        // member_id 로 관심 주식 가져오기
        List<InterestStockVO> interestStocks = interestStockService.getInterestStocks(sessionLoginUser.getId());

        // DTO 변환
        List<InterestStockDto> list = interestStocks.stream()
                .map(stock -> new InterestStockDto(
                        stock.getStockName(),
                        stock.getStockCode(),
                        stock.getCurrentPrice(),
                        stock.getPrdyVrssSign() + stock.getPrdyVrss(), // 전일 대비
                        stock.getPrdyVrssSign() + stock.getPrdyCtrt() // 전일 대비률
                ))
                .toList();


        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "관심 종목 가져오기 Success", list));
    }

}
