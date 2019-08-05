package com.company.stockman.web.screens.product;

import com.company.stockman.entity.Product;
import com.company.stockman.entity.StockChangeType;
import com.company.stockman.service.StockService;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.math.BigDecimal;


/**
 * Created by Aleksey Stukalov on 2019-06-19.
 */

@UiController("stockman_Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
@LoadDataBeforeShow
public class ProductBrowse extends StandardLookup<Product> {

    @Inject
    private StockService stockService;
    @Inject
    private Dialogs dialogs;
    @Inject
    private CollectionContainer<Product> productsDc;
    @Inject
    private GroupTable<Product> productsTable;

    @Install(to = "productsTable.available", subject = "columnGenerator")
    private Component productsTableAvailableColumnGenerator(Product product) {
        return new Table.PlainTextCell(stockService.checkStockAvailability(Id.of(product)).toString());
    }

    @Subscribe("changeStockBtn")
    private void onChangeStockBtnClick(Button.ClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption("Change Stock")
                .withParameter(InputParameter.entityParameter("product", Product.class)
                        .withRequired(true)
                        .withDefaultValue(productsDc.getItemOrNull()))
                .withParameter(InputParameter.enumParameter("operation", StockChangeType.class)
                        .withRequired(true)
                        .withDefaultValue(StockChangeType.REPLENISH))
                .withParameter(InputParameter.bigDecimalParameter("quantity")
                        .withRequired(true))
                .withValidator(context -> {
                    BigDecimal quantity = context.getValue("quantity");
                    StockChangeType op = context.getValue("operation");
                    if (quantity == null || quantity.compareTo(BigDecimal.ZERO) < 0) {
                        return ValidationErrors.of("Quantity should be positive");
                    }
                    if (op == StockChangeType.DEDUCT) {
                        Product product = context.getValue("product");
                        BigDecimal inStockCount = stockService.checkStockAvailability(Id.of(product));
                        if (inStockCount.compareTo(BigDecimal.ZERO) <= 0 || inStockCount.compareTo(quantity) < 0) {
                            return ValidationErrors.of("Not enough items in stock");
                        }
                    }
                    return ValidationErrors.none();
                })
                .withActions(DialogActions.OK_CANCEL, result -> {
                    if (!result.getCloseActionType().equals(InputDialog.InputDialogResult.ActionType.OK))
                        return;

                    BigDecimal quantity = result.getValue("quantity");
                    Product product = result.getValue("product");
                    StockChangeType changeType = result.getValue("operation");

                    stockService.changeStock(Id.of(product), changeType, quantity);
                    productsTable.repaint();
                })
                .show();
    }
}