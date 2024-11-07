package store.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {

    private static final String COMMA_DELIMITER = ",";
    private static final String HYPHEN_DELIMITER = "-";
    private static final String BRACKETS_PATTERN = "[\\[\\]]";
    private static final String EMPTY_STRING = "";
    private static final int PRODUCT_NAME_INDEX = 0;
    private static final int PRODUCT_QUANTITY_INDEX = 1;

    public static String[] splitByDelimiter(final String userInput) {
        return userInput.replaceAll(BRACKETS_PATTERN, EMPTY_STRING).split(COMMA_DELIMITER);
    }

    public static Map<String, String> splitByHyphen(final String[] splitProducts) {
        final Map<String, String> products = new LinkedHashMap<>();
        Arrays.stream(splitProducts)
                .forEach(product -> {
                    String[] productNameAndQuantity = product.split(HYPHEN_DELIMITER);
                    products.put(productNameAndQuantity[PRODUCT_NAME_INDEX], productNameAndQuantity[PRODUCT_QUANTITY_INDEX]);
                });
        return products;
    }

}