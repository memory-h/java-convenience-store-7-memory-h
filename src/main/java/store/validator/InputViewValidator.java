package store.validator;

import static store.common.ViewMessage.NO;
import static store.common.ViewMessage.YES;

public class InputViewValidator {

    public static void validateUserInputIsNotEmpty(final String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    public static void validateUserInputYesOrNo(final String userInput) {
        if (!(userInput.equals(YES.getMessage()) || userInput.equals(NO.getMessage()))) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

}