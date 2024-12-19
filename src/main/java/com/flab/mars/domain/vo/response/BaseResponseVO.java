package com.flab.mars.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseVO {
    @JsonProperty("rt_cd")
    private String rtCd;

    private String msg1;
}
