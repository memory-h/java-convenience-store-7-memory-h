package store.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    @Test
    @DisplayName("개별 상품이 쉼표로 구분되는지 테스트한다.")
    void 개별_상품을_쉼표로_구분하는_테스트() {
        String userInput = "[콜라-3],[에너지바-5]";

        String[] splitUserInput = userInput.split(",");

        assertThat(splitUserInput).hasSize(2);
        assertThat(splitUserInput).contains("[콜라-3]", "[에너지바-5]");
    }

}