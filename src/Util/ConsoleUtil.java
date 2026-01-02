package Util;

/**
 * Konsol tabanlı kullanıcı arayüzü işlemlerini merkezileştirmek için kullanılan yardımcı sınıftır.
 * <p>
 * Bu sınıf; başlık yazdırma, ayırıcı çizgi oluşturma ve kullanıcıdan onay (Enter) bekleme
 * gibi ekran düzenleyici fonksiyonları static metodlar olarak sunar.
 * </p>
 * @author kral
 * @version 1.0
 */
public class ConsoleUtil {

    /**
     * Belirtilen metni konsolda vurgulu bir başlık formatında yazdırır.
     * <p>
     * Başlığın üstüne ve altına eşittir (=) işaretlerinden oluşan çizgiler ekleyerek
     * görsel bir çerçeve oluşturur.
     * </p>
     *
     * @param title Ekrana yazdırılacak olan başlık metni.
     */
    public static void printTitle(String title) {
        System.out.println("\n==============================");
        System.out.println(title);
        System.out.println("==============================");
    }

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
            // Kullanıcının bir karakter girmesini bekler (genellikle Enter)
            System.in.read();
        } catch (Exception ignored) {
            // Hata durumunda akışın bozulmaması için boş bırakılmıştır
        }
    }
}