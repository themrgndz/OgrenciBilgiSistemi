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
    public static boolean isMantikliTarih(LocalDate date) {
        if (date == null) return false;
        LocalDate bugun = LocalDate.now();
        LocalDate altSinir = LocalDate.of(1900, 1, 1);
        // Tarih bugünden sonra olamaz ve 1900'den önce olamaz
        return !date.isAfter(bugun) && !date.isBefore(altSinir);
    }
    /**
     * Verilen LocalDate nesnesini belirlenen formatta String olarak döndürür.
     */
    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}
