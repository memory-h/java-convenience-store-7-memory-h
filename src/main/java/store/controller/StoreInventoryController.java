package store.controller;

import store.domain.Product;
import store.domain.Products;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoreInventoryController {

    private static final String productFilePath = "src/main/resources/products.md";
    private static final String NULL_STRING = "null";
    private static final String COMMA_DELIMITER = ",";
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_INDEX = 3;

    private final List<Product> products = new ArrayList<>();

    public void init() {
        try(BufferedReader br = new BufferedReader(new FileReader(productFilePath))) {
            br.readLine();
            br.lines().forEach(this::processProductLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Products.from(products);
    }

    private void processProductLine(String line) {
        products.add(parseProduct(line));
    }

    private Product parseProduct(String line) {
        String[] productList = line.split(COMMA_DELIMITER);
        boolean promotion = isPromotion(productList);
        return Product.of(productList[NAME_INDEX],
                Integer.parseInt(productList[PRICE_INDEX]),
                Integer.parseInt(productList[QUANTITY_INDEX]),
                promotion);
    }

    private boolean isPromotion(String[] productList) {
        return !productList[PROMOTION_INDEX].equals(NULL_STRING);
    }

}