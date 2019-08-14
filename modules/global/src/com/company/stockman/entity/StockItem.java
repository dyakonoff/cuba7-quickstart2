package com.company.stockman.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NamePattern("%s: %s|product,quantity")
@Table(name = "STOCKMAN_STOCK_ITEM", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_STOCKMAN_STOCK_ITEM_UNQ", columnNames = {"PRODUCT_ID"})
})
@Entity(name = "stockman_StockItem")
public class StockItem extends StandardEntity {
    private static final long serialVersionUID = -4552892415873117917L;

    @OnDelete(DeletePolicy.DENY)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @NotNull
    @Column(name = "QUANTITY", nullable = false, precision = 0, scale = 0)
    protected BigDecimal quantity;

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @PostConstruct
    public void postConstruct() {
        quantity = BigDecimal.ZERO;
    }
}