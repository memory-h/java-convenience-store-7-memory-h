package store.utils;

import store.domain.PurchaseSummary;
import store.domain.PurchaseSummaryList;
import store.domain.conveniencestore.Product;

import java.util.*;

public class PurchaseSummaryGenerator {

    private static final List<PurchaseSummary> purchaseSummaries = new ArrayList<>();

    public static PurchaseSummaryList generateSummaryList(Map<String, Integer> productsPurchase, List<Product> products) {
        Set<String> productNames = new LinkedHashSet<>(productsPurchase.keySet());
        for (Product product : products) {
            if (productNames.contains(product.getName())) {
                productNames.remove(product.getName());
                purchaseSummaries.add(PurchaseSummary.of(
                        product.getName(), productsPurchase.get(product.getName()),
                        product.getPrice() * productsPurchase.get(product.getName())));
            }
        }
        return PurchaseSummaryList.from(purchaseSummaries);
    }

}