package com.flab.mars.domain.service;

import com.flab.mars.db.repository.PriceDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class StockPriceFetcher {
    private static final int PAGE_SIZE = 20;
    private final PriceDataRepository priceDataRepository;

    // 변동률 5 이상인 주식들 가져오기
    public Stream<Long> fetchPagedStockIdWithHighChangeRate(Double priceChangeRate) {
        return Stream.iterate(0, page -> page + 1) // 페이지 번호를 0 부터 하나씩 증가시킴
                .map(page -> {
                    Pageable pageable = PageRequest.of(page, PAGE_SIZE);
                    Page<Long> stockIds = priceDataRepository.findDistinctStockInfoIdsByPriceChangeRateGreaterThanEqual(priceChangeRate, pageable);
                    return (stockIds != null && stockIds.hasContent())
                            ? stockIds.getContent()
                            : List.<Long>of(); // null이면 빈 리스트 반환
                })
                .takeWhile(stockIds -> stockIds != null && !stockIds.isEmpty())// 데이터가 비어있지 않으면 계속 반복, 비면 종료
                .flatMap(List::stream);
    }
}
