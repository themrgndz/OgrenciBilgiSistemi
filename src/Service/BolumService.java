package Service;

import Model.Bolum;
import java.util.ArrayList;
import java.util.List;

/**
 * Üniversite bölümleriyle ilgili iş mantığını yöneten servis sınıfıdır.
 * <p>
 * Bu sınıf; bölümlerin sisteme eklenmesi, silinmesi, aranması, listelenmesi ve
 * bilgilerinin güncellenmesi gibi operasyonları merkezi bir noktadan yönetir.
 * Veri tutarlılığını sağlamak için isim kontrolü gibi doğrulamaları gerçekleştirir.
 * </p>
 */
public class BolumService {

    /** * Sistemde tanımlı olan tüm bölümleri bellekte tutan liste.
     */
    private final List<Bolum> bolumler;

    /**
     * Yeni bir BolumService nesnesi oluşturur ve bölüm listesini başlatır.
     */
    public BolumService() {
        this.bolumler = new ArrayList<>();
    }

    /**
     * Sisteme yeni bir bölüm ekler.
     * <p>
     * Ekleme işlemi yapılmadan önce bölümün geçerliliği ve isim çakışması kontrol edilir.
     * Boş isimli veya sistemde zaten kayıtlı olan bölümler eklenmez.
     * </p>
     *
     * @param bolum Eklenecek {@link Bolum} nesnesi.
     * @return Bölüm başarıyla eklenirse true, aksi halde false döner.
     */
    public boolean bolumEkle(Bolum bolum) {
        if (bolum == null || bolum.getAd() == null || bolum.getAd().trim().isEmpty()) {
            return false;
        }

        if (bolumVarMi(bolum.getAd())) {
            return false;
        }

        bolumler.add(bolum);
        return true;
    }

    /**
     * Bölüm adına göre ilgili bölümü sistemden siler.
     *
     * @param bolumAdi Silinecek bölümün tam adı.
     * @return Silme işlemi başarılıysa true, bölüm bulunamazsa false döner.
     */
    public boolean bolumSil(String bolumAdi) {
        Bolum bolum = bolumAra(bolumAdi);
        if (bolum == null) {
            return false;
        }

        bolumler.remove(bolum);
        return true;
    }

    /**
     * Bölüm adına göre sistemde arama yapar.
     * <p>
     * Arama işlemi büyük/küçük harf duyarsız (case-insensitive) olarak gerçekleştirilir.
     * </p>
     *
     * @param bolumAdi Aranacak bölümün adı.
     * @return Bölüm bulunursa {@link Bolum} nesnesini, bulunamazsa null döndürür.
     */
    public Bolum bolumAra(String bolumAdi) {
        for (Bolum bolum : bolumler) {
            if (bolum.getAd().equalsIgnoreCase(bolumAdi)) {
                return bolum;
            }
        }
        return null;
    }

    /**
     * Sistemde kayıtlı olan tüm bölümlerin listesini döndürür.
     *
     * @return Mevcut tüm bölümleri içeren {@link List}.
     */
    public List<Bolum> bolumListele() {
        return bolumler;
    }

    /**
     * Belirtilen isimde bir bölümün sistemde kayıtlı olup olmadığını kontrol eder.
     *
     * @param bolumAdi Kontrol edilecek bölüm adı.
     * @return Bölüm varsa true, yoksa false döner.
     */
    public boolean bolumVarMi(String bolumAdi) {
        return bolumAra(bolumAdi) != null;
    }
}