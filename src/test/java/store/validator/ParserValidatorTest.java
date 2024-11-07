package store.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParserValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "[콜라-3];[에너지바-5];[사이다-6]",
            "[콜라-3].[에너지바-5].[사이다-6]",
            "[콜라-3]:[에너지바-5]:[사이다-6]",
            "[콜라-3],[에너지바-5]:[사이다-6]"
    })
    @DisplayName("개별 상품이 대괄호와 쉼표로 구분되지 않으면 예외가 발생한다.")
    void 개별_상품이_대괄호와_쉼표로_구분되지_않으면_예외_발생(String condition) {
        assertThatThrownBy(() -> ParserValidator.validateIndividualProductDelimiter(condition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 개별 상품은 대괄호와 쉼표로 구분되어야 합니다.");
    }

}