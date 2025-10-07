package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date operations
 */
public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Parse a date string in format dd/MM/yyyy
     */
    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    /**
     * Format a date to string in format dd/MM/yyyy
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
}