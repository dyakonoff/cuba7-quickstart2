package com.company.stockman.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@NamePattern("%s|name")
@Table(name = "STOCKMAN_PRODUCT")
@Entity(name = "stockman_Product")
public class Product extends StandardEntity {
    private static final long serialVersionUID = -7255854577604196372L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @PositiveOrZero(message = "Price should be positive or zero")
    @NotNull
    @Column(name = "PRICE", nullable = false)
    protected BigDecimal price;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "product")
    protected StockItem stock;

    public StockItem getStock() {
        return stock;
    }

    public void setStock(StockItem stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PostConstruct
    public void postConstruct() {
        StockItem stockItem = AppBeans.get(DataManager.class).create(StockItem.class);
        stockItem.setProduct(this);
        setStock(stockItem);
    }
}