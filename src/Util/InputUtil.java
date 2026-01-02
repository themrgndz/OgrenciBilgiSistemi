package Util;

import java.util.Scanner;

/**
 * Konsol uygulamalarında kullanıcıdan güvenli veri almayı sağlayan yardımcı sınıftır.
 * <p>
 * Bu sınıf; metin girişi, sayısal giriş ve sadece harf içeren özel giriş türleri için
 * hata kontrolleri (validation) barındırır. Hatalı giriş durumunda kullanıcıyı
 * bilgilendirerek doğru veriyi alana kadar döngüye girer.
 * </p>
 * @author kral
 * @version 1.0
 */
public class InputUtil {

    /** Konsol girişlerini okumak için kullanılan Scanner nesnesi */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Kullanıcıdan herhangi bir metin girişi alır.
     *
     * @param message Kullanıcıya gösterilecek yönlendirme mesajı.
     * @return Girilen metnin boşlukları temizlenmiş (trimmed) hali.
     */
    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    /**
     * Kullanıcıdan sadece harf ve boşluk içeren, en az 2 karakterli metin girişi alır.
     * <p>
     * İsim ve soyisim gibi sadece alfabetik karakterlerin beklendiği alanlar için
     * Regex (Düzenli İfadeler) kullanarak doğrulama yapar.
     * </p>
     *
     * @param message Kullanıcıya gösterilecek yönlendirme mesajı.
     * @return Doğrulanmış alfabetik metin.
     */
    public static String readOnlyText(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            // Regex: En az 2 karakter olmalı ve sadece harf/boşluk içermeli
            if (input.length() >= 2 && input.matches("^[\\p{L} ]+$")) {
                return input;
            } else {
                System.out.println("Hata: Geçersiz giriş! Sadece harf kullanın ve en az 2 karakter girin.");
            }
        }
    }

    /**
     * Kullanıcıdan tam sayı (int) türünde veri okur.
     * <p>
     * Kullanıcı sayısal olmayan bir değer girerse {@link NumberFormatException} yakalanır
     * ve geçerli bir sayı girilene kadar işlem tekrarlanır.
     * </p>
     *
     * @param message Kullanıcıya gösterilecek yönlendirme mesajı.
     * @return Girilen geçerli tam sayı değeri.
     */
    public static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Hata: Lütfen geçerli bir tam sayı giriniz.");
            }
        }
    }
}