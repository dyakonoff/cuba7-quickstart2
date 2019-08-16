package com.company.stockman.service;

import com.company.stockman.entity.StockChangeType;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

public interface StockService {
    String NAME = "stockman_StockService";

    @Validated
    void changeStock(@NotNull UUID stockId, StockChangeType changeType, @PositiveOrZero int quantity);
}