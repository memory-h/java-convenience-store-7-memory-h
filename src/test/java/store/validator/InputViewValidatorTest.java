package store.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputViewValidatorTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"\n", "\t"})
    @DisplayName("상품명과 수량이 비어있으면 예외가 발생한다.")
    void 상품명과_수량이_비어있으면_예외_발생(String condition) {
        assertThatThrownBy(() -> InputViewValidator.validateUserInputIsNotEmpty(condition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품명과 수량을 비어있을 수 없습니다.");
    }

}