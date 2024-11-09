package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.conveniencestore.Product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductsPurchaseTest {

    @Test
    @DisplayName("편의점에 없는 상품명을 입력하면 예외가 발생한다.")
    void 편의점에_없는_상품명을_입력하면_예외_발생() {
        List<Product> products = List.of(Product.of("사이다", 1000, 10, "탄산2+1"));
        Map<String, Integer> productsUserInput = new LinkedHashMap<>();
        productsUserInput.put("콜라", 5);
        assertThatThrownBy(() -> ProductsPurchase.of(products, productsUserInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }

    @Test
    @DisplayName("편의점이 보유한 수량보다 많이 입력하면 예외가 발생한다.")
    void 편의점이_보유한_수량보다_많이_입력하면_예외_발생() {
        List<Product> products = List.of(Product.of("콜라", 1000, 10, "탄산2+1"));
        Map<String, Integer> productsUserInput = new LinkedHashMap<>();
        productsUserInput.put("콜라", 20);
        assertThatThrownBy(() -> ProductsPurchase.of(products, productsUserInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

}