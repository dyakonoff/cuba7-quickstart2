package com.company.stockman.service;

import com.company.stockman.entity.StockChangeType;
import com.company.stockman.entity.StockItem;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Service(StockService.NAME)
public class StockServiceBean implements StockService {

    @Inject
    private DataManager dataManager;

    @Validated
    @Override
    public void changeStock(@NotNull UUID stockId, StockChangeType changeType, @PositiveOrZero int quantity) {
        StockItem stockItem = dataManager.load(StockItem.class)
                .view(View.LOCAL)
                .id(stockId)
                .one();

        if (changeType.equals(StockChangeType.REPLENISH)) {
            stockItem.setQuantity(stockItem.getQuantity() + quantity);
        } else {
            stockItem.setQuantity(stockItem.getQuantity() - quantity);
        }
        dataManager.commit(stockItem);
    }
}