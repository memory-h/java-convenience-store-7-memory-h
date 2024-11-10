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

    public static Map<String, Integer> calculatePurchaseWithPromotions(
            final Map<String, Integer> purchaseRequests, final List<Product> products, final List<Promotion> promotions) {

        Map<String, Integer> regularPurchaseResults = new LinkedHashMap<>();
        Map<String, Integer> promotionResults = new LinkedHashMap<>();

        purchaseRequests.forEach((productName, requestedQuantity) -> {
            applyPromotionToPurchase(regularPurchaseResults, promotionResults, productName, requestedQuantity, products, promotions);
        });
        return promotionResults;
    }

    private static void applyPromotionToPurchase(
            final Map<String, Integer> regularPurchaseResults, final Map<String, Integer> promotionResults,
            final String productName, final int requestedQuantity,
            final List<Product> products, final List<Promotion> promotions) {

        Product product = findProductByName(products, productName);
        Promotion promotion = findPromotionByName(promotions, product != null ? product.getPromotion() : null);

        if (product != null && promotion != null && PromotionProcessor.isWithinPromotionPeriod(promotion)) {
            processPromotionPurchase(regularPurchaseResults, promotionResults, product, requestedQuantity, promotion);
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

    private static Promotion findPromotionByName(final List<Promotion> promotions, final String promotionName) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName))
                .findFirst()
                .orElse(null);
    }

    private static void processPromotionPurchase(
            final Map<String, Integer> regularPurchaseResults, final Map<String, Integer> promotionResults,
            final Product product, final int requestedQuantity, final Promotion promotion) {

        int freeUnits = calculateFreeUnitsForPromotion(requestedQuantity, promotion);

        if (freeUnits > MIN_PROMOTION_QUANTITY) {
            promotionResults.put(product.getName(), freeUnits);
        }
        product.decreaseQuantity(requestedQuantity);
        updateFinalQuantity(regularPurchaseResults, product.getName(), requestedQuantity);
    }

    private static int calculateFreeUnitsForPromotion(final int requestedQuantity, final Promotion promotion) {
        int buyQuantity = promotion.getBuy();
        int getQuantity = promotion.getGet();
        int applicablePromotionSets = requestedQuantity / buyQuantity;
        return applicablePromotionSets * getQuantity;
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