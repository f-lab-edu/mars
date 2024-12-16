package com.flab.mars.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class KISConfig {
    @Value("${kis.api.grant_type}")
    private String grantType;

}