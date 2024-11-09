package store.utils.calculator;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.conveniencestore.Product;
import store.domain.conveniencestore.Promotion;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionProcessor {

    public static boolean isPromotion(final List<Product> products, final String productName) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(productName) && product.getPromotion() != null);
    }

    public static boolean isWithinPromotionPeriod(final Promotion promotion) {
        LocalDateTime currentDate = DateTimes.now();
        return (promotion.getStartDate().isEqual(currentDate.toLocalDate()) || promotion.getStartDate().isBefore(currentDate.toLocalDate())) &&
                (promotion.getEndDate().isEqual(currentDate.toLocalDate()) || promotion.getEndDate().isAfter(currentDate.toLocalDate()));
    }

}