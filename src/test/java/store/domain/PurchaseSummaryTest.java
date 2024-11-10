package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PurchaseSummaryTest {

    @Test
    @DisplayName("구매할 상품의 상품명, 수량, 금액이 있는지 테스트")
    void 구매할_상품_정보가_있는지_테스트() {
        PurchaseSummary purchaseSummary = PurchaseSummary.of("콜라", 10, 1000);

        assertThat(purchaseSummary.getProductName()).isEqualTo("콜라");
        assertThat(purchaseSummary.getQuantity()).isEqualTo(10);
        assertThat(purchaseSummary.getPrice()).isEqualTo(1000);
    }

}