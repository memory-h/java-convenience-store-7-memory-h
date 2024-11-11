package store.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReceiptResult {

    private static final String TOTAL_PURCHASES = "총구매액";
    private static final String PROMOTION_DISCOUNT = "행사할인";
    private static final String MEMBERSHIP_DISCOUNT = "멤버십할인";
    private static final String FINAL_AMOUNT = "내실돈";

    private final Map<String, Integer> receiptData;

    private ReceiptResult(
            final int totalPurchasesReceipt,
            final int promotionDiscountReceipt,
            final int membershipDiscountReceipt,
            final int receiveToMoneyReceipt
    ) {

        this.receiptData = new LinkedHashMap<>();
        receiptData.put(TOTAL_PURCHASES, totalPurchasesReceipt);
        receiptData.put(PROMOTION_DISCOUNT, -promotionDiscountReceipt);
        receiptData.put(MEMBERSHIP_DISCOUNT, -membershipDiscountReceipt);
        receiptData.put(FINAL_AMOUNT, receiveToMoneyReceipt);
    }

    public static ReceiptResult of(
            final int totalPurchasesReceipt,
            final int promotionDiscountReceipt,
            final int membershipDiscountReceipt,
            final int receiveToMoneyReceipt
    ) {
        return new ReceiptResult(
                totalPurchasesReceipt, promotionDiscountReceipt,
                membershipDiscountReceipt, receiveToMoneyReceipt);
    }

    public Map<String, Integer> getReceiptData() {
        return Collections.unmodifiableMap(receiptData);
    }

}