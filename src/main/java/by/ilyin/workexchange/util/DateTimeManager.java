package by.ilyin.workexchange.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeManager {


    private static DateTimeManager instance = null;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateTimeManager() {
    }

    public static DateTimeManager getInstance() {
        if (instance == null) {
            instance = new DateTimeManager();
        }
        return instance;
    }

    public LocalDateTime getCurrentLocalDateTime() {
        String currentLocalDateTimeStr = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        LocalDateTime currentLocalDateTime = LocalDateTime.parse(currentLocalDateTimeStr, DATE_TIME_FORMATTER);
        return currentLocalDateTime;
    }

}
