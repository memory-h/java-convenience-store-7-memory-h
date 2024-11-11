package store.common;

public enum ViewMessage {

    STORE_START_MESSAGE("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n\n"),
    INPUT_PRODUCT_NAME_AND_QUANTITY("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    MEMBERSHIP_STATUS_MESSAGE("멤버십 할인을 받으시겠습니까? (Y/N)"),
    LINE_SEPARATOR("==============W 편의점================"),
    ITEM_MENU("상품명\t\t\t\t수량\t\t  금액"),
    ITEM_FORMAT("%-9s\t\t\t%-2d\t\t  %-,8d%n"),
    PROMOTION_SEPARATOR("=============증\t\t정==============="),
    PROMOTION_FORMAT ("%-9s\t\t\t%-2d%n"),
    SEPARATOR("===================================="),
    TOTAL_FORMAT("%-9s\t\t\t%-2d\t\t  %,5d%n"),
    FOOTER_FORMAT("%-9s\t\t\t\t\t  %-1s%n"),
    PROMOTION_ADDITIONAL_QUANTITY_MESSAGE("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    NON_PROMOTION_PURCHASE_CONFIRMATION_MESSAGE("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    PROMPT_FOR_ADDITIONAL_PURCHASE_MESSAGE("\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),
    YES("Y"),
    NO("N");

    private final String message;

    ViewMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}