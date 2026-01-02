package Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Uygulama genelinde tarih işlemlerini standartlaştırmak için kullanılan yardımcı sınıftır.
 * <p>
 * Bu sınıf; metin tabanlı tarihlerin {@link LocalDate} nesnesine dönüştürülmesi,
 * girilen tarihlerin mantıksal olarak doğrulanması ve tarihlerin kullanıcıya
 * gösterilecek formatta metne dönüştürülmesi işlemlerini yapar.
 * </p>
 */
public class DateUtil {

    /** Uygulama genelinde kullanılan standart tarih formatı (gg.aa.yyyy) */
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Kullanıcıdan alınan metin formatındaki tarihi LocalDate nesnesine dönüştürür.
     *
     * @param input Kullanıcının girdiği tarih metni
     * @return Dönüştürme başarılıysa {@link LocalDate} nesnesi
     */
    public static LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input, FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Verilen tarihin mantıksal sınırlar içerisinde olup olmadığını kontrol eder.
     * <p>
     * Bir tarihin mantıklı kabul edilmesi için bugünden ileri bir tarih olmaması
     * ve 01.01.1900 tarihinden önce olmaması gerekir.
     * </p>
     *
     * @param date Kontrol edilecek tarih nesnesi.
     * @return Tarih mantıklıysa true, değilse veya null ise false.
     */
    public static boolean isMantikliTarih(LocalDate date) {
        if (date == null) return false;
        LocalDate bugun = LocalDate.now();
        LocalDate altSinir = LocalDate.of(1900, 1, 1);
        return !date.isAfter(bugun) && !date.isBefore(altSinir);
    }

    /**
     * Verilen LocalDate nesnesini standart "dd.MM.yyyy" formatında metne dönüştürür.
     *
     * @param date Formatlanacak tarih nesnesi.
     * @return Belirlenen formatta tarih metni.
     */
    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}