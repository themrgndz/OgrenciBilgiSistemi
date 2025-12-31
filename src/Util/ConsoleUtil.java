package Util;

/**
 * ConsoleUtil sınıfı, konsol tabanlı uygulamalarda tekrar eden ekran işlemlerini merkezileştirmek için kullanılır.
 */
public class ConsoleUtil {

    /**
     * Konsolda başlık formatında bir metin yazdırır.
     *
     * @param title ekrana yazdırılacak başlık
     */
    public static void printTitle(String title) {
        System.out.println("\n==============================");
        System.out.println(title);
        System.out.println("==============================");
    }

    /**
     * Konsola ayırıcı bir çizgi yazdırır.
     */
    public static void printLine() {
        System.out.println("------------------------------");
    }

    /**
     * Kullanıcının Enter tuşuna basmasını bekler.
     */
    public static void waitForEnter() {
        System.out.println("\nDevam etmek için Enter'a bas...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }
}
