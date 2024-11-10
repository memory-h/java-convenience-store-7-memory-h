package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PurchaseSummaryListTest {

    @Test
    @DisplayName("구매할 상품의 상품명, 수량, 금액이 리스트에 담겨 있는지 테스트")
    void 구매할_정보가_리스트에_딤겨_있는지_테스트() {
        PurchaseSummary cola = PurchaseSummary.of("콜라", 10, 1000);
        PurchaseSummary Sprite = PurchaseSummary.of("사이다", 8, 1000);
        List<PurchaseSummary> purchaseSummaries = PurchaseSummaryList.from(List.of(cola, Sprite)).getPurchaseSummaries();

        assertThat(purchaseSummaries.size()).isEqualTo(2);
        assertThat(purchaseSummaries).contains(cola, Sprite);
    }

}