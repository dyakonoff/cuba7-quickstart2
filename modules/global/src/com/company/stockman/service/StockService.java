package com.company.stockman.service;

import com.company.stockman.entity.Product;
import com.company.stockman.entity.StockChangeType;
import com.haulmont.cuba.core.entity.contracts.Id;

import java.math.BigDecimal;
import java.util.UUID;

public interface StockService {
    String NAME = "stockman_StockService";

    BigDecimal checkStockAvailability(Product product);

    void changeStock(Product product, StockChangeType changeType, BigDecimal quantity);
}