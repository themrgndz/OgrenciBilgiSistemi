package Util;

import java.util.Scanner;

/**
 * InputUtil sınıfı, konsol uygulamalarında kullanıcıdan güvenli veri almayı sağlamak için kullanılır.
 */
public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Kullanıcıdan String türünde veri okur.
     *
     * @param message kullanıcıya gösterilecek mesaj
     * @return Kullanıcının girdiği metin
     */
    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
    public static String readOnlyText(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            // Regex: En az 2 karakter olmalı ve sadece harf/boşluk içermeli
            // \\p{L} tüm dillerdeki harfleri kapsar
            if (input.length() >= 2 && input.matches("^[\\p{L} ]+$")) {
                return input;
            } else {
                System.out.println("Hata: Geçersiz giriş! Sadece harf kullanın ve en az 2 karakter girin.");
            }
        }
    }
    /**
     * Kullanıcıdan int türünde veri okur.
     * Geçersiz girişlerde kullanıcıdan tekrar giriş istenir.
     *
     * @param message kullanıcıya gösterilecek mesaj
     * @return Girilen geçerli tam sayı
     */
    public static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir sayı giriniz.");
            }
        }
    }
}
