package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.validator.InputViewValidator;
import store.validator.ParserValidator;

import static store.common.ViewMessage.INPUT_PRODUCT_NAME_AND_QUANTITY;

public class InputView {

    public static String readUserInput() {
        String userInput = Console.readLine();
        InputViewValidator.validateUserInputIsNotEmpty(userInput);
        return userInput;
    }

    public static String readProducts() {
        System.out.println(INPUT_PRODUCT_NAME_AND_QUANTITY.getMessage());
        String userInput = readUserInput();
        ParserValidator.validateIndividualProductDelimiter(userInput);
        return userInput;
    }

}