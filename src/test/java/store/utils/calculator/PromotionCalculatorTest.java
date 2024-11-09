package store.utils.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.conveniencestore.Product;
import store.domain.conveniencestore.Promotion;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionCalculatorTest {

    private List<Product> products;
    private List<Promotion> promotions;

    @BeforeEach
    void setUp() {
        products = List.of(
                Product.of("콜라", 1000, 10, "탄산2+1"),
                Product.of("콜라", 1000, 10, null),
                Product.of("물", 500, 10, null),
                Product.of("오렌지주스", 1800, 9, "MD추천상품"),
                Product.of("감자칩", 1500, 5, "반짝할인")
        );
        promotions = List.of(
                Promotion.of("탄산2+1", 2, 1, "2024-01-01", "2024-12-31"),
                Promotion.of("MD추천상품", 1, 1, "2024-01-01", "2024-12-31"),
                Promotion.of("반짝할인", 1, 1, "2024-11-01", "2024-11-30")
        );
    }

    @Test
    @DisplayName("프로모션이 적용된 경우 각 제품의 최종 구매 수량을 검증")
    void 프로모션이_적용된_경우_각_제품의_최종_구매_수량을_검증() {
        Map<String, Integer> userRequests = new LinkedHashMap<>();
        userRequests.put("콜라", 4);
        userRequests.put("물", 3);
        userRequests.put("오렌지주스", 5);

        Map<String, Integer> appliedPromotions = PromotionCalculator.calculatePromotionPurchase(userRequests, products, promotions);

        // TODO 커밋 전 삭제
        for (Product product : products) {
            System.out.println("product.getName() = " + product.getName());
            System.out.println("product.getQuantity() = " + product.getQuantity());
        }

        assertThat(appliedPromotions.get("콜라")).isEqualTo(2);
        assertThat(appliedPromotions.get("오렌지주스")).isEqualTo(5);
    }

    @Test
    @DisplayName("프로모션이 적용되지 않은 경우 일반 재고로만 구매")
    void 프로모션이_적용되지_않은_경우_일반_재고로만_구매() {
        Map<String, Integer> userRequests = new LinkedHashMap<>();
        userRequests.put("물", 3);

        Map<String, Integer> appliedPromotions = PromotionCalculator.calculatePromotionPurchase(userRequests, products, promotions);

        assertThat(appliedPromotions.get("물")).isNull();
    }

    @Test
    @DisplayName("프로모션 재고가 부족한 경우 일반 재고로 충당하여 구매")
    void 프로모션_재고가_부족한_경우_일반_재고로_충당하여_구매() {
        Map<String, Integer> userRequests = new LinkedHashMap<>();
        userRequests.put("콜라", 6);

        Map<String, Integer> appliedPromotions = PromotionCalculator.calculatePromotionPurchase(userRequests, products, promotions);

        assertThat(appliedPromotions.get("콜라")).isEqualTo(3);
    }

    @Test
    @DisplayName("프로모션이 없는 제품의 일반 구매를 검증")
    void 프로모션이_없는_제품의_일반_구매를_검증() {
        Map<String, Integer> userRequests = new LinkedHashMap<>();
        userRequests.put("물", 2);

        Map<String, Integer> appliedPromotions = PromotionCalculator.calculatePromotionPurchase(userRequests, products, promotions);

        assertThat(appliedPromotions.get("물")).isNull();
    }

}