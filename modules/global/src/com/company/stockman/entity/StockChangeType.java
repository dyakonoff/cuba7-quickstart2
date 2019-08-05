package com.company.stockman.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum StockChangeType implements EnumClass<String> {

    REPLENISH("R"),
    DEDUCT("D");

    private String id;

    StockChangeType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static StockChangeType fromId(String id) {
        for (StockChangeType at : StockChangeType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}