package com.flab.mars.domain.component;

import com.flab.mars.db.entity.StockOrderEntity;
import com.flab.mars.db.repository.StockOrderRepository;
import com.flab.mars.domain.vo.response.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdempotencyValidator {
    private final StockOrderRepository stockOrderRepository;

    public Optional<OrderResult> validate(String idempotencyKey) {
        StockOrderEntity existingOrder  = stockOrderRepository.findByIdempotencyKey(idempotencyKey);
        if (existingOrder != null) {
            OrderResult result = OrderResult.builder()
                    .orderId(existingOrder.getId())
                    .status(existingOrder.getOrderStatus().name().toLowerCase())
                    .message("이미 처리된 주문입니다.")
                    .build();
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
