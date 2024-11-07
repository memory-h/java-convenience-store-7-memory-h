package store.view;

public class InputView {

    private static final String DELIMITER_REGEX = "^\\[[^\\]]+\\](,\\[[^\\]]+\\])*$";

    public static String readProducts(final String userInput) {
        Validator.validateDelimiter(userInput);
        return userInput;
    }

    public static String readUserInput(final String userInput) {
        Validator.validateUserInputIsNotEmpty(userInput);
        return userInput;
    }

    private static class Validator {

        private static void validateUserInputIsNotEmpty(final String userInput) {
            if (userInput == null || userInput.isBlank()) {
                throw new IllegalArgumentException("[ERROR] 상품명과 수량을 비어있을 수 없습니다.");
            }
        }

        private static void validateDelimiter(final String userInput) {
            if (!userInput.matches(DELIMITER_REGEX)) {
                throw new IllegalArgumentException("[ERROR] 개별 상품은 대괄호와 쉼표로 구분되어야 합니다.");
            }
        }

    }

}