package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductsUserInputTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "[콜라-3];[에너지바-5];[사이다-6]",
            "[콜라-3].[에너지바-5].[사이다-6]",
            "[콜라-3]:[에너지바-5]:[사이다-6]",
            "[콜라-3],[에너지바-5]:[사이다-6]"
    })
    @DisplayName("개별 상품이 대괄호와 쉼표로 구분되지 않으면 예외가 발생한다.")
    void 개별_상품이_대괄호와_쉼표로_구분되지_않으면_예외_발생(String condition) {
        assertThatThrownBy(() -> ProductsUserInput.from(condition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 개별 상품은 대괄호와 쉼표로 구분되어야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[콜라;3],[에너지바-5],[사이다-6]",
            "[콜라-3],[탄산수:2],[초코바-1],[오렌지주스,10]"
    })
    @DisplayName("상품명과 수량이 하이픈으로 구분되지 않으면 예외가 발생한다.")
    void 상품명과_수량이_하이픈으로_구분되지_않으면_예외_발생(String condition) {
        assertThatThrownBy(() -> ProductsUserInput.from(condition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품명과 수량은 하이픈으로 구분되어야 합니다.");
    }

    @Test
    @DisplayName("상품 수량이 0보다 큰 수가 입력되었는지 테스트 한다.")
    void 수량이_0보다_큰_수인지_테스트() {
        assertThatThrownBy(() -> ProductsUserInput.from("[사이다-0]"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 수량은 0보다 큰 정수여야 합니다.");
    }

}