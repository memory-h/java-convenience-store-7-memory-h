package store.controller;

import store.domain.Promotion;
import store.domain.Promotions;
import store.utils.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorePromotionController {

    private static final String promotionFilePath = "src/main/resources/promotions.md";
    private static final String COMMA_DELIMITER = ",";
    private static final int PROMOTION_NAME_INDEX = 0;
    private static final int PROMOTION_BUY_INDEX = 1;
    private static final int PROMOTION_GET_INDEX = 2;
    private static final int PROMOTION_START_DATE_INDEX = 3;
    private static final int PROMOTION_END_DATE_INDEX = 4;

    private final List<Promotion> promotions = new ArrayList<>();

    public Promotions init() {
        try(BufferedReader br = new BufferedReader(new FileReader(promotionFilePath))) {
            br.readLine();
            br.lines().forEach(line -> promotions.add(parsePromotion(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Promotions.from(promotions);
    }

    private Promotion parsePromotion(String line) {
        String[] promotionList = line.split(COMMA_DELIMITER);
        return Promotion.of(promotionList[PROMOTION_NAME_INDEX],
                Parser.parseInt(promotionList[PROMOTION_BUY_INDEX]),
                Parser.parseInt(promotionList[PROMOTION_GET_INDEX]),
                promotionList[PROMOTION_START_DATE_INDEX],
                promotionList[PROMOTION_END_DATE_INDEX]);
    }

}