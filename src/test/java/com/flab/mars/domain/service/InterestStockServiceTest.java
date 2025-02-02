package com.flab.mars.domain.service;

import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.InterestStockRepository;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.db.repository.StockInfoRepository;
import com.flab.mars.domain.StockCodeValidator;
import com.flab.mars.domain.vo.TokenInfoVO;
import com.flab.mars.domain.vo.response.InterestStockVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterestStockServiceTest {

    @InjectMocks
    private InterestStockService interestStockService;

    @Mock
    private StockInfoRepository stockInfoRepository;

    @Mock
    private InterestStockRepository interestStockRepository;

    @Mock
    private PriceDataRepository priceDataRepository;

    @Mock
    private StockCodeValidator stockCodeValidator;

    private StockInfoEntity stockInfoEntity;
    private InterestStockEntity interestStockEntity;
    private PriceDataEntity priceDataEntity;

    @BeforeEach
    void setUp() {
        Long memberId = 1L;
        stockInfoEntity = new StockInfoEntity(1L, "AAPL", "Apple Inc.");
        interestStockEntity = new InterestStockEntity(1L, stockInfoEntity, memberId);
        priceDataEntity = PriceDataEntity.builder()
                .id(1L)
                .stockInfoEntity(stockInfoEntity)  // stockInfoEntity는 이미 준비된 StockInfoEntity 객체
                .currentPrice("150")
                .openPrice("145")
                .closePrice("149")
                .highPrice("152")
                .lowPrice("144")
                .acmlVol("1000000")
                .acmlTrPbmn("150000000")
                .prdyVrss("1.5")
                .prdyVrssSign("+")
                .prdyCtrt("1.0")
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * 관심 주식 등록하기
     */
    @Test
    void testRegisterInterestStock_NewStock() {
        Long memberId = 1L;
        String stockCode = "00123";
        TokenInfoVO token = new TokenInfoVO("appkey", "appSecret", "accessToken");

        // 주식 등록
        when(stockInfoRepository.findByStockCode(anyString())).thenReturn(Optional.empty());
        when(stockInfoRepository.save(any(StockInfoEntity.class))).thenReturn(stockInfoEntity);
        when(interestStockRepository.findByMemberIdAndStockInfo(anyLong(), any(StockInfoEntity.class))).thenReturn(Optional.empty());

        when(stockCodeValidator.validateAndGetStockName(stockCode, token)).thenReturn("Apple");


        when(interestStockRepository.save(any(InterestStockEntity.class))).thenReturn(interestStockEntity);

        interestStockService.registerInterestStock(memberId, stockCode, token);

        verify(stockInfoRepository, times(1)).save(any(StockInfoEntity.class));
        verify(interestStockRepository, times(1) ).save(any(InterestStockEntity.class));

    }

    /**
     * 관심 주식 이미 등록된 경우 성공 처리
     */
    @Test
    void testRegisterInterestStock_ExistingStock() {
        Long memberId = 1L;
        when(stockInfoRepository.save(any(StockInfoEntity.class))).thenReturn(stockInfoEntity);

        when(interestStockRepository.findByMemberIdAndStockInfo(memberId, stockInfoEntity))
                .thenReturn(Optional.of(interestStockEntity));

        Long stockId = interestStockService.registerInterestStock(1L, stockInfoEntity.getStockCode(), any(TokenInfoVO.class));

        assertEquals(interestStockEntity.getId(), stockId);

        // 이미 저장된 주식인 경우 저장 로직 미호출
        verify(interestStockRepository, times(0)).save(any(InterestStockEntity.class));
    }

    /**
     * 관심 주식 가져오기 테스트
     */

    @Test
    void testGetInterestStocks() {
        Long memberId = 1L;
        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Order.asc("id")));

        Page<InterestStockEntity> interestStockEntityPage = new PageImpl<>(Collections.singletonList(interestStockEntity), pageable, 1);
        when(interestStockRepository.findByMemberId(memberId, pageable)).thenReturn(interestStockEntityPage);


        Page<InterestStockVO> interestStockVOs = interestStockService.getInterestStocks(memberId, pageable);


        assertNotNull(interestStockVOs);
        assertEquals(1, interestStockVOs.get().toList().size());

        InterestStockVO vo = interestStockVOs.get().toList().getFirst();
        assertEquals(interestStockEntity.getStockInfo().getStockCode(), vo.getStockCode());
        assertEquals(interestStockEntity.getStockInfo().getStockName(), vo.getStockName());
    }
}