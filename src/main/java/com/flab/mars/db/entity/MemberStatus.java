package com.flab.mars.db.entity;

public enum MemberStatus {
    ACTIVE("활성"),
    INACTIVE("비활성");

    private final String description;

    MemberStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
