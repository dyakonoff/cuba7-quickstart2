package com.company.stockman.web.screens.product;

import com.haulmont.cuba.gui.screen.*;
import com.company.stockman.entity.Product;


@UiController("stockman_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
@LoadDataBeforeShow
public class ProductEdit extends StandardEditor<Product> {

}