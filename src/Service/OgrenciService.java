package Service;

import Model.Ogrenci;
import java.util.ArrayList;
import java.util.List;

/**
 * Öğrenci işlemlerini yöneten servis sınıfıdır.
 * <p>
 * Öğrenci ekleme, silme, arama, listeleme ve güncelleme gibi tüm iş kuralları bu sınıf içerisinde toplanmıştır.
 * </p>
 */
public class OgrenciService {

    /**
     * Sistemdeki tüm öğrencileri tutan liste.
     */
    private List<Ogrenci> ogrenciler;

    /**
     * OgrenciService nesnesini oluşturur ve
     * öğrenci listesini başlatır.
     */
    public OgrenciService() {
        this.ogrenciler = new ArrayList<>();
    }

    /**
     * Sisteme yeni bir öğrenci ekler.
     *
     * @param ogrenci Eklenecek öğrenci nesnesi
     * @return Öğrenci başarıyla eklenirse true, aksi halde false döner
     */
    public boolean ogrenciEkle(Ogrenci ogrenci) {
        if (ogrenci == null) {
            return false;
        }

        if (ogrenciVarMi(ogrenci.getOgrenciNo())) {
            return false;
        }

        ogrenciler.add(ogrenci);
        return true;
    }

    /**
     * Öğrenci numarasına göre öğrenciyi sistemden siler.
     *
     * @param ogrenciNo Silinecek öğrencinin numarası
     * @return Silme işlemi başarılıysa true, aksi halde false döner
     */
    public boolean ogrenciSil(int ogrenciNo) {
        Ogrenci ogrenci = ogrenciAra(ogrenciNo);
        if (ogrenci == null) {
            return false;
        }

        ogrenciler.remove(ogrenci);
        return true;
    }

    /**
     * Öğrenci numarasına göre öğrenci arar.
     *
     * @param ogrenciNo Aranacak öğrencinin numarası
     * @return Öğrenci bulunursa Ogrenci nesnesi, bulunamazsa null döner
     */
    public Ogrenci ogrenciAra(int ogrenciNo) {
        for (Ogrenci ogrenci : ogrenciler) {
            if (ogrenci.getOgrenciNo() == ogrenciNo) {
                return ogrenci;
            }
        }
        return null;
    }

    /**
     * Sistemdeki tüm öğrencileri listeler.
     *
     * @return Öğrenci listesini döndürür
     */
    public List<Ogrenci> ogrenciListele() {
        return ogrenciler;
    }

    /**
     * Verilen öğrenci numarasına sahip bir öğrenci sistemde var mı kontrol eder.
     *
     * @param ogrenciNo Kontrol edilecek öğrenci numarası
     * @return Öğrenci varsa true, yoksa false döner
     */
    public boolean ogrenciVarMi(int ogrenciNo) {
        return ogrenciAra(ogrenciNo) != null;
    }

    /**
     * Var olan bir öğrencinin isim ve soyisim bilgilerini günceller.
     * Öğrenci numarası değiştirilemez kabul edilmiştir.
     *
     * @param ogrenci Güncellenmiş bilgileri içeren öğrenci nesnesi
     * @return Güncelleme başarılıysa true, aksi halde false döner
     */
    public boolean ogrenciGuncelle(Ogrenci ogrenci) {
        if (ogrenci == null) {
            return false;
        }

        Ogrenci eskiOgrenci = ogrenciAra(ogrenci.getOgrenciNo());
        if (eskiOgrenci == null) {
            return false;
        }

        eskiOgrenci.setIsim(ogrenci.getIsim());
        eskiOgrenci.setSoyisim(ogrenci.getSoyisim());
        return true;
    }
}
