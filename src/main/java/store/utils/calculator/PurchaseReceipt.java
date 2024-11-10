package store.utils.calculator;

import store.domain.conveniencestore.Product;

import java.util.*;

public class PurchaseReceipt {

    private static final String NOT_PROMOTION = "null";
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;
    private static final int MEMBERSHIP_DISCOUNT_RATE = 30;
    private static final int PERCENTAGE_DIVISOR = 100;
    private static final int DEFAULT_VALUE = 0;

    private PurchaseReceipt() {
    }

    public static int calculateTotalPurchases(final Map<String, Integer> productsPurchase, final List<Product> products) {
        return computeTotalReceipt(productsPurchase, products);
    }

    public static int calculatePromotionDiscount(final Map<String, Integer> promotionResult, final List<Product> products) {
        return computeTotalReceipt(promotionResult, products);
    }

    public static int calculateMembershipDiscount(final Map<String, Integer> productsPurchase, final List<Product> products) {
        int membershipDiscountAmount = DEFAULT_VALUE;
        Set<String> productNames = new LinkedHashSet<>(productsPurchase.keySet());
        for (Product product : products) {
            if (productNames.contains(product.getName()) && !isPromotionalProduct(productNames, product)) {
                membershipDiscountAmount += product.getPrice() * productsPurchase.get(product.getName());
                productNames.remove(product.getName());
            }
        }
        return Math.min(computeMembershipDiscount(membershipDiscountAmount), MAX_MEMBERSHIP_DISCOUNT);
    }

    public static int calculateFinalAmount(int totalPurchases, int promotionDiscount, int membershipDiscount) {
        return totalPurchases - (promotionDiscount + membershipDiscount);
    }

    private static int computeTotalReceipt(Map<String, Integer> productsPurchase, List<Product> products) {
        int totalReceipt = DEFAULT_VALUE;
        Set<String> productNames = new LinkedHashSet<>(productsPurchase.keySet());
        for (Product product : products) {
            if (productNames.contains(product.getName())) {
                totalReceipt += productsPurchase.get(product.getName()) * product.getPrice();
                productNames.remove(product.getName());
            }
        }
        return totalReceipt;
    }

    private static boolean isPromotionalProduct(Set<String> productNames, Product product) {
        if (!product.getPromotion().equals(NOT_PROMOTION)) {
            productNames.remove(product.getName());
            return true;
        }
        return false;
    }

    private static int computeMembershipDiscount(final int amount) {
        return (amount * MEMBERSHIP_DISCOUNT_RATE) / PERCENTAGE_DIVISOR;
    }

}