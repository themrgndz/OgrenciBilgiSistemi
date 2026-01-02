package Util;

/**
 * Konsol tabanlı kullanıcı arayüzü işlemlerini merkezileştirmek için kullanılan yardımcı sınıftır.
 * <p>
 * Bu sınıf; başlık yazdırma, ayırıcı çizgi oluşturma ve kullanıcıdan onay (Enter) bekleme
 * gibi ekran düzenleyici fonksiyonları static metodlar olarak sunar.
 * </p>
 */
public class ConsoleUtil {
    /**
     * Konsol ekranına standart bir ayırıcı çizgi (tirelerden oluşan) yazdırır.
     * <p>
     * Liste öğeleri veya farklı bölümler arasında görsel ayrım sağlamak için kullanılır.
     * </p>
     */
    public static void printLine() {
        System.out.println("------------------------------");
    }

    /**
     * Uygulama akışını durdurarak kullanıcının 'Enter' tuşuna basmasını bekler.
     * <p>
     * Menü geçişlerinde veya bilgi mesajlarından sonra ekranın hemen temizlenmemesi
     * veya değişmemesi için kullanılır. Giriş sırasında oluşabilecek hatalar
     * güvenli bir şekilde yoksayılır.
     * </p>
     */
    public static void waitForEnter() {
        System.out.println("\nDevam etmek için Enter'a bas...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }
}