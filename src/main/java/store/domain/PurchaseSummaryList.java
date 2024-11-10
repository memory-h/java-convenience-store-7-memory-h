package store.domain;

import java.util.Collections;
import java.util.List;

public class PurchaseSummaryList {

    private final List<PurchaseSummary> purchaseSummaries;

    private PurchaseSummaryList(List<PurchaseSummary> purchaseSummaries) {
        this.purchaseSummaries = purchaseSummaries;
    }

    public static PurchaseSummaryList from(List<PurchaseSummary> purchaseSummaries) {
        return new PurchaseSummaryList(purchaseSummaries);
    }

    public List<PurchaseSummary> getPurchaseSummaries() {
        return Collections.unmodifiableList(purchaseSummaries);
    }

}