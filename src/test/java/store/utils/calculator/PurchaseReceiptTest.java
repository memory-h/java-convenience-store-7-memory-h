package store.utils.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.conveniencestore.Product;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class PurchaseReceiptTest {

    private Map<String, Integer> productsPurchase;
    private Map<String, Integer> promotionResult;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        productsPurchase = new LinkedHashMap<>();
        productsPurchase.put("콜라", 3);
        productsPurchase.put("사이다", 2);
        productsPurchase.put("물", 1);

        products = List.of(
                Product.of("콜라", 1000, 10, "탄산2+1"),
                Product.of("사이다", 1000, 5, "탄산2+1"),
                Product.of("물", 500, 10, "null"),
                Product.of("오렌지주스", 1800, 9, "MD추천상품")
        );
        promotionResult = new LinkedHashMap<>();
        promotionResult.put("콜라", 1);
        promotionResult.put("사이다", 1);
    }

    @Test
    @DisplayName("총 구매 금액 계산")
    void 총_구매_금액_계산() {
        int totalPurchases = PurchaseReceipt.calculateTotalPurchases(productsPurchase, products);
        assertThat(totalPurchases).isEqualTo(5500);
    }

    @Test
    @DisplayName("프로모션 할인 금액 계산")
    void 프로모션_할인_금액_계산() {
        int promotionDiscount = PurchaseReceipt.calculatePromotionDiscount(promotionResult, products);
        assertThat(promotionDiscount).isEqualTo(2000);
    }

    @Test
    @DisplayName("멤버십 할인 금액 계산")
    void 멤버십_할인_금액_계산() {
        int membershipDiscount = PurchaseReceipt.calculateMembershipDiscount(productsPurchase, products);
        assertThat(membershipDiscount).isEqualTo(150);
    }

    @Test
    @DisplayName("최종 결제 금액 계산")
    void 최종_결제_금액_계산() {
        int totalPurchases = PurchaseReceipt.calculateTotalPurchases(productsPurchase, products);
        int promotionDiscount = PurchaseReceipt.calculatePromotionDiscount(promotionResult, products);
        int membershipDiscount = PurchaseReceipt.calculateMembershipDiscount(productsPurchase, products);

        int finalAmount = PurchaseReceipt.calculateFinalAmount(totalPurchases, promotionDiscount, membershipDiscount);

        int expectedFinalAmount = totalPurchases - (promotionDiscount + membershipDiscount);
        assertThat(finalAmount).isEqualTo(expectedFinalAmount);
    }

}