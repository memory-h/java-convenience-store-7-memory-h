package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.utils.calculator.PromotionProcessor;
import store.domain.conveniencestore.Product;
import store.domain.conveniencestore.Promotion;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionProcessorTest {

    @Test
    @DisplayName("구매할 상품이 프로모션 여부를 확인하는 테스트")
    void 구매할_상품이_프로모션에_해당() {
        List<Product> products = List.of(Product.of("콜라", 1000, 10, "탄산2+1"));

        boolean result = PromotionProcessor.isPromotion(products, "콜라");

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("오늘 날짜가 프로모션 기간 내에 포함 여부를 확인하는 테스트")
    void 오늘_날짜가_프로모션_기간_내에_포함() {
        List<Product> products = List.of(Product.of("콜라", 1000, 10, "탄산2+1"));
        List<Promotion> promotions = List.of(Promotion.of("탄산2+1", 2, 1, "2024-01-01", "2024-12-31"));

        boolean result = PromotionProcessor.isPromotion(products, "콜라");
        assertThat(result).isTrue();

        assertThat(PromotionProcessor.isWithinPromotionPeriod(promotions.getFirst()))
                .isTrue();
    }

}