package com.company.stockman.web.screens.product;

import com.company.stockman.entity.Product;
import com.company.stockman.entity.StockChangeType;
import com.company.stockman.service.StockService;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

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
    private CollectionLoader<Product> productsDl;

    @Install(to = "productsTable.available", subject = "columnGenerator")
    private Component productsTableAvailableColumnGenerator(Product product) {
         return new Table.PlainTextCell(product.getStock().getQuantity().toString());

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
                .withParameter(InputParameter.intParameter("quantity")
                        .withRequired(true))
                .withValidator(this::validateInputData)
                .withActions(DialogActions.OK_CANCEL, this::applyDialogChangeRequest)
                .show();
    }

    private void applyDialogChangeRequest(InputDialog.InputDialogResult result) {
        if (!result.getCloseActionType().equals(InputDialog.InputDialogResult.ActionType.OK))
            return;

        Integer quantity = result.getValue("quantity");
        Product product = result.getValue("product");
        StockChangeType changeType = result.getValue("operation");

        assert product != null;
        assert product.getStock() != null;
        assert quantity != null;

        stockService.changeStock(product.getStock().getId(), changeType, quantity);
        productsDl.load();
    }

    private ValidationErrors validateInputData(InputDialog.ValidationContext context) {
        Integer quantity = context.getValue("queqantity");
        StockChangeType op = context.getValue("operation");
        Product product = context.getValue("product");

        assert product != null;
        
        if (quantity == null || quantity < 0) {
            return ValidationErrors.of("Quantity should be positive");
        }
        if (StockChangeType.DEDUCT.equals(op) && quantity > product.getStock().getQuantity()) {
            return ValidationErrors.of("Not enough items in stock");
        }
        return ValidationErrors.none();
    }
}