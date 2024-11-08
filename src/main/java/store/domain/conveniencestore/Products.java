package store.domain.conveniencestore;

import java.util.Collections;
import java.util.List;

public class Products {

    public final List<Product> products;

    private Products(final List<Product> products) {
        this.products = products;
    }

    public static Products from(final List<Product> products) {
        return new Products(products);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

}