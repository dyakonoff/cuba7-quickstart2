package com.company.stockman.service;

import com.company.stockman.entity.Product;
import com.company.stockman.entity.StockChangeType;
import com.company.stockman.entity.StockItem;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service(StockService.NAME)
public class StockServiceBean implements StockService {

    @Inject
    private DataManager dataManager;

    @Override
    public BigDecimal checkStockAvailability(Id<Product, UUID> productId) {
        Optional<StockItem> stockItem = dataManager
                .load(StockItem.class)
                .query("select e from stockman_StockItem e where e.product.id = :productId")
                .parameter("productId", productId)
                .optional();

        return stockItem.map(StockItem::getQuantity).orElse(BigDecimal.ZERO);
    }

    @Override
    public void changeStock(Id<Product, UUID> productId, StockChangeType changeType, BigDecimal quantity) {
        StockItem stockItem = dataManager
                .load(StockItem.class)
                .query("select e from stockman_StockItem e where e.product.id = :productId")
                .parameter("productId", productId)
                .optional()
                .orElseGet(() -> {
                    StockItem newStockItem = dataManager.create(StockItem.class);
                    newStockItem.setProduct(dataManager.getReference(productId));
                    return newStockItem;
                });
        BigDecimal difference = StockChangeType.DEDUCT.equals(changeType) ? quantity.negate() : quantity;
        stockItem.setQuantity(stockItem.getQuantity().add(difference));

        dataManager.commit(stockItem);
    }
}