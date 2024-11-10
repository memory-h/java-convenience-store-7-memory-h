package store.controller;

import store.domain.conveniencestore.Product;
import store.domain.conveniencestore.Products;
import store.utils.Parser;
import store.utils.PriceFormatter;
import store.view.OutputView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static store.common.ConvenienceProductMessage.*;

public class StoreInventoryController {

    private static final String productFilePath = "src/main/resources/products.md";
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
        OutputView.startMessage(storeInventoryView.append(NEW_LINE.getMessage()).toString());
        return Products.from(products);
    }

    private void processProductLine(final String line) {
        products.add(parseProduct(line));
        formatProductView(line);
    }

    private Product parseProduct(final String line) {
        String[] productList = line.split(COMMA_DELIMITER.getMessage());
        return Product.of(productList[NAME_INDEX],
                Parser.parseInt(productList[PRICE_INDEX]),
                Parser.parseInt(productList[QUANTITY_INDEX]),
                productList[PROMOTION_INDEX]);
    }

    private void formatProductView(final String line) {
        String[] productList = line.split(COMMA_DELIMITER.getMessage());
        storeInventoryView.append(BULLET_POINT.getMessage())
                .append(productList[NAME_INDEX]).append(SPACE.getMessage())
                .append(PriceFormatter.formatToKoreanCurrency(productList[PRICE_INDEX])).append(CURRENCY_UNIT.getMessage())
                .append(getAvailableStockMessage(productList));
        if (!productList[PROMOTION_INDEX].equals(NULL_STRING.getMessage())) {
            storeInventoryView.append(productList[PROMOTION_INDEX]);
        }
        storeInventoryView.append(NEW_LINE.getMessage());
    }

    private String getAvailableStockMessage(final String[] productList) {
        if (productList[QUANTITY_INDEX].equals(EMPTY_QUANTITY.getMessage())) {
            return OUT_OF_STOCK_MESSAGE.getMessage();
        }
        return productList[QUANTITY_INDEX] + QUANTITY_UNIT.getMessage();
    }

}