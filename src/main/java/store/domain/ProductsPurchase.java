package store.domain;

import store.domain.conveniencestore.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductsPurchase {

    private final Map<String, Integer> productsPurchase;

    private ProductsPurchase(final Map<String, Integer> productsPurchase) {
        this.productsPurchase = productsPurchase;
    }

    public static ProductsPurchase of(final List<Product> products, final Map<String, Integer> productsUserInput) {
        Validator.validateProductNameAndQuantity(products, productsUserInput);
        return new ProductsPurchase(productsUserInput);
    }

    public Map<String, Integer> getProductsPurchase() {
        return Collections.unmodifiableMap(productsPurchase);
    }

    private static class Validator {

        private static void validateProductNameAndQuantity(final List<Product> products, final Map<String, Integer> productsUserInput) {
            Map<String, Integer> productStockMap = products.stream()
                    .collect(Collectors.toMap(Product::getName, Product::getQuantity, Integer::sum, LinkedHashMap::new));
            productsUserInput.forEach(
                    (inputName, inputQuantity) -> validateProductAvailability(inputName, inputQuantity, productStockMap));
        }

        private static void validateProductAvailability(final String inputName, final int inputQuantity, final Map<String, Integer> productStockMap) {
            validateProductExists(inputName, productStockMap);
            validateProductQuantity(inputName, inputQuantity, productStockMap);
        }

        private static void validateProductExists(final String inputName, final Map<String, Integer> productStockMap) {
            if (!productStockMap.containsKey(inputName)) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
        }

        private static void validateProductQuantity(final String inputName, final int inputQuantity, final Map<String, Integer> productStockMap) {
            int availableQuantity = productStockMap.get(inputName);
            if (inputQuantity > availableQuantity) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }

    }

}