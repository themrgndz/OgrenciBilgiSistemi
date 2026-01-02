package Model;

/**
 * Üniversite bünyesinde verilen bir dersi temsil eden model sınıfıdır.
 * <p>
 * Bu sınıf; dersin adı, benzersiz ders kodu ve AKTS (Avrupa Kredi Transfer Sistemi)
 * değerini saklar. Sistemdeki not hesaplamaları bu sınıftaki AKTS değerleri
 * üzerinden gerçekleştirilir.
 * </p>
 * @author kral
 * @version 1.0
 */
public class Ders {

    /** Dersin tam adı (Örn: Nesne Yönelimli Programlama) */
    private String ad;
    /** Dersin benzersiz kodu (Örn: NYP101) */
    private final String kod;
    /** Dersin AKTS kredi değeri */
    private final int akts;

    /**
     * Yeni bir Ders nesnesi oluşturur.
     *
     * @param ad   Dersin adı
     * @param kod  Dersin benzersiz kodu (örn: Java445)
     * @param akts Dersin AKTS kredi değeri
     */
    public Ders(String ad, String kod, int akts){
        this.ad = ad;
        this.kod = kod;
        this.akts = akts;
    }

    /**
     * Dersin adını döndürür.
     * @return Ders adı
     */
    public String getAd() {
        return ad;
    }

    /**
     * Dersin adını günceller.
     *
     * @param ad Atanacak yeni ders adı
     */
    public void setAd(String ad) {
        this.ad = ad;
    }

    /**
     * Dersin kodunu döndürür.
     * @return Ders kodu
     */
    public String getKod() {
        return kod;
    }

    /**
     * Dersin AKTS kredi değerini döndürür.
     * @return AKTS değeri
     */
    public int getAkts() {
        return akts;
    }
}