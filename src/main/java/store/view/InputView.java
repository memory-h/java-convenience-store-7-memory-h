package store.view;

public class InputView {

    private static final String DELIMITER_REGEX = "^\\[[^\\]]+\\](,\\[[^\\]]+\\])*$";

    public static String readProducts(String userInput) {
        Validator.validateDelimiter(userInput);
        return userInput;
    }

    private static class Validator {

        private static void validateDelimiter(String userInput) {
            if (!userInput.matches(DELIMITER_REGEX)) {
                throw new IllegalArgumentException("[ERROR] 개별 상품은 대괄호와 쉼표로 구분되어야 합니다.");
            }
        }

    }

}