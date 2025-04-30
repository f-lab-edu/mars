package com.flab.mars.api.controller;

import com.flab.mars.api.dto.request.BuyStockRequest;
import com.flab.mars.api.dto.response.OrderStockResponse;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.domain.service.StockOrderService;
import com.flab.mars.domain.vo.OrderStockVO;
import com.flab.mars.domain.vo.response.OrderResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class StockOrderController {

    private final StockOrderService stockOrderService;

    @PostMapping
    public ResponseEntity<ResultAPIDto<OrderStockResponse>> buyStock(@RequestBody @Valid BuyStockRequest request) {

        OrderStockVO orderStockVO = OrderStockVO.builder()
                .memberId(request.getMemberId())
                .appKey(request.getAppKey())
                .appSecret(request.getAppSecret())
                .accessToken(request.getAccessToken())
                .idempotencyKey(request.getIdempotencyKey())
                .orderType(request.getOrderType())
                .priceType(request.getPriceType())
                .quantity(request.getQuantity())
                .stockCode(request.getStockCode())
                .build();

        OrderResult orderResult = stockOrderService.processOrder(orderStockVO);
        OrderStockResponse orderStockResponse = new OrderStockResponse(orderResult.getOrderId(), orderResult.getStatus(), orderResult.getMessage());

        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "주식 주문 완료", orderStockResponse));
    }
}
