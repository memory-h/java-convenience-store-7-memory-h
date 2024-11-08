package store.view;

import static store.common.ViewMessage.STORE_START_MESSAGE;

public class OutputView {

    public static void startMessage(final String message) {
        System.out.printf(STORE_START_MESSAGE.getMessage() + message + "%n");
    }

}