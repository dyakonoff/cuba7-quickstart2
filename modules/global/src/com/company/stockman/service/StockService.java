package com.company.stockman.service;

import com.company.stockman.entity.Product;
import com.company.stockman.entity.StockChangeType;

import java.math.BigDecimal;

public interface StockService {
    String NAME = "stockman_StockService";

    void changeStock(Product product, StockChangeType changeType, BigDecimal quantity);
}