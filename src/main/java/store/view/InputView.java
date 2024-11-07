package store.view;

import store.validator.InputViewValidator;
import store.validator.ParserValidator;

public class InputView {

    public static String readProducts(final String userInput) {
        ParserValidator.validateIndividualProductDelimiter(userInput);
        return userInput;
    }

    public static String readUserInput(final String userInput) {
        InputViewValidator.validateUserInputIsNotEmpty(userInput);
        return userInput;
    }

}