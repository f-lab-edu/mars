package com.flab.mars.domain.vo;


import com.flab.mars.client.dto.KisStockPriceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class StockPriceVO  {
    private Output output;

    @AllArgsConstructor
    @Getter
    public static class Output {
        private String stckPrpr; // 주식 현재가
    }

    public static StockPriceVO from(KisStockPriceDto stockPriceDto) {
        Objects.requireNonNull(stockPriceDto, "KisStockPriceDto cannot be null");
        StockPriceVO.Output voOutput = new StockPriceVO.Output(stockPriceDto.getOutput().getStckPrpr());
        return new StockPriceVO(voOutput);
    }

}