package com.flab.mars.domain.vo.response;

import com.flab.mars.client.dto.KISFluctuationResponseDto;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
public class StockFluctuationVO {
    private List<StockFluctuationItemVO> output;


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    @ToString
    public static class StockFluctuationItemVO {

        private String stockCode;

        private String dataRank;

        private String stockName;

        private String stockPrice;

        private String priceChange;

        private String priceChangeSign;

        private String priceChangeRate;
    }

    public static StockFluctuationVO dtoToVO(KISFluctuationResponseDto kisDto) {
        Objects.requireNonNull(kisDto, "KISFluctuationResponseDto cannot be null");

        List<StockFluctuationItemVO> output = kisDto.getOutput().stream()
                .map(dto -> StockFluctuationItemVO.builder()
                    .stockCode(dto.getStockCode())
                    .dataRank(dto.getDataRank())
                    .stockName(dto.getStockName())
                    .stockPrice(dto.getStockPrice())
                    .priceChange(dto.getPriceChange())
                    .priceChangeSign(dto.getPriceChangeSign())
                    .priceChangeRate(dto.getPriceChangeRate())
                    .build()
                )
                .collect(Collectors.toList());

        return new StockFluctuationVO(output);
    }

}
