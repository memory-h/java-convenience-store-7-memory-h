package store.utils;

public class Parser {

    private static final String DELIMITER = ",";

    public static String[] splitByDelimiter(final String userInput) {
        return userInput.split(DELIMITER);
    }

}