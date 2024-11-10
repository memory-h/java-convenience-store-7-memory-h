package store.controller;

import store.domain.PurchaseSummaryList;
import store.domain.ReceiptResult;
import store.domain.conveniencestore.Product;
import store.domain.conveniencestore.Products;
import store.domain.ProductsPurchase;
import store.domain.ProductsUserInput;
import store.domain.conveniencestore.Promotion;
import store.domain.conveniencestore.Promotions;
import store.utils.PurchaseSummaryGenerator;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.Map;

import static store.utils.calculator.PromotionCalculator.calculatePromotionPurchase;
import static store.utils.calculator.PurchaseReceipt.*;

public class StoreController {

    private final StoreInventoryController storeInventoryController = new StoreInventoryController();
    private final StorePromotionController storePromotionController = new StorePromotionController();

    public void run() {
        final Products products = storeInventoryController.init();
        final Promotions promotions = storePromotionController.init();

        startPurchaseProcess(products.getProducts(), promotions.getPromotions());
    }

    private void startPurchaseProcess(final List<Product> products, final List<Promotion> promotions) {
        try {
            Map<String, Integer> productsPurchase = getProductsPurchase(products).getProductsPurchase();
            Map<String, Integer> promotionResult = calculatePromotionPurchase(productsPurchase, products, promotions);
            receiptPrint(products, productsPurchase, promotionResult);
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            startPurchaseProcess(products, promotions);
        }
    }

    private ProductsPurchase getProductsPurchase(final List<Product> products) {
        String userInput = InputView.readProducts();
        ProductsUserInput productsUserInput = ProductsUserInput.from(userInput);
        return ProductsPurchase.of(products, productsUserInput.getProductsUserInput());
    }

    private void receiptPrint(final List<Product> products, final Map<String, Integer> productsPurchase, final Map<String, Integer> promotionResult) {
        ReceiptResult receiptResult = getReceipt(products, productsPurchase, promotionResult);
        PurchaseSummaryList purchaseSummaryList = PurchaseSummaryGenerator.generateSummaryList(productsPurchase, products);
        OutputView.receiptMessage(receiptResult.getReceiptData(), promotionResult, purchaseSummaryList.getPurchaseSummaries());
    }

    private ReceiptResult getReceipt(final List<Product> products, final Map<String, Integer> productsPurchase, final Map<String, Integer> promotionResult) {
        int totalPurchasesReceipt = calculateTotalPurchases(productsPurchase, products);
        int promotionDiscountReceipt = calculatePromotionDiscount(promotionResult, products);
        int membershipDiscountReceipt = calculateMembershipDiscount(productsPurchase, products);
        int receiveToMoneyReceipt = calculateFinalAmount(totalPurchasesReceipt, promotionDiscountReceipt, membershipDiscountReceipt);
        return ReceiptResult.of(totalPurchasesReceipt, promotionDiscountReceipt, membershipDiscountReceipt, receiveToMoneyReceipt);
    }

}