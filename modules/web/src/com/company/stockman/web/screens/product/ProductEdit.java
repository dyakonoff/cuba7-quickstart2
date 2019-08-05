package com.company.stockman.web.screens.product;

import com.haulmont.cuba.gui.screen.*;
import com.company.stockman.entity.Product;


/**
 * Created by Aleksey Stukalov on 2019-06-19.
 */

@UiController("stockman_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
@LoadDataBeforeShow
public class ProductEdit extends StandardEditor<Product> {

}