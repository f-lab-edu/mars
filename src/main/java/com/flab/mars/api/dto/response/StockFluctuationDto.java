package com.flab.mars.api.dto.response;

import com.flab.mars.domain.vo.response.StockFluctuationResponseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockFluctuationDto {
    private List<StockFluctuation> output;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockFluctuation {
        private String stockCode;
        private String dataRank;
        private String stockName;
        private String stockPrice;
        private String priceChange;
        private String priceChangeSign;
        private String priceChangeRate;
    }

    public static StockFluctuationDto toDTO(StockFluctuationResponseVO vo) {
        List<StockFluctuation> stockFluctuations = vo.getOutput().stream()
                .map(stockFluctuationVO -> new StockFluctuation(
                        stockFluctuationVO.getStockCode(),
                        stockFluctuationVO.getDataRank(),
                        stockFluctuationVO.getStockName(),
                        stockFluctuationVO.getStockPrice(),
                        stockFluctuationVO.getPriceChange(),
                        stockFluctuationVO.getPriceChangeSign(),
                        stockFluctuationVO.getPriceChangeRate()
                ))
                .collect(Collectors.toList());

        return new StockFluctuationDto(stockFluctuations);
    }
}
