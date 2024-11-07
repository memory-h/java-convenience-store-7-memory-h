package store.validator;

public class ParserValidator {

    private static final String INDIVIDUAL_PRODUCT_DELIMITER_REGEX = "^\\[[^\\]]+\\](,\\[[^\\]]+\\])*$";
    private static final String PRODUCT_AND_QUANTITY_DELIMITER_REGEX = "[가-힣a-zA-Z]+-[가-힣a-zA-Z]";

    public static void validateIndividualProductDelimiter(final String userInput) {
        if (!userInput.matches(INDIVIDUAL_PRODUCT_DELIMITER_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 개별 상품은 대괄호와 쉼표로 구분되어야 합니다.");
        }
    }

    public static void validateProductDelimiter(final String[] splitProducts) {
        for (String splitProduct : splitProducts) {
            if (!splitProduct.matches(PRODUCT_AND_QUANTITY_DELIMITER_REGEX)) {
                throw new IllegalArgumentException("[ERROR] 상품명과 수량은 하이픈으로 구분되어야 합니다.");
            }
        }
    }

}