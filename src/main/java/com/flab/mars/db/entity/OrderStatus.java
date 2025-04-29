package com.flab.mars.db.entity;

public enum OrderStatus {
    REQUESTED,  // 거래 요청됨
    PENDING,    // 처리 중
    FILLED,     // 거래 체결 완료
    CANCELED    // 거래 취소
}
