package Service;

import Model.Ders;
import java.util.ArrayList;
import java.util.List;

/**
 * Üniversite dersleriyle ilgili iş mantığını yöneten servis sınıfıdır.
 * <p>
 * Bu sınıf; derslerin sisteme eklenmesi, silinmesi, benzersiz ders koduyla aranması,
 * listelenmesi ve mevcut ders bilgilerinin güncellenmesi işlemlerini koordine eder.
 * Veri bütünlüğünü sağlamak için AKTS değerinin pozitifliği ve ders kodunun
 * benzersizliği gibi kritik kontrolleri gerçekleştirir.
 * </p>
 */
public class DersService {

    /** * Sistemde tanımlı olan tüm dersleri bellekte tutan liste.
     */
    private final List<Ders> dersler;

    /**
     * Yeni bir DersService nesnesi oluşturur ve ders listesini başlatır.
     */
    public DersService() {
        this.dersler = new ArrayList<>();
    }

    /**
     * Sisteme yeni bir ders ekler.
     * <p>
     * Ekleme öncesinde şu kontroller yapılır:
     * 1. Ders nesnesi, adı ve kodu boş olamaz.
     * 2. AKTS değeri sıfırdan büyük olmalıdır.
     * 3. Girilen ders kodu sistemde başka bir ders tarafından kullanılmıyor olmalıdır.
     * </p>
     *
     * @param ders Eklenecek {@link Ders} nesnesi.
     * @return Ders başarıyla eklenirse true, kural ihlali varsa false döner.
     */
    public boolean dersEkle(Ders ders) {
        if (ders == null ||
                ders.getAd() == null || ders.getAd().trim().isEmpty() ||
                ders.getKod() == null || ders.getKod().trim().isEmpty()) {
            return false;
        }

        if (ders.getAkts() <= 0) {
            System.out.println("Hata: AKTS değeri 0'dan büyük olmalıdır!");
            return false;
        }

        if (dersVarMi(ders.getKod())) {
            return false;
        }

        dersler.add(ders);
        return true;
    }

    /**
     * Benzersiz ders koduna göre ilgili dersi sistemden siler.
     *
     * @param dersKodu Silinecek dersin kodu (Örn: Java445).
     * @return Silme işlemi başarılıysa true, ders bulunamazsa false döner.
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
     * Ders koduna göre sistemde arama yapar.
     * <p>
     * Arama işlemi büyük/küçük harf duyarsız (case-insensitive) olarak gerçekleştirilir.
     * </p>
     *
     * @param dersKodu Aranacak dersin kodu.
     * @return Ders bulunursa {@link Ders} nesnesini, bulunamazsa null döndürür.
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
     * Sistemde kayıtlı olan tüm derslerin listesini döndürür.
     *
     * @return Mevcut tüm dersleri içeren {@link List}.
     */
    public List<Ders> dersListele() {
        return dersler;
    }

    /**
     * Belirtilen koda sahip bir dersin sistemde kayıtlı olup olmadığını kontrol eder.
     *
     * @param dersKodu Kontrol edilecek ders kodu.
     * @return Ders varsa true, yoksa false döner.
     */
    public boolean dersVarMi(String dersKodu) {
        return dersAra(dersKodu) != null;
    }
}