package store.utils.calculator;

import store.domain.conveniencestore.Product;
import store.domain.conveniencestore.Promotion;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.Map;

import static store.common.ViewMessage.NO;
import static store.common.ViewMessage.YES;

public class PromotionEligibilityChecker {

    private static final String NOT_PROMOTION = "null";
    private static final int NO_ADDITIONAL_QUANTITY = 0;

    public static void validateAndPromptAdditionalQuantity(final Map<String, Integer> productsUserInput, final List<Product> products, final List<Promotion> promotions) {
        for (Product product : products) {
            if (!isEligibleForPromotion(productsUserInput, product)) {
                continue;
            }
            processPromotionForProduct(productsUserInput, product, promotions);
        }
    }

    private static boolean isEligibleForPromotion(final Map<String, Integer> productsUserInput, final Product product) {
        return productsUserInput.containsKey(product.getName()) && !product.getPromotion().equals(NOT_PROMOTION);
    }

    private static void processPromotionForProduct(final Map<String, Integer> productsUserInput, final Product product, final List<Promotion> promotions) {
        if (isProductQuantityExceedingStock(productsUserInput, product)) {
            int requiredQuantity = calculateRequiredPromotionQuantity(productsUserInput, product, promotions);
            promptForPromotionConfirmation(productsUserInput, product.getName(), requiredQuantity);
            return;
        }
        handleRemainingPromotionQuantity(productsUserInput, product, promotions);
    }

    private static boolean isProductQuantityExceedingStock(final Map<String, Integer> productsUserInput, final Product product) {
        return productsUserInput.get(product.getName()) >= product.getQuantity();
    }

    private static int calculateRequiredPromotionQuantity(final Map<String, Integer> productsUserInput, final Product product, final List<Promotion> promotions) {
        for (Promotion promotion : promotions) {
            if (product.getPromotion().equals(promotion.getName())) {
                return computeRequiredQuantity(productsUserInput, product, promotion);
            }
        }
        return NO_ADDITIONAL_QUANTITY;
    }

    private static int computeRequiredQuantity(final Map<String, Integer> productsUserInput, final Product product, final Promotion promotion) {
        int promotionTotalQuantity = promotion.getBuy() + promotion.getGet();
        return productsUserInput.get(product.getName()) - (promotionTotalQuantity) * (product.getQuantity() / promotionTotalQuantity);
    }

    private static void promptForPromotionConfirmation(final Map<String, Integer> productsUserInput, final String productName, int needQuantity) {
        try {
            String userInput = InputView.promptForNonPromotionPurchaseConfirmation(productName, needQuantity);
            if (userInput.equals(NO.getMessage())) {
                productsUserInput.put(productName, productsUserInput.get(productName) - needQuantity);
            }
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            promptForPromotionConfirmation(productsUserInput, productName, needQuantity);
        }
    }

    private static void handleRemainingPromotionQuantity(final Map<String, Integer> productsUserInput, final Product product, final List<Promotion> promotions) {
        for (Promotion promotion : promotions) {
            if (!product.getPromotion().equals(promotion.getName())) {
                continue;
            }
            checkAndPromptAdditionalQuantity(productsUserInput, product, promotion);
            break;
        }
    }

    private static void checkAndPromptAdditionalQuantity(final Map<String, Integer> productsUserInput, final Product product, final Promotion promotion) {
        int promotionTotalQuantity = promotion.getBuy() + promotion.getGet();
        int productQuantity = productsUserInput.get(product.getName());
        if (isShortageOfPromotionQuantity(productQuantity, promotionTotalQuantity)) {
            handleShortageQuantity(productsUserInput, product, promotionTotalQuantity, productQuantity);
            return;
        }
        handleRemainingPromotionQuantity(productsUserInput, product, promotionTotalQuantity, productQuantity);
    }

    private static boolean isShortageOfPromotionQuantity(int productQuantity, int promotionTotalQuantity) {
        return productQuantity < promotionTotalQuantity;
    }

    private static void handleShortageQuantity(final Map<String, Integer> productsUserInput, final Product product, int promotionTotalQuantity, int productQuantity) {
        int requiredAdditionalQuantity = promotionTotalQuantity - productQuantity;
        promptForAdditionalQuantity(productsUserInput, product.getName(), requiredAdditionalQuantity);
    }

    private static void handleRemainingPromotionQuantity(final Map<String, Integer> productsUserInput, final Product product, int promotionTotalQuantity, int productQuantity) {
        int needQuantity = productQuantity % promotionTotalQuantity;
        if (needQuantity > NO_ADDITIONAL_QUANTITY) {
            promptForAdditionalQuantity(productsUserInput, product.getName(), needQuantity);
        }
    }

    private static void promptForAdditionalQuantity(final Map<String, Integer> productsUserInput, final String productName, int requiredAdditionalQuantity) {
        try {
            String userInput = InputView.promptForAdditionalQuantity(productName, requiredAdditionalQuantity);
            if (userInput.equals(YES.getMessage())) {
                productsUserInput.put(productName, productsUserInput.get(productName) + requiredAdditionalQuantity);
            }
        } catch (IllegalArgumentException e) {
            OutputView.printMessage(e.getMessage());
            promptForAdditionalQuantity(productsUserInput, productName, requiredAdditionalQuantity);
        }
    }

}