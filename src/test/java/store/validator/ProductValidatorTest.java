package store.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.utils.Parser;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "[사이다-0]",
            "[탄산수--1]"
    })
    @DisplayName("상품 수량이 0보다 큰 수가 입력되었는지 테스트 한다.")
    void 수량이_0보다_큰_수인지_테스트(String condition) {
        String[] splitProducts = Parser.splitByDelimiter(condition);
        Map<String, String> products = Parser.splitByHyphen(splitProducts);

        for (String productName : products.keySet()) {
            assertThatThrownBy(() -> ProductValidator.validatePositiveQuantity(products.get(productName)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 수량은 0보다 큰 정수여야 합니다.");
        }
    }

}