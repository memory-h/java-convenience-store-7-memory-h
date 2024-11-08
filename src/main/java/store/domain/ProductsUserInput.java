package store.domain;

import store.utils.Parser;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static store.validator.ParserValidator.validateIndividualProductDelimiter;
import static store.validator.ParserValidator.validateProductDelimiter;
import static store.validator.ProductValidator.validatePositiveQuantity;

public class ProductsUserInput {

    private final Map<String, Integer> productsUserInput;

    private ProductsUserInput(Map<String, Integer> productsUserInput) {
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

    private static String[] splitUserInputByDelimiter(String userInput) {
        validateIndividualProductDelimiter(userInput);
        return Parser.splitByDelimiter(userInput);
    }

    private static Map<String, String> parseProductsWithQuantities(String[] splitByDelimiter) {
        validateProductDelimiter(splitByDelimiter);
        return Parser.splitByHyphen(splitByDelimiter);
    }

    private static Map<String, Integer> convertToProductQuantityMap(Map<String, String> products) {
        Map<String, Integer> productsUserInput = new LinkedHashMap<>();
        products.keySet().forEach(productName -> {
            validatePositiveQuantity(products.get(productName));
            productsUserInput.put(productName, Parser.parseInt(products.get(productName)));
        });
        return productsUserInput;
    }

}