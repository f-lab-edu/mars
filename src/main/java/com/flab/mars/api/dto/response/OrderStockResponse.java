package com.flab.mars.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStockResponse {
    private Long OrderId;
    private String status;  // 예: "fail", "pending"
    private String message; // 선택적 설명
}
