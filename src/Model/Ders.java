package Model;

/**
 * Ders sınıfı, üniversitede verilen bir dersin temel bilgilerini temsil eder.
 */
public class Ders {

    private String ad;
    private String kod;
    private int akts;

    /**
     * Ders nesnesi oluşturur.
     *
     * @param ad dersin adı
     * @param kod dersin kodu (örn: Java445)
     * @param akts dersin AKTS değeri
     */
    public Ders(String ad, String kod, int akts){
        this.ad = ad;
        this.kod = kod;
        this.akts = akts;
    }

    /**
     * Dersin adını döndürür.
     */
    public String getAd() {
        return ad;
    }

    /**
     * Dersin adını günceller.
     *
     * @param ad atanmış ders adı
     */
    public void setAd(String ad) {
        this.ad = ad;
    }

    /**
     * Dersin kodunu döndürür.
     */
    public String getKod() {
        return kod;
    }

    /**
     * Dersin akts değerini döndürür.
     */
    public int getAkts() {
        return akts;
    }
}
