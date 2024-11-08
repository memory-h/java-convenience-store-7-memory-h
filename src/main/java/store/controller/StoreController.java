package store.controller;

public class StoreController {

    private final StoreInventoryController storeInventoryController = new StoreInventoryController();

    public void run() {
        storeInventoryController.init();
    }

}