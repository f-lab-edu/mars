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

    //TODO 회원 가입 및 회원 관련 로직 추후 고려
    @NotNull(message = "필수 항목입니다.")
    private Long memberId;

    @NotNull(message = "필수 항목입니다.")
    private String appKey;

    @NotNull(message = "필수 항목입니다.")
    private String appSecret;

    @NotNull(message = "필수 항목입니다.")
    private String accessToken;

}
