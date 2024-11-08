package store.controller;

import store.domain.Products;
import store.domain.ProductsUserInput;
import store.domain.Promotions;
import store.view.InputView;

public class StoreController {

    private final StoreInventoryController storeInventoryController = new StoreInventoryController();
    private final StorePromotionController storePromotionController = new StorePromotionController();

    public void run() {
        final Products products = storeInventoryController.init();
        final Promotions promotions = storePromotionController.init();

        startPurchaseProcess(products, promotions);
    }

    private void startPurchaseProcess(Products products, Promotions promotions) {
        String userInput = InputView.readProducts();
        ProductsUserInput.from(userInput);
    }

}