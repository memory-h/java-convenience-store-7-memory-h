package store.controller;

import store.domain.Product;
import store.domain.Products;
import store.utils.Parser;
import store.utils.PriceFormatter;
import store.view.OutputView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoreInventoryController {

    private static final String productFilePath = "src/main/resources/products.md";
    private static final String CURRENCY_UNIT = "원 ";
    private static final String QUANTITY_UNIT = "개 ";
    private static final String BULLET_POINT = "- ";
    private static final String NEW_LINE = "\n";
    private static final String NULL_STRING = "null";
    private static final String COMMA_DELIMITER = ",";
    private static final String SPACE = " ";
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_INDEX = 3;

    private final List<Product> products = new ArrayList<>();
    private final StringBuilder storeInventoryView = new StringBuilder();

    public Products init() {
        try(BufferedReader br = new BufferedReader(new FileReader(productFilePath))) {
            br.readLine();
            br.lines().forEach(this::processProductLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputView.startMessage(storeInventoryView.toString());
        return Products.from(products);
    }

    private void processProductLine(final String line) {
        products.add(parseProduct(line));
        formatProductView(line);
    }

    private Product parseProduct(final String line) {
        String[] productList = line.split(COMMA_DELIMITER);
        boolean promotion = isPromotion(productList);
        return Product.of(productList[NAME_INDEX],
                Parser.parseInt(productList[PRICE_INDEX]),
                Parser.parseInt(productList[QUANTITY_INDEX]),
                promotion);
    }

    private boolean isPromotion(final String[] productList) {
        return !productList[PROMOTION_INDEX].equals(NULL_STRING);
    }

    private void formatProductView(final String line) {
        String[] productList = line.split(COMMA_DELIMITER);
        storeInventoryView.append(BULLET_POINT)
                .append(productList[NAME_INDEX]).append(SPACE)
                .append(PriceFormatter.formatToKoreanCurrency(productList[PRICE_INDEX])).append(CURRENCY_UNIT)
                .append(productList[QUANTITY_INDEX]).append(QUANTITY_UNIT);
        if (!productList[PROMOTION_INDEX].equals(NULL_STRING)) {
            storeInventoryView.append(productList[PROMOTION_INDEX]);
        }
        storeInventoryView.append(NEW_LINE);
    }

}