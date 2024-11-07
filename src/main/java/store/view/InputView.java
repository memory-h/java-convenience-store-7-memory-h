package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.validator.InputViewValidator;
import store.validator.ParserValidator;

public class InputView {

    public static String readProducts() {
        String userInput = readUserInput();
        ParserValidator.validateIndividualProductDelimiter(userInput);
        return userInput;
    }

    public static String readUserInput() {
        String userInput = Console.readLine();
        InputViewValidator.validateUserInputIsNotEmpty(userInput);
        return userInput;
    }

}