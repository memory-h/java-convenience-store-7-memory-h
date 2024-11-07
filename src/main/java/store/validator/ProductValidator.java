package store.validator;

public class ProductValidator {

    private static final String POSITIVE_INTEGER_PATTERN = "^[1-9]\\d*$";

    public static void validatePositiveQuantity(final String quantity) {
        if (!quantity.matches(POSITIVE_INTEGER_PATTERN)) {
            throw new IllegalArgumentException("[ERROR] 수량은 0보다 큰 정수여야 합니다.");
        }
    }

}