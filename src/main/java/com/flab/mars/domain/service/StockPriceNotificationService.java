package com.flab.mars.domain.service;

import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.repository.InterestStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

// DB 에 +-5% 값을 가지는 stockid 를 가져와서
// 해당 interrst 에 stockid 기반으로 member 를 가져오고
// 해당 정보를 기반으로 memberid 에게 문자 보내기
@Service
@RequiredArgsConstructor
public class StockPriceNotificationService {

    private final StockPriceFetcher stockPriceFetcher;

    private final InterestStockRepository interestStockRepository;

    private final double CHANGE_RATE = 5.0;

    public void notifyStockPrice() {
        Stream<Long> priceDataEntityStream = stockPriceFetcher.fetchPagedStockIdWithHighChangeRate(CHANGE_RATE);
        priceDataEntityStream.forEach(stockId -> {
            List<InterestStockEntity> interestStockEntities = interestStockRepository.findByStockInfoId(stockId);
            // 관심 주식을 가진 사용자들에게 문자 전송
            interestStockEntities.forEach(interestStock -> {
                // TODO
                Long memberId = interestStock.getMemberId();

            });

        });
    }
}
