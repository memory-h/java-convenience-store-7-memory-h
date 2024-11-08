package store.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatter {

    public static String formatToKoreanCurrency(String price) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);
        return formatter.format(Integer.parseInt(price));
    }

}