package store.validator;

public class InputViewValidator {

    public static void validateUserInputIsNotEmpty(final String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 상품명과 수량은 비어있을 수 없습니다.");
        }
    }

}