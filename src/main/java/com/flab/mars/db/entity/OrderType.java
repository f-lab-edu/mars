package com.flab.mars.db.entity;

public enum OrderType {
    BUY("매수"),
    SELL("매도");

    private final String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
