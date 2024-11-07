package store.utils;

public class Parser {

    private static final String COMMA_DELIMITER = ",";

    public static String[] splitByDelimiter(final String userInput) {
        return userInput.split(COMMA_DELIMITER);
    }

}