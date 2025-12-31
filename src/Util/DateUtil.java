package Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * DateUtil sınıfı, tarih ile ilgili ortak yardımcı işlemleri merkezileştirmek için kullanılır.
 */
public class DateUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Verilen metni belirlenen tarih formatına göre LocalDate nesnesine dönüştürür.
     */
    public static LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input, FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Verilen LocalDate nesnesini belirlenen formatta String olarak döndürür.
     */
    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}
