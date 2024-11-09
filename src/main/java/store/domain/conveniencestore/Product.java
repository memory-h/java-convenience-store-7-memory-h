package store.domain.conveniencestore;

public class Product {

    private static final int MIN_QUANTITY = 0;

    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    private Product(final String name, final int price, final int quantity, final String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product of(final String name, final int price, final int quantity, final String promotion) {
        return new Product(name, price, quantity, promotion);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public void decreaseQuantity(final int quantity) {
        this.quantity = Math.max(MIN_QUANTITY, this.quantity - quantity);
    }

}