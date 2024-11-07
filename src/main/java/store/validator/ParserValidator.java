package store.validator;

public class ParserValidator {

    private static final String INDIVIDUAL_PRODUCT_DELIMITER_REGEX = "^\\[[^\\]]+\\](,\\[[^\\]]+\\])*$";

    public static void validateIndividualProductDelimiter(final String userInput) {
        if (!userInput.matches(INDIVIDUAL_PRODUCT_DELIMITER_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 개별 상품은 대괄호와 쉼표로 구분되어야 합니다.");
        }
    }

}