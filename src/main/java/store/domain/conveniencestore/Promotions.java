package store.domain.conveniencestore;

import java.util.Collections;
import java.util.List;

public class Promotions {

    private final List<Promotion> promotions;

    private Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static Promotions from(final List<Promotion> promotions) {
        return new Promotions(promotions);
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }

}