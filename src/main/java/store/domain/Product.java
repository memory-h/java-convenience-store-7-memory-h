package store.domain;

public class Product {

    private final String name;
    private final int price;
    private final int quantity;
    private final boolean promotion;

    private Product(final String name, final int price, final int quantity, final boolean promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product of(final String name, final int price, final int quantity, final boolean promotion) {
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

    public boolean isPromotion() {
        return promotion;
    }

}