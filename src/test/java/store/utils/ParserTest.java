package store.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.validator.InputViewValidator;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    @Test
    @DisplayName("개별 상품이 쉼표로 구분되는지 테스트한다.")
    void 개별_상품을_쉼표로_구분하는_테스트() {
        String userInput = "[콜라-3],[에너지바-5]";

        String[] splitUserInput = Parser.splitByDelimiter(userInput);

        assertThat(splitUserInput).hasSize(2);
        assertThat(splitUserInput).contains("[콜라-3]", "[에너지바-5]");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[콜라-3],[에너지바-5],[사이다-6]",
            "[콜라-3],[탄산수-2],[초코바-1],[오렌지주스-10]"
    })
    @DisplayName("상품명과 수량을 하이픈으로 구분되는지 테스트한다.")
    void 상품명과_수량은_하이픈으로_구분한다(String condition) {
        InputViewValidator.validateUserInputIsNotEmpty(condition);
        String[] splitProducts = Parser.splitByDelimiter(condition);
        Map<String, String> products = Parser.splitByHyphen(splitProducts);

        assertThat(products).hasSize(products.size());
        products.keySet()
                .forEach(productName -> assertThat(products.get(productName))
                        .isEqualTo(products.get(productName)));
    }

}