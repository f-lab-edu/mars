package com.flab.mars.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "필수 항목입니다.")
    String appKey;

    @NotBlank(message = "필수 항목입니다.")
    String appSecret;

    @NotBlank(message = "필수 항목입니다.")
    String email;

    @NotBlank(message = "필수 항목입니다.")
    String pw;
}
