package com.flab.mars.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiCredentialsRequestDto {

    String appKey;
    String appSecret;
}
