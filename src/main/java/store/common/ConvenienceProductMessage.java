package store.common;

public enum ConvenienceProductMessage {

    OUT_OF_STOCK_MESSAGE("재고 없음"),
    EMPTY_QUANTITY("0"),
    CURRENCY_UNIT("원 "),
    QUANTITY_UNIT("개 "),
    BULLET_POINT("- "),
    NEW_LINE("\n"),
    NULL_STRING("null"),
    COMMA_DELIMITER(","),
    SPACE(" ");

    private final String message;

    ConvenienceProductMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}