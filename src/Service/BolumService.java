package Service;

import Model.Bolum;
import java.util.ArrayList;
import java.util.List;

/**
 * Bölüm işlemlerini yöneten servis sınıfıdır.
 * <p>
 * Bölüm ekleme, silme, arama, listeleme ve güncelleme işlemleri bu sınıf üzerinden gerçekleştirilir.
 * </p>
 */
public class BolumService {

    /**
     * Sistemde tanımlı olan tüm bölümleri tutan liste.
     */
    private List<Bolum> bolumler;

    /**
     * BolumService nesnesini oluşturur ve
     * bölüm listesini başlatır.
     */
    public BolumService() {
        this.bolumler = new ArrayList<>();
    }

    /**
     * Sisteme yeni bir bölüm ekler.
     *
     * @param bolum Eklenecek bölüm nesnesi
     * @return Bölüm başarıyla eklenirse true, aksi halde false döner
     */
    public boolean bolumEkle(Bolum bolum) {
        if (bolum == null) {
            return false;
        }

        if (bolumVarMi(bolum.getAd())) {
            return false;
        }

        bolumler.add(bolum);
        return true;
    }

    /**
     * Bölüm adına göre bölümü sistemden siler.
     *
     * @param bolumAdi Silinecek bölümün adı
     * @return Silme işlemi başarılıysa true, aksi halde false döner
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
     * Bölüm adına göre bölüm arar.
     *
     * @param bolumAdi Aranacak bölümün adı
     * @return Bölüm bulunursa Bolum nesnesi, bulunamazsa null döner
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
     * Sistemdeki tüm bölümleri listeler.
     *
     * @return Bölüm listesini döndürür
     */
    public List<Bolum> bolumListele() {
        return bolumler;
    }

    /**
     * Verilen bölüm adına sahip bir bölümün sistemde var olup olmadığını kontrol eder.
     *
     * @param bolumAdi Kontrol edilecek bölüm adı
     * @return Bölüm varsa true, yoksa false döner
     */
    public boolean bolumVarMi(String bolumAdi) {
        return bolumAra(bolumAdi) != null;
    }

    /**
     * Var olan bir bölümün web sayfası bilgisini günceller.
     * Bölümün kuruluş tarihi değiştirilemez kabul edilmiştir.
     *
     * @param bolum Güncellenmiş bilgileri içeren bölüm nesnesi
     * @return Güncelleme başarılıysa true, aksi halde false döner
     */
    public boolean bolumGuncelle(Bolum bolum) {
        if (bolum == null) {
            return false;
        }

        Bolum eskiBolum = bolumAra(bolum.getAd());
        if (eskiBolum == null) {
            return false;
        }

        eskiBolum.setWebSayfasi(bolum.getWebSayfasi());
        // kuruluş tarihi değişmez kabul edildi
        return true;
    }
}
