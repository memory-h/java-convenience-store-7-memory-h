package store.domain;

public class PurchaseSummary {

    private final String productName;
    private final int quantity;
    private final int price;

    private PurchaseSummary(final String productName, final int quantity, final int price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public static PurchaseSummary of(final String productName, final int quantity, final int price) {
        return new PurchaseSummary(productName, quantity, price);
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

}