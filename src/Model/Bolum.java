package Model;

import java.time.LocalDate;

/**
 * Bolum sınıfı, üniversitedeki bir akademik bölümün temel bilgilerini temsil eder.
 */
public class Bolum {

    private String ad;
    private String webSayfasi;
    private final LocalDate kurulusTarihi;

    /**
     * Bolum nesnesi oluşturur.
     *
     * @param ad bölümün adı
     * @param webSayfasi bölümün resmi web sitesi
     * @param kurulusTarihi bölümün kuruluş tarihi
     */
    public Bolum(String ad, String webSayfasi, LocalDate kurulusTarihi){
        this.ad = ad;
        this.webSayfasi = webSayfasi;
        this.kurulusTarihi = kurulusTarihi;
    }

    /**
     * Bölüm adını döndürür.
     */
    public String getAd() {
        return ad;
    }

    /**
     * Bölüm adını günceller.
     *
     * @param ad atanmış bölüm adı
     */
    public void setAd(String ad) {
        this.ad = ad;
    }

    /**
     * Bölümün web sitesini döndürür.
     */
    public String getWebSayfasi() {
        return webSayfasi;
    }

    /**
     * Bölümün web sitesini günceller.
     *
     * @param webSayfasi atanmış web sitesi adresi
     */
    public void setWebSayfasi(String webSayfasi) {
        this.webSayfasi = webSayfasi;
    }

    /**
     * Bölümün kuruluş tarihini döndürür.
     */
    public LocalDate getKuruluşTarihi() {
        return kurulusTarihi;
    }
}
