package com.flab.mars.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddStockRequest {
    @NotBlank(message = "필수 항목입니다.")
    private String stockCode;

    @NotBlank(message = "필수 항목입니다.")
    private String stockName;

    @NotNull(message = "필수 항목입니다.")
    private Long memberId;
}
