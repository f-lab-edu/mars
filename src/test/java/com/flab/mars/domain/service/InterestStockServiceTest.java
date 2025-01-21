package com.flab.mars.domain.service;

import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.InterestStockRepository;
import com.flab.mars.db.repository.MemberRepository;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.db.repository.StockInfoRepository;
import com.flab.mars.domain.vo.response.InterestStockVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
    private MemberRepository memberRepository;

    @Mock
    private PriceDataRepository priceDataRepository;

    private MemberEntity memberEntity;
    private StockInfoEntity stockInfoEntity;
    private InterestStockEntity interestStockEntity;
    private PriceDataEntity priceDataEntity;

    @BeforeEach
    void setUp() {
        memberEntity = new MemberEntity(1L, "John", "test@test.com", "password!@#A");
        stockInfoEntity = new StockInfoEntity(1L, "AAPL", "Apple Inc.");
        interestStockEntity = new InterestStockEntity(1L, stockInfoEntity, memberEntity);
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
        // 주식 등록
        when(memberRepository.findById(memberEntity.getId())).thenReturn(Optional.of(memberEntity));
        when(stockInfoRepository.findByStockCode(anyString())).thenReturn(Optional.empty());
        when(stockInfoRepository.save(any(StockInfoEntity.class))).thenReturn(stockInfoEntity);
        when(interestStockRepository.findByMemberAndStockInfo(any(MemberEntity.class), any(StockInfoEntity.class))).thenReturn(Optional.empty());

        when(interestStockRepository.save(any(InterestStockEntity.class))).thenReturn(interestStockEntity);

        interestStockService.registerInterestStock(memberEntity.getId(), stockInfoEntity.getStockCode(), stockInfoEntity.getStockName());

        verify(stockInfoRepository, times(1)).save(any(StockInfoEntity.class));
        verify(interestStockRepository, times(1) ).save(any(InterestStockEntity.class));

    }

    /**
     * 관심 주식 이미 등록된 경우 성공 처리
     */
    @Test
    void testRegisterInterestStock_ExistingStock() {
        when(memberRepository.findById(memberEntity.getId())).thenReturn(Optional.of(memberEntity));
        when(stockInfoRepository.save(any(StockInfoEntity.class))).thenReturn(stockInfoEntity);

        when(interestStockRepository.findByMemberAndStockInfo(memberEntity, stockInfoEntity))
                .thenReturn(Optional.of(interestStockEntity));

        Long stockId = interestStockService.registerInterestStock(1L, stockInfoEntity.getStockCode(), stockInfoEntity.getStockName());

        assertEquals(interestStockEntity.getId(), stockId);

        // 이미 저장된 주식인 경우 저장 로직 미호출
        verify(interestStockRepository, times(0)).save(any(InterestStockEntity.class));
    }

    /**
     * 관심 주식 가져오기 테스트
     */

    @Test
    void testGetInterestStocks() {
        when(memberRepository.findById(memberEntity.getId())).thenReturn(Optional.of(memberEntity));
        when(interestStockRepository.findByMember(memberEntity)).thenReturn(Collections.singletonList(interestStockEntity));
        when(priceDataRepository.findTopByStockInfoEntityIdAndDateTimeAfterOrderByDateTimeDesc(any(Long.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(priceDataEntity));

        List<InterestStockVO> interestStockVOs = interestStockService.getInterestStocks(memberEntity.getId());


        assertNotNull(interestStockVOs);
        assertEquals(1, interestStockVOs.size());

        InterestStockVO vo = interestStockVOs.getFirst();
        assertEquals(interestStockEntity.getStockInfo().getStockCode(), vo.getStockCode());
        assertEquals(interestStockEntity.getStockInfo().getStockName(), vo.getStockName());
        assertEquals(priceDataEntity.getCurrentPrice(), vo.getCurrentPrice());
        assertEquals(priceDataEntity.getPrdyCtrt(), vo.getPrdyCtrt());
        assertEquals(priceDataEntity.getPrdyVrssSign(), vo.getPrdyVrssSign());
        assertEquals(priceDataEntity.getPrdyVrss(), vo.getPrdyVrss());
    }
}