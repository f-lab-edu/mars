package com.flab.mars.domain.service;


import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.StockInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class StockInfoFetcher  {
    private final StockInfoRepository stockInfoRepository;
    private static final int PAGE_SIZE = 20;

    public Stream<StockInfoEntity> fetchPagedStocksStream() {
        return Stream.iterate(0, page -> page + 1)
                .map(page -> {
                    Pageable pageable = PageRequest.of(page, PAGE_SIZE);
                    Page<StockInfoEntity> stockPage = stockInfoRepository.findAll(pageable);
                    return stockPage.hasContent() ? stockPage.getContent() : null;
                })
                .takeWhile(stocks -> stocks != null && !stocks.isEmpty())
                .flatMap(List::stream);
    }
}
