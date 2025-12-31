package Service;

import Model.Ders;
import java.util.ArrayList;
import java.util.List;

/**
 * Ders işlemlerini yöneten servis sınıfıdır.
 * <p>
 * Ders ekleme, silme, arama, listeleme ve güncelleme işlemleri bu sınıf üzerinden gerçekleştirilir.
 * </p>
 */
public class DersService {

    /**
     * Sistemde tanımlı olan tüm dersleri tutan liste.
     */
    private List<Ders> dersler;

    /**
     * DersService nesnesini oluşturur ve ders listesini başlatır.
     */
    public DersService() {
        this.dersler = new ArrayList<>();
    }

    /**
     * Sisteme yeni bir ders ekler.
     *
     * @param ders Eklenecek ders nesnesi
     * @return Ders başarıyla eklenirse true, aksi halde false döner
     */
    public boolean dersEkle(Ders ders) {
        if (ders == null) {
            return false;
        }

        if (dersVarMi(ders.getKod())) {
            return false;
        }

        dersler.add(ders);
        return true;
    }

    /**
     * Ders koduna göre dersi sistemden siler.
     *
     * @param dersKodu Silinecek dersin kodu
     * @return Silme işlemi başarılıysa true, aksi halde false döner
     */
    public boolean dersSil(String dersKodu) {
        Ders ders = dersAra(dersKodu);
        if (ders == null) {
            return false;
        }

        dersler.remove(ders);
        return true;
    }

    /**
     * Ders koduna göre ders arar.
     *
     * @param dersKodu Aranacak dersin kodu
     * @return Ders bulunursa Ders nesnesi, bulunamazsa null döner
     */
    public Ders dersAra(String dersKodu) {
        for (Ders ders : dersler) {
            if (ders.getKod().equalsIgnoreCase(dersKodu)) {
                return ders;
            }
        }
        return null;
    }

    /**
     * Sistemdeki tüm dersleri listeler.
     *
     * @return Ders listesini döndürür
     */
    public List<Ders> dersListele() {
        return dersler;
    }

    /**
     * Verilen ders koduna sahip bir dersin sistemde var olup olmadığını kontrol eder.
     *
     * @param dersKodu Kontrol edilecek ders kodu
     * @return Ders varsa true, yoksa false döner
     */
    public boolean dersVarMi(String dersKodu) {
        return dersAra(dersKodu) != null;
    }

    /**
     * Var olan bir dersin adını günceller.
     * Ders kodu değiştirilemez kabul edilmiştir.
     *
     * @param ders Güncellenmiş bilgileri içeren ders nesnesi
     * @return Güncelleme başarılıysa true, aksi halde false döner
     */
    public boolean dersGuncelle(Ders ders) {
        if (ders == null) {
            return false;
        }

        Ders eskiDers = dersAra(ders.getKod());
        if (eskiDers == null) {
            return false;
        }

        eskiDers.setAd(ders.getAd());
        // AKTS değişebilir kabul ediliyor
        return true;
    }
}
