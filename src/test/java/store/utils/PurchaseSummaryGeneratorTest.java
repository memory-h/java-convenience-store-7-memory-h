package store.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.PurchaseSummary;
import store.domain.conveniencestore.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class PurchaseSummaryGeneratorTest {

    private Map<String, Integer> productsPurchase;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        productsPurchase = new LinkedHashMap<>();
        productsPurchase.put("콜라", 3);
        productsPurchase.put("물", 2);

        products = List.of(
                Product.of("콜라", 1000, 10, "탄산2+1"),
                Product.of("콜라", 1000, 10, "null"),
                Product.of("물", 500, 10, "null"),
                Product.of("오렌지주스", 1800, 9, "MD추천상품"),
                Product.of("감자칩", 1500, 5, "반짝할인")
        );
    }

    @Test
    @DisplayName("정상적인 구매 요약 리스트 생성")
    void 정상적인_구매_요약_리스트_생성() {
        List<PurchaseSummary> purchaseSummaries = PurchaseSummaryGenerator.generateSummaryList(productsPurchase, products)
                .getPurchaseSummaries();
        assertThat(purchaseSummaries.size()).isEqualTo(2);

        PurchaseSummary colaSummary = purchaseSummaries.getFirst();
        assertThat(colaSummary.getProductName()).isEqualTo("콜라");
        assertThat(colaSummary.getPrice()).isEqualTo(3000);
        assertThat(colaSummary.getQuantity()).isEqualTo(3);

        PurchaseSummary ciderSummary = purchaseSummaries.get(1);
        assertThat(ciderSummary.getProductName()).isEqualTo("물");
        assertThat(ciderSummary.getPrice()).isEqualTo(1000);
        assertThat(ciderSummary.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("일치하는 제품이 없을 때 빈 구매 요약 리스트 반환")
    void 일치하는_제품이_없을_때_빈_구매_요약_리스트_반환() {
        productsPurchase.clear();
        productsPurchase.put("없는상품", 1);

        List<PurchaseSummary> purchaseSummaries = PurchaseSummaryGenerator.generateSummaryList(productsPurchase, products)
                .getPurchaseSummaries();

        assertNotNull(purchaseSummaries);
        assertEquals(0, purchaseSummaries.size());
    }

}