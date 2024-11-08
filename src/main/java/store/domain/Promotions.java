package store.domain;

import java.util.Collections;
import java.util.List;

public class Promotions {

    private final List<Promotion> promotions;

    private Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public static Promotions from(List<Promotion> promotions) {
        return new Promotions(promotions);
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }

}