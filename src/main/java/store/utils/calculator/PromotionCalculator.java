package store.utils.calculator;

import store.domain.conveniencestore.Product;
import store.domain.conveniencestore.Promotion;

import java.util.*;

public class PromotionCalculator {

    private static final int MINIMUM_PROMOTION_QUANTITY = 0;
    private static final int NO_REMAINING_QUANTITY = 0;

    private PromotionCalculator() {
    }

    public static Map<String, Integer> calculatePurchaseWithPromotions(
            final Map<String, Integer> productsPurchase, final List<Product> products, final List<Promotion> promotions) {

        Map<String, Integer> regularPurchaseResults = new LinkedHashMap<>();
        Map<String, Integer> promotionResults = new LinkedHashMap<>();
        productsPurchase.forEach((productName, requestedQuantity) -> {
            applyPromotionToPurchase(regularPurchaseResults, promotionResults, productName, requestedQuantity, products, promotions);
        });
        return promotionResults;
    }

    private static void applyPromotionToPurchase(
            final Map<String, Integer> regularPurchaseResults, final Map<String, Integer> promotionResults,
            final String productName, final int requestedQuantity,
            final List<Product> products, final List<Promotion> promotions) {

        Product product = findProductByName(products, productName);
        Promotion promotion = getPromotionIfProductExists(promotions, product);
        if (product != null && promotion != null && PromotionProcessor.isWithinPromotionPeriod(promotion)) {
            processPromotionPurchase(regularPurchaseResults, promotionResults, product, products , requestedQuantity, promotion);
            return;
        }
        processRegularPurchase(regularPurchaseResults, product, requestedQuantity);
    }

    private static Product findProductByName(final List<Product> products, final String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    private static Promotion getPromotionIfProductExists(final List<Promotion> promotions, final Product product) {
        if (product == null) {
            return null;
        }
        return findPromotionByName(promotions, product.getPromotion());
    }

    private static Promotion findPromotionByName(final List<Promotion> promotions, final String promotionName) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName))
                .findFirst()
                .orElse(null);
    }

    private static void processPromotionPurchase(
            final Map<String, Integer> regularPurchaseResults, final Map<String, Integer> promotionResults,
            final Product product, final List<Product> products, final int requestedQuantity, final Promotion promotion) {

        int promotionUnit = calculatePromotionUnit(promotion);
        int applicablePromotionSets = calculateApplicablePromotionSets(requestedQuantity, product, promotionUnit);
        int promotionApplicableQuantity = applicablePromotionSets * (promotion.getBuy() + promotion.getGet());
        int freeUnits = applicablePromotionSets * promotion.getGet();
        applyPromotionQuantities(regularPurchaseResults, promotionResults, product, promotionApplicableQuantity, freeUnits);
        applyRemainingQuantity(regularPurchaseResults, product, products, requestedQuantity, promotionApplicableQuantity);
    }

    private static int calculatePromotionUnit(final Promotion promotion) {
        return promotion.getBuy() + promotion.getGet();
    }

    private static int calculateApplicablePromotionSets(final int requestedQuantity, final Product product, final int promotionUnit) {
        return Math.min(requestedQuantity / promotionUnit, product.getQuantity() / promotionUnit);
    }

    private static void applyPromotionQuantities(
            final Map<String, Integer> regularPurchaseResults, final Map<String, Integer> promotionResults,
            final Product product, final int promotionApplicableQuantity, final int freeUnits) {

        product.decreaseQuantity(promotionApplicableQuantity);
        updateFinalQuantity(regularPurchaseResults, product.getName(), promotionApplicableQuantity);
        if (freeUnits > MINIMUM_PROMOTION_QUANTITY) {
            promotionResults.put(product.getName(), freeUnits);
        }
    }

    private static void applyRemainingQuantity(
            final Map<String, Integer> regularPurchaseResults, final Product product,
            final List<Product> products, final int requestedQuantity, final int promotionApplicableQuantity) {

        int remainingQuantity = requestedQuantity - promotionApplicableQuantity;
        int remainingToDecrease = Math.abs(product.decreaseQuantity(remainingQuantity));
        if (remainingQuantity <= NO_REMAINING_QUANTITY) {
            return;
        }
        Product regularProduct = ProductFinderUtil.findRegularProductByName(products, product.getName());
        regularProduct.decreaseQuantity(remainingToDecrease);
        updateFinalQuantity(regularPurchaseResults, regularProduct.getName(), remainingToDecrease);
    }

    private static void processRegularPurchase(final Map<String, Integer> regularPurchaseResults, final Product product, final int requestedQuantity) {
        int availableQuantity = Math.min(requestedQuantity, product.getQuantity());
        product.decreaseQuantity(availableQuantity);
        updateFinalQuantity(regularPurchaseResults, product.getName(), availableQuantity);
    }

    private static void updateFinalQuantity(final Map<String, Integer> regularPurchaseResults, final String productName, final int quantity) {
        regularPurchaseResults.put(productName, quantity);
    }

}