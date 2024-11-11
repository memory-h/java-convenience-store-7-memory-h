package store.utils.calculator;

import store.domain.conveniencestore.Product;

import java.util.List;

public class ProductFinderUtil {

    private ProductFinderUtil() {
    }

    public static Product findRegularProductByName(final List<Product> products, final String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName) && "null".equals(product.getPromotion()))
                .findFirst()
                .orElse(null);
    }

}