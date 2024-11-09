package store.utils.calculator;

import store.domain.conveniencestore.Product;
import store.domain.conveniencestore.Promotion;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PromotionCalculator {

    private static final int MIN_PROMOTION_QUANTITY = 0;

    private PromotionCalculator() {
    }

    public static Map<String, Integer> calculatePromotionPurchase(
            final Map<String, Integer> productsPurchase1, final List<Product> products, final List<Promotion> promotions) {

        Map<String, Integer> resultMap = new LinkedHashMap<>();
        Map<String, Integer> appliedPromotions = new LinkedHashMap<>();

        productsPurchase1.forEach((productName, requestedQuantity) -> {
            processPurchase(resultMap, appliedPromotions, productName, requestedQuantity, products, promotions);
        });
        return appliedPromotions;
    }

    private static void processPurchase(
            final Map<String, Integer> resultMap, final Map<String, Integer> appliedPromotions,
            final String productName, final int requestedQuantity,
            final List<Product> products, final List<Promotion> promotions) {

        Product product = findProductByName(products, productName);
        Promotion promotion = findPromotionByName(promotions, product != null ? product.getPromotion() : null);

        if (product != null && promotion != null && PromotionProcessor.isWithinPromotionPeriod(promotion)) {
            handlePromotionPurchase(resultMap, appliedPromotions, product, requestedQuantity, promotion);
            return;
        }
        handleRegularPurchase(resultMap, product, requestedQuantity);
    }

    private static Product findProductByName(final List<Product> products, final String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    private static Promotion findPromotionByName(final List<Promotion> promotions, final String promotionName) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName))
                .findFirst()
                .orElse(null);
    }

    private static void handlePromotionPurchase(
            final Map<String, Integer> resultMap, final Map<String, Integer> appliedPromotions,
            final Product product, final int requestedQuantity, final Promotion promotion) {

        int promotionUnitsToGive = calculatePromotionUnits(requestedQuantity, promotion);

        if (promotionUnitsToGive > MIN_PROMOTION_QUANTITY) {
            appliedPromotions.put(product.getName(), promotionUnitsToGive);
        }
        product.decreaseQuantity(requestedQuantity);
        updateFinalQuantity(resultMap, product.getName(), requestedQuantity);
    }

    private static int calculatePromotionUnits(final int requestedQuantity, final Promotion promotion) {
        int buyQuantity = promotion.getBuy();
        int getQuantity = promotion.getGet();
        int applicablePromotionSets = requestedQuantity / buyQuantity;
        return applicablePromotionSets * getQuantity;
    }

    private static void handleRegularPurchase(final Map<String, Integer> resultMap, final Product product, final int requestedQuantity) {
        int availableQuantity = Math.min(requestedQuantity, product.getQuantity());
        product.decreaseQuantity(availableQuantity);
        updateFinalQuantity(resultMap, product.getName(), availableQuantity);
    }

    private static void updateFinalQuantity(final Map<String, Integer> resultMap, final String productName, final int quantity) {
        resultMap.put(productName, quantity);
    }

}