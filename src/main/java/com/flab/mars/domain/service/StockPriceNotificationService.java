package com.flab.mars.domain.service;

import com.flab.mars.db.entity.InterestStockEntity;
import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.repository.InterestStockRepository;
import com.flab.mars.db.repository.MemberRepository;
import com.flab.mars.domain.component.FirebaseMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockPriceNotificationService {

    private final StockPriceFetcher stockPriceFetcher;

    private final InterestStockRepository interestStockRepository;

    private final MemberRepository memberRepository;

    private final FirebaseMessageSender firebaseMessageSender;

    private final double CHANGE_RATE = 5.0;

    @Value("${notification.stock.title}")
    private String titleTemplate;

    @Value("${notification.stock.message}")
    private String messageTemplate;

    @Transactional(readOnly = true) //  @Transactional을 사용하여 세션 유지
    public void notifyStockPrice() {
        // DB 에 +-5% 값을 가지는 stockid 를 가져와서
        Stream<Long> priceDataEntityStream = stockPriceFetcher.fetchPagedStockIdWithHighChangeRate(CHANGE_RATE);

        // stockid 기반으로 member 를 가져오고
        priceDataEntityStream.forEach(stockId -> {
            List<InterestStockEntity> interestStockEntities = interestStockRepository.findByStockInfoId(stockId);
            // 관심 주식을 가진 사용자들에게 문자 전송
            interestStockEntities.forEach(interestStock -> {
                // 해당 정보를 기반으로 memberid 에게 문자 보내기
                Long memberId = interestStock.getMemberId();
                memberRepository.findById(memberId).ifPresent(member -> SendNotification(stockId, interestStock, member));
            });
        });
    }

    private void SendNotification(Long stockId, InterestStockEntity interestStock, MemberEntity member) {
        String fcmToken = member.getFCMToken();
        if (!StringUtils.hasText(fcmToken)) {
            log.info("memberId : {} , fcm token is creating : {}", member.getId(), fcmToken);
            return;
        }

        // 주식 가격 변동률 가져오기
        Double changeRate = stockPriceFetcher.getLatestPriceChageRateForStock(stockId);
        String message = String.format(messageTemplate, changeRate);

        // Could not initialize proxy 발생 -  no session  =>   @Transactional 으로 해결
        String notificationMessage = String.format("%s %s", interestStock.getStockInfo().getStockName(), message);

        try {
            firebaseMessageSender.sendPush(fcmToken, titleTemplate, notificationMessage);
            log.info("memberId : {}, message: {} 알림 전송", member.getId(), notificationMessage);
        } catch (Exception e) {
            log.error("memberId: {}, 알림 전송 실패: {}", member.getId(), e.getMessage(), e);
        }
    }
}
