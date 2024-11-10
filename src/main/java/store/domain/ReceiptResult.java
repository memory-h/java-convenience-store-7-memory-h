package store.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReceiptResult {

    private final Map<String, Integer> receiptData;

    private ReceiptResult(
            final int totalPurchasesReceipt,
            final int promotionDiscountReceipt,
            final int membershipDiscountReceipt,
            final int receiveToMoneyReceipt
    ) {

        this.receiptData = new LinkedHashMap<>();
        receiptData.put("총구매액", totalPurchasesReceipt);
        receiptData.put("행사할인", -promotionDiscountReceipt);
        receiptData.put("멤버십할인", -membershipDiscountReceipt);
        receiptData.put("내실돈", receiveToMoneyReceipt);
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