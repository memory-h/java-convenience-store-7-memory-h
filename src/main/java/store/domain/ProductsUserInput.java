package store.domain;

import store.utils.Parser;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductsUserInput {

    private final Map<String, Integer> productsUserInput;

    private ProductsUserInput(final Map<String, Integer> productsUserInput) {
        this.productsUserInput = productsUserInput;
    }

    public static ProductsUserInput from(String userInput) {
        String[] splitByDelimiter = splitUserInputByDelimiter(userInput);
        Map<String, String> products = parseProductsWithQuantities(splitByDelimiter);
        Map<String, Integer> productsUserInput = convertToProductQuantityMap(products);
        return new ProductsUserInput(productsUserInput);
    }

    public Map<String, Integer> getProductsUserInput() {
        return Collections.unmodifiableMap(productsUserInput);
    }

    private static String[] splitUserInputByDelimiter(final String userInput) {
        Validator.validateIndividualProductDelimiter(userInput);
        return Parser.splitByDelimiter(userInput);
    }

    private static Map<String, String> parseProductsWithQuantities(final String[] splitByDelimiter) {
        Validator.validateProductDelimiter(splitByDelimiter);
        return Parser.splitByHyphen(splitByDelimiter);
    }

    private static Map<String, Integer> convertToProductQuantityMap(final Map<String, String> products) {
        Map<String, Integer> productsUserInput = new LinkedHashMap<>();
        products.keySet().forEach(productName -> {
            Validator.validatePositiveQuantity(products.get(productName));
            productsUserInput.put(productName, Parser.parseInt(products.get(productName)));
        });
        return productsUserInput;
    }

    private static class Validator {

        private static final String INDIVIDUAL_PRODUCT_DELIMITER_REGEX = "^\\[[^\\]]+\\](,\\[[^\\]]+\\])*$";
        private static final String PRODUCT_AND_QUANTITY_DELIMITER_REGEX = "[가-힣a-zA-Z]+-[0-9]+";
        private static final String POSITIVE_INTEGER_PATTERN = "^[1-9]\\d*$";

        private static void validateIndividualProductDelimiter(final String userInput) {
            if (!userInput.matches(INDIVIDUAL_PRODUCT_DELIMITER_REGEX)) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
            }
        }

        private static void validateProductDelimiter(final String[] splitProducts) {
            for (String splitProduct : splitProducts) {
                if (!splitProduct.matches(PRODUCT_AND_QUANTITY_DELIMITER_REGEX)) {
                    throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
                }
            }
        }

        private static void validatePositiveQuantity(final String quantity) {
            if (!quantity.matches(POSITIVE_INTEGER_PATTERN)) {
                throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
            }
        }

    }

}