package com.flab.mars.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class BaseResponseVO {
    @JsonProperty("rt_cd")
    private String rtCd;

    private String msg1;

    @JsonProperty("output")
    private Output output;

    @Data
    public static class Output {
        @JsonProperty("stck_prpr")
        private String stckPrpr; // 주식 현재가
    }

}
