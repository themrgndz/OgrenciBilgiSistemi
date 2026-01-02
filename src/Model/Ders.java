package Model;

/**
 * Üniversite bünyesinde verilen bir dersi temsil eden model sınıfıdır.
 * <p>
 * Bu sınıf; dersin adı, benzersiz ders kodu ve AKTS (Avrupa Kredi Transfer Sistemi)
 * değerini saklar. Sistemdeki not hesaplamaları bu sınıftaki AKTS değerleri
 * üzerinden gerçekleştirilir.
 * </p>
 */
public class Ders {

    /** Dersin tam adı*/
    private String ad;
    /** Dersin benzersiz kodu */
    private final String kod;
    /** Dersin AKTS kredi değeri */
    private final int akts;

    /**
     * Yeni bir Ders nesnesi oluşturur.
     *
     * @param ad   Dersin adı
     * @param kod  Dersin benzersiz kodu
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