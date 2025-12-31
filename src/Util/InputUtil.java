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
     * @return Kullanıcının girdiği metin (trim edilmiş)
     */
    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
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
