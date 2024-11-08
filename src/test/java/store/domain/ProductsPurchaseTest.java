package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductsPurchaseTest {

    @Test
    @DisplayName("편의점에 없는 상품명을 입력하면 예외가 발생한다.")
    void 편의점에_없는_상품명을_입력하면_예외_발생() {
        List<Product> products = List.of(Product.of("사이다", 1000, 10, true));
        Map<String, Integer> productsUserInput = new LinkedHashMap<>();
        productsUserInput.put("콜라", 5);
        assertThatThrownBy(() -> ProductsPurchase.of(products, productsUserInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 편의점에 해당 상품은 존재하지 않습니다.");
    }

}