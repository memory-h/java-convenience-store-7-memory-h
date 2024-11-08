package store.controller;

public class StoreController {

    private final StoreInventoryController storeInventoryController = new StoreInventoryController();
    private final StorePromotionController storePromotionController = new StorePromotionController();

    public void run() {
        storeInventoryController.init();
        storePromotionController.init();
    }

}