package com.company.stockman.web.screens.product;

import com.company.stockman.entity.Product;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

@UiController("stockman_ProductAvaliabilityCellFragment")
@UiDescriptor("product-avaliability-cell-fragment.xml")
public class ProductAvaliabilityCellFragment extends ScreenFragment {

    @WindowParam
    protected Product product;


    public void onBtnAddClick() {
    }

    public void onBtnMinusClick() {
    }
}