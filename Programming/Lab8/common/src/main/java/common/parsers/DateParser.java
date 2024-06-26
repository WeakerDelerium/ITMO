package common.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser {
    public static final Parser dateParser = date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}
