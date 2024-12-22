package com.flab.mars.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.mars.api.dto.request.ApiCredentialsRequestDto;
import com.flab.mars.api.dto.response.StockPriceResponseDto;
import com.flab.mars.client.KISClient;
import com.flab.mars.domain.service.StockService;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.domain.vo.response.PriceDataResponseVO;
import com.flab.mars.domain.vo.response.StockFluctuationResponseVO;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StockController.class)
@WithMockUser(roles = "USER") // 인증된 사용자
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StockService stockService;

    @MockitoBean
    private KISClient kisClient;


    @Test
    public void testGetAccessToken_Success() throws Exception {
        // Arrange
        ApiCredentialsRequestDto request = new ApiCredentialsRequestDto("appKey", "appSecret");

        when(kisClient.getAccessToken(
                eq("appKey"),
                eq("appSecret"),
                anyString()
        )).thenReturn("mockAccessToken");

        Mockito.doNothing().when(stockService).getAccessToken(any(TokenInfo.class), any(HttpSession.class));

        // Act & Assert
        mockMvc.perform(post("/api/stock/accessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                )
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // 응답 로깅
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultMsg").value("Success"));

        verify(stockService, times(1)).getAccessToken(any(TokenInfo.class), any(HttpSession.class));
    }

    @Test
    void testGetStockPrice_Success() throws Exception {
        // Arrange
        String stockCode = "12345";
        PriceDataResponseVO priceDataResponseVO = new PriceDataResponseVO();

        // Mock stockService.getStockPrice() 메서드
        when(stockService.getStockPrice(eq(stockCode), any(HttpSession.class))).thenReturn(priceDataResponseVO);

        // Act & Assert
        mockMvc.perform(get("/api/stock/quotations/inquire-price")
                        .param("stockCode", stockCode)
                        .sessionAttr("mockSession", new MockHttpSession())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultMsg").value("Success"));

        verify(stockService, times(1)).getStockPrice(eq(stockCode), any(HttpSession.class));
    }

    @Test
    void testGetFluctuationRanking_Success() throws Exception {
        // Given
        StockFluctuationResponseVO mockResponse = new StockFluctuationResponseVO();
        List<StockFluctuationResponseVO.StockFluctuationVO> stockFluctuations = new ArrayList<>();

        // Mock data
        stockFluctuations.add(new StockFluctuationResponseVO.StockFluctuationVO(
                "AAPL", "1", "Apple Inc.", "145.00", "1.50", "+", "1.04"));

        mockResponse.setOutput(stockFluctuations); // Set the output field properly

        // Mocking the service call
        when(stockService.getFluctuationRanking(anyString(), any())).thenReturn(mockResponse);
        // Act & Assert
        mockMvc.perform(get("/api/stock/domestic-stock/ranking/fluctuation")
                        .param("fidInputIscd", "0001")
                        .param("fidRankSortClsCode", "0")
                        .param("fidInputCnt1", "0")
                        .param("fidPrcClsCode", "0")
                        .param("fidInputPrice1", "")
                        .param("fidInputPrice2", "")
                        .param("fidVolCnt", "")
                        .sessionAttr("mockSession", new MockHttpSession())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // 응답 로깅
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultMsg").value("Success"))
                .andExpect(jsonPath("$.resultData.output[0].stockCode").value("AAPL")) // 첫 번째 output의 stockCode 검증
                .andExpect(jsonPath("$.resultData.output[0].dataRank").value("1")) // 첫 번째 output의 dataRank 검증
                .andExpect(jsonPath("$.resultData.output[0].stockName").value("Apple Inc.")) // 첫 번째 output의 stockName 검증
                .andExpect(jsonPath("$.resultData.output[0].stockPrice").value("145.00")) // 첫 번째 output의 stockPrice 검증
                .andExpect(jsonPath("$.resultData.output[0].priceChange").value("1.50")) // 첫 번째 output의 priceChange 검증
                .andExpect(jsonPath("$.resultData.output[0].priceChangeSign").value("+")) // 첫 번째 output의 priceChangeSign 검증
                .andExpect(jsonPath("$.resultData.output[0].priceChangeRate").value("1.04")) // 첫 번째 output의 priceChangeRate 검증
                .andExpect(jsonPath("$.resultData.output").isArray()) // output 배열이 존재하는지 검증
                .andExpect(jsonPath("$.resultData.output.length()").value(1)); // output 배열의 길이가 1인지 검증
    }
}