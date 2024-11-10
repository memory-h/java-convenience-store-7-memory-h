package store.domain.conveniencestore;

import store.common.ConvenienceProductMessage;

import java.util.Collections;
import java.util.List;

import static store.common.ConvenienceProductMessage.NULL_STRING;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(ConvenienceProductMessage.BULLET_POINT.getMessage())
                    .append(product.getName()).append(ConvenienceProductMessage.SPACE.getMessage())
                    .append(product.getPrice()).append(ConvenienceProductMessage.CURRENCY_UNIT.getMessage())
                    .append(getQuantityString(product)).append(getPromotionString(product))
                    .append(ConvenienceProductMessage.NEW_LINE.getMessage());
        }
        return sb.toString();
    }

    private String getQuantityString(final Product product) {
        final int minQuantity = 0;
        if (product.getQuantity() > minQuantity) {
            return product.getQuantity() + ConvenienceProductMessage.QUANTITY_UNIT.getMessage();
        }
        return ConvenienceProductMessage.OUT_OF_STOCK_MESSAGE.getMessage() + ConvenienceProductMessage.SPACE.getMessage();
    }

    private String getPromotionString(final Product product) {
        final String EMPTY_STRING = "";
        if (!product.getPromotion().equals(NULL_STRING.getMessage())) {
            return product.getPromotion();
        }
        return EMPTY_STRING;
    }

}