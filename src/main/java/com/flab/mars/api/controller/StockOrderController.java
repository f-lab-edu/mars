package com.flab.mars.api.controller;

import com.flab.mars.api.converter.OrderFactory;
import com.flab.mars.api.dto.request.BuyStockRequest;
import com.flab.mars.api.dto.response.OrderStockResponse;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.domain.service.StockOrderService;
import com.flab.mars.domain.vo.AuthInfoVO;
import com.flab.mars.domain.vo.order.OrderVO;
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

        OrderVO order = OrderFactory.from(request);

        AuthInfoVO authInfo = new AuthInfoVO(request.getAppKey(), request.getAppSecret(), request.getAccessToken());

        OrderResult orderResult = stockOrderService.processOrder(order, authInfo, request.getMemberId());
        OrderStockResponse orderStockResponse = new OrderStockResponse(orderResult.getOrderId(), orderResult.getStatus(), orderResult.getMessage());

        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "주식 주문 완료", orderStockResponse));
    }
}
