package Model;

import java.time.LocalDate;

/**
 * Üniversite bünyesindeki bir akademik bölümü temsil eden model sınıfıdır.
 * <p>
 * Bu sınıf, bölümün adı, resmi web sayfası ve kuruluş tarihi gibi
 * temel bilgileri saklar. Kuruluş tarihi nesne oluşturulduktan sonra
 * değiştirilemez (immutable) yapıdadır.
 * </p>
 * * @author kral
 * @version 1.0
 */
public class Bolum {

    /** Bölümün tam adı (Örn: Bilgisayar Mühendisliği) */
    private String ad;
    /** Bölümün resmi internet adresi */
    private String webSayfasi;
    /** Bölümün resmi kuruluş tarihi (Değiştirilemez) */
    private final LocalDate kurulusTarihi;

    /**
     * Yeni bir Bolum nesnesi oluşturur.
     *
     * @param ad             Bölümün adı
     * @param webSayfasi     Bölümün resmi web sitesi URL'si
     * @param kurulusTarihi  Bölümün resmi kuruluş tarihi
     */
    public Bolum(String ad, String webSayfasi, LocalDate kurulusTarihi){
        this.ad = ad;
        this.webSayfasi = webSayfasi;
        this.kurulusTarihi = kurulusTarihi;
    }

    /**
     * Bölümün adını döndürür.
     * * @return Bölüm adı
     */
    public String getAd() {
        return ad;
    }

    /**
     * Bölümün adını günceller.
     *
     * @param ad Yeni atanacak bölüm adı
     */
    public void setAd(String ad) {
        this.ad = ad;
    }

    /**
     * Bölümün resmi web sitesini döndürür.
     * * @return Web sayfası adresi
     */
    public String getWebSayfasi() {
        return webSayfasi;
    }

    /**
     * Bölümün web sitesi adresini günceller.
     *
     * @param webSayfasi Yeni web sitesi adresi
     */
    public void setWebSayfasi(String webSayfasi) {
        this.webSayfasi = webSayfasi;
    }

    /**
     * Bölümün kuruluş tarihini döndürür.
     * * @return Kuruluş tarihi (LocalDate)
     */
    public LocalDate getKuruluşTarihi() {
        return kurulusTarihi;
    }
}