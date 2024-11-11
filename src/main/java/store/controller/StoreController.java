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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static store.common.ViewMessage.YES;
import static store.utils.calculator.PromotionCalculator.calculatePurchaseWithPromotions;
import static store.utils.calculator.PurchaseReceipt.*;

public class StoreController {

    private static final String EMPTY_STRING = "";
    private final static int DEFAULT_VALUE = 0;

    private final StoreInventoryController storeInventoryController = new StoreInventoryController();
    private final StorePromotionController storePromotionController = new StorePromotionController();
    private final Products products = storeInventoryController.init();
    private final Promotions promotions = storePromotionController.init();

    public void run() {
        startPurchaseProcess(products.getProducts(), promotions.getPromotions());
    }

    private void startPurchaseProcess(final List<Product> products, final List<Promotion> promotions) {
        try {
            Map<String, Integer> productsPurchase = getProductsPurchase(products).getProductsPurchase();
            Map<String, Integer> promotionResult = calculatePurchaseWithPromotions(productsPurchase, products, promotions);
            receiptPrint(products, productsPurchase, promotionResult);
            additionalPurchase();
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            getProductsPurchase(products);
        }
    }

    private ProductsPurchase getProductsPurchase(final List<Product> products) {
        Map<String, Integer> productsPurchaseMap = null;
        try {
            String userInput = InputView.readProducts();
            productsPurchaseMap = processUserInputAndCreatePurchaseMap(userInput, products);
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            getProductsPurchase(products);
        }
        return ProductsPurchase.of(products, productsPurchaseMap);
    }

    private Map<String, Integer> processUserInputAndCreatePurchaseMap(final String userInput, final List<Product> products) {
        ProductsUserInput productsUserInput = ProductsUserInput.from(userInput);
        productsUserInput.checkPromotionEligibilityAndUpdateQuantity(
                productsUserInput.getProductsUserInput(), products, promotions.getPromotions()
        );
        return new LinkedHashMap<>(ProductsPurchase.of(products, productsUserInput.getProductsUserInput()).getProductsPurchase());
    }

    private void receiptPrint(final List<Product> products, final Map<String, Integer> productsPurchase, final Map<String, Integer> promotionResult) {
        ReceiptResult receiptResult = getReceipt(products, productsPurchase, promotionResult);
        PurchaseSummaryList purchaseSummaryList = PurchaseSummaryGenerator.generateSummaryList(productsPurchase, products);
        OutputView.receiptMessage(receiptResult.getReceiptData(), promotionResult, purchaseSummaryList.getPurchaseSummaries());
    }

    private ReceiptResult getReceipt(final List<Product> products, final Map<String, Integer> productsPurchase, final Map<String, Integer> promotionResult) {
        int totalPurchasesReceipt = calculateTotalPurchases(productsPurchase, products);
        int promotionDiscountReceipt = calculatePromotionDiscount(promotionResult, products);
        int membershipDiscountReceipt = getMembershipDiscountReceipt(products, productsPurchase);
        int receiveToMoneyReceipt = calculateFinalAmount(totalPurchasesReceipt, promotionDiscountReceipt, membershipDiscountReceipt);
        return ReceiptResult.of(totalPurchasesReceipt, promotionDiscountReceipt, membershipDiscountReceipt, receiveToMoneyReceipt);
    }

    private int getMembershipDiscountReceipt(final List<Product> products, final Map<String, Integer> productsPurchase) {
        try {
            String userInput = InputView.printMembershipStatus();
            if (userInput.equals(YES.getMessage())) {
                return calculateMembershipDiscount(productsPurchase, products);
            }
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            getMembershipDiscountReceipt(products, productsPurchase);
        }
        return DEFAULT_VALUE;
    }

    private void additionalPurchase() {
        try {
            String userInput = InputView.printAdditionalPurchase();
            isRestartRequested(userInput);
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
        }
    }

    private void isRestartRequested(final String userInput) {
        if (userInput.equals(YES.getMessage())) {
            OutputView.startMessage(EMPTY_STRING);
            OutputView.printMessage(products.toString());
            run();
        }
    }

}