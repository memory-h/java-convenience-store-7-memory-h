package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.validator.InputViewValidator;

import static store.common.ViewMessage.*;

public class InputView {

    public static String readUserInput() {
        String userInput = Console.readLine();
        OutputView.printEmptyLine();
        InputViewValidator.validateUserInputIsNotEmpty(userInput);
        return userInput;
    }

    public static String readProducts() {
        System.out.println(INPUT_PRODUCT_NAME_AND_QUANTITY.getMessage());
        return readUserInput();
    }

    public static String promptForAdditionalQuantity(final String productName, final int quantity) {
        System.out.printf(PROMOTION_ADDITIONAL_QUANTITY_MESSAGE.getMessage(), productName, quantity);
        OutputView.printEmptyLine();
        String userInput = readUserInput();
        InputViewValidator.validateUserInputYesOrNo(userInput);
        return userInput;
    }

    public static String promptForNonPromotionPurchaseConfirmation(final String productName, final int quantity) {
        System.out.printf(NON_PROMOTION_PURCHASE_CONFIRMATION_MESSAGE.getMessage(), productName, quantity);
        OutputView.printEmptyLine();
        String userInput = readUserInput();
        InputViewValidator.validateUserInputYesOrNo(userInput);
        return userInput;
    }

    public static String printMembershipStatus() {
        System.out.println(MEMBERSHIP_STATUS_MESSAGE.getMessage());
        String userInput = readUserInput();
        InputViewValidator.validateUserInputYesOrNo(userInput);
        return userInput;
    }

    public static String printAdditionalPurchase() {
        System.out.println(PROMPT_FOR_ADDITIONAL_PURCHASE_MESSAGE.getMessage());
        String userInput = readUserInput();
        InputViewValidator.validateUserInputYesOrNo(userInput);
        return userInput;
    }

}