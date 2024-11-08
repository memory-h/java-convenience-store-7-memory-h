package store.common;

public enum ViewMessage {

    STORE_START_MESSAGE("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n\n");

    private final String message;

    ViewMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}