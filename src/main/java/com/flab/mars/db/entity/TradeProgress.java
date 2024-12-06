package com.flab.mars.db.entity;

public enum TradeProgress {
    INITIATED("거래 요청됨"),
    PENDING("처리 중"),
    FILLED("거래 체결 완료"),
    CANCELLED("거래 취소");

    private final String description;

    TradeProgress(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

