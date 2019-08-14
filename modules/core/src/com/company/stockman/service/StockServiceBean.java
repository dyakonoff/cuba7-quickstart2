package com.company.stockman.service;

import com.company.stockman.entity.Product;
import com.company.stockman.entity.StockChangeType;
import com.company.stockman.entity.StockItem;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;

@Service(StockService.NAME)
public class StockServiceBean implements StockService {

    @Inject
    private DataManager dataManager;

    @Override
    public void changeStock(Product product, StockChangeType changeType, BigDecimal quantity) {
        BigDecimal difference = StockChangeType.DEDUCT.equals(changeType) ? quantity.negate() : quantity;
        Product reloadedProduct = dataManager.reload(product, "product-with-stock");
        StockItem stockItem = reloadedProduct.getStock();
        stockItem.setQuantity(stockItem.getQuantity().add(difference));
        dataManager.commit(stockItem);
    }
}