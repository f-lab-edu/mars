package com.flab.mars.api.dto.response;

import com.flab.mars.domain.vo.StockPriceVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockPriceDto {

    private Output output;

    @AllArgsConstructor
    @Getter
    public static class Output {
        private String stckPrpr; // 주식 현재가
    }

    public static StockPriceDto from(StockPriceVO stockPriceVO) {
        return new StockPriceDto(new Output(stockPriceVO.getOutput().getStckPrpr()));
    }
}
