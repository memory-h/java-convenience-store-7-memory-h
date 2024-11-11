package store.view;

import store.domain.PurchaseSummary;

import java.util.List;
import java.util.Map;

import static store.common.ViewMessage.*;

public class OutputView {

    private static final String TOTAL_PURCHASE_LABEL = "총구매액";
    private static final String FINAL_AMOUNT_LABEL = "내실돈";
    private static final String ZERO_AMOUNT_DISPLAY = "-0";
    private static final String AMOUNT_FORMAT = "%,d";
    private static final int ZERO_AMOUNT = 0;

    public static void printMessage(final String message) {
        System.out.println(message);
    }

    public static void printEmptyLine() {
        System.out.println();
    }

    public static void startMessage(final String message) {
        System.out.printf(STORE_START_MESSAGE.getMessage() + message);
    }

    public static void receiptMessage(
            final Map<String, Integer> receiptData,
            final Map<String, Integer> promotionResult,
            final List<PurchaseSummary> purchaseSummaries
    ) {
        printHeader(purchaseSummaries);
        printPromotions(promotionResult);
        System.out.println(SEPARATOR.getMessage());
        printFooter(receiptData, getTotalQuantity(purchaseSummaries));
    }

    private static void printHeader(final List<PurchaseSummary> purchaseSummaries) {
        System.out.println(LINE_SEPARATOR.getMessage());
        System.out.println(ITEM_MENU.getMessage());
        for (PurchaseSummary purchaseSummary : purchaseSummaries) {
            System.out.printf(ITEM_FORMAT.getMessage(),
                    purchaseSummary.getProductName(),
                    purchaseSummary.getQuantity(),
                    purchaseSummary.getPrice());
        }
    }

    private static void printPromotions(final Map<String, Integer> promotionResult) {
        System.out.println(PROMOTION_SEPARATOR.getMessage());
        for (Map.Entry<String, Integer> entry : promotionResult.entrySet()) {
            System.out.printf(PROMOTION_FORMAT.getMessage(), entry.getKey(), entry.getValue());
        }
    }

    private static void printFooter(final Map<String, Integer> receiptData, final int totalQuantity) {
        receiptData.forEach((label, amount) -> {
            if (printSpecialLabels(totalQuantity, label, amount)) {
                return;
            }
            System.out.printf(FOOTER_FORMAT.getMessage(), label, formatAmount(amount));
        });
    }

    private static boolean printSpecialLabels(int totalQuantity, String label, Integer amount) {
        if (label.equals(TOTAL_PURCHASE_LABEL)) {
            System.out.printf(TOTAL_FORMAT.getMessage(), label, totalQuantity, amount);
            return true;
        }
        if (label.equals(FINAL_AMOUNT_LABEL)) {
            System.out.printf(FINAL_AMOUNT_FORMAT.getMessage(), label, amount);
            return true;
        }
        return false;
    }

    private static int getTotalQuantity(final List<PurchaseSummary> purchaseSummaries) {
        return purchaseSummaries.stream()
                .mapToInt(PurchaseSummary::getQuantity)
                .sum();
    }

    private static String formatAmount(int amount) {
        if (amount == ZERO_AMOUNT) {
            return ZERO_AMOUNT_DISPLAY;
        }
        return String.format(AMOUNT_FORMAT, amount);
    }

}