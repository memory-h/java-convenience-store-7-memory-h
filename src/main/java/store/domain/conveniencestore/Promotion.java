package store.domain.conveniencestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Promotion {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(final String name, final int buy, final int get, final String startDate, final String endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = LocalDate.parse(startDate, DATE_FORMATTER);
        this.endDate = LocalDate.parse(endDate, DATE_FORMATTER);
    }

    public static Promotion of(final String name, final int buy, final int get, final String startDate, final String endDate) {
        return new Promotion(name, buy, get, startDate, endDate);
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}