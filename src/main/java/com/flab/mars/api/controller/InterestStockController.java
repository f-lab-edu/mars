package com.flab.mars.api.controller;


import com.flab.mars.api.dto.request.AddStockRequest;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.domain.service.InterestStockService;
import com.flab.mars.domain.vo.MemberInfoVO;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interest-stocks")
public class InterestStockController {

    private final InterestStockService interestStockService;

    @PostMapping
    public ResponseEntity<ResultAPIDto<Long>> registerInterestStock(@RequestBody @Valid AddStockRequest request, HttpSession session) {
        MemberInfoVO sessionLoginUser = SessionUtil.getSessionLoginUser(session);

        if(sessionLoginUser == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Long interestStockId  = interestStockService.registerInterestStock(sessionLoginUser.getId(), request.getStockCode(), request.getStockName());

        // 중복 등록 방지
        if(interestStockId == -1L)  return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.CONFLICT, "This stock is already registered.", null));

        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "Success", interestStockId ));
    }


}
