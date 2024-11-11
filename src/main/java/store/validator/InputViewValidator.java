package store.validator;

import static store.common.ViewMessage.NO;
import static store.common.ViewMessage.YES;
import static store.common.exception.ErrorMessage.INVALID_INPUT;

public class InputViewValidator {

    public static void validateUserInputIsNotEmpty(final String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
        }
    }

    public static void validateUserInputYesOrNo(final String userInput) {
        if (!(userInput.equals(YES.getMessage()) || userInput.equals(NO.getMessage()))) {
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
        }
    }

}