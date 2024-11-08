package store.domain;

import java.util.*;
import java.util.stream.Collectors;

public class ProductsPurchase {

    private final Map<String, Integer> productsPurchase;

    private ProductsPurchase(Map<String, Integer> productsPurchase) {
        this.productsPurchase = productsPurchase;
    }

    public static ProductsPurchase of(List<Product> products, Map<String, Integer> productsUserInput) {
        Validator.validateProductNameAndQuantity(products, productsUserInput);
        return new ProductsPurchase(new LinkedHashMap<>());
    }

    public Map<String, Integer> getProductsPurchase() {
        return Collections.unmodifiableMap(productsPurchase);
    }

    private static class Validator {

        private static void validateProductNameAndQuantity(List<Product> products, Map<String, Integer> productsUserInput) {
            Map<String, Integer> productStockMap = products.stream()
                    .collect(Collectors.toMap(Product::getName, Product::getQuantity, Integer::sum, LinkedHashMap::new));
            productsUserInput.forEach(
                    (inputName, inputQuantity) -> validateProductAvailability(inputName, inputQuantity, productStockMap));
        }

        private static void validateProductAvailability(String inputName, int inputQuantity, Map<String, Integer> productStockMap) {
            validateProductExists(inputName, productStockMap);
            validateProductQuantity(inputName, inputQuantity, productStockMap);
        }

        private static void validateProductExists(String inputName, Map<String, Integer> productStockMap) {
            if (!productStockMap.containsKey(inputName)) {
                throw new IllegalArgumentException("[ERROR] 편의점에 해당 상품은 존재하지 않습니다.");
            }
        }

        private static void validateProductQuantity(String inputName, int inputQuantity, Map<String, Integer> productStockMap) {
            int availableQuantity = productStockMap.get(inputName);
            if (inputQuantity > availableQuantity) {
                throw new IllegalArgumentException("[ERROR] 편의점이 보유한 수량보다 많이 구매할 수 없습니다.");
            }
        }

    }

}