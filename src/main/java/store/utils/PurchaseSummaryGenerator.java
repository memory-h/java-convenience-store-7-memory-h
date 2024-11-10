package store.utils;

import store.domain.PurchaseSummary;
import store.domain.PurchaseSummaryList;
import store.domain.conveniencestore.Product;

import java.util.*;

public class PurchaseSummaryGenerator {

    private static List<PurchaseSummary> purchaseSummaries;

    public static PurchaseSummaryList generateSummaryList(final Map<String, Integer> productsPurchase, final List<Product> products) {
        purchaseSummaries = new ArrayList<>();
        Set<String> productNames = new LinkedHashSet<>(productsPurchase.keySet());
        for (Product product : products) {
            if (productNames.contains(product.getName())) {
                addPurchaseSummary(product, productsPurchase);
                productNames.remove(product.getName());
            }
        }
        return PurchaseSummaryList.from(purchaseSummaries);
    }

    private static void addPurchaseSummary(final Product product, final Map<String, Integer> productsPurchase) {
        purchaseSummaries.add(PurchaseSummary.of(
                product.getName(),
                productsPurchase.get(product.getName()),
                product.getPrice() * productsPurchase.get(product.getName())
        ));
    }

}