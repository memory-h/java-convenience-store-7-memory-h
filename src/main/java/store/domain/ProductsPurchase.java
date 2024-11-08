package store.domain;

import java.util.*;
import java.util.stream.Collectors;

public class ProductsPurchase {

    private final Map<String, Integer> productsPurchase;

    private ProductsPurchase(Map<String, Integer> productsPurchase) {
        this.productsPurchase = productsPurchase;
    }

    public static ProductsPurchase of(List<Product> products, Map<String, Integer> productsUserInput) {
        Validator.validateProductName(products, productsUserInput);
        return new ProductsPurchase(new LinkedHashMap<>());
    }

    public Map<String, Integer> getProductsPurchase() {
        return Collections.unmodifiableMap(productsPurchase);
    }

    private static class Validator {
        private static void validateProductName(List<Product> products, Map<String, Integer> productsUserInput) {
            Set<String> productNames = products.stream()
                    .map(Product::getName)
                    .collect(Collectors.toSet());
            productsUserInput.keySet().stream()
                    .filter(inputName -> !productNames.contains(inputName))
                    .forEach(inputName -> {
                        throw new IllegalArgumentException("[ERROR] 편의점에 해당 상품은 존재하지 않습니다.");
                    });
        }
    }

}