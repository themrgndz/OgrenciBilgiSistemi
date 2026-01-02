package Service;

import Model.Ogrenci;
import java.util.ArrayList;
import java.util.List;

/**
 * Öğrenci işlemlerini yöneten servis sınıfıdır.
 * Öğrenci ekleme, silme, arama, listeleme ve güncelleme işlemlerini içerir.
 */
public class OgrenciService {

    /**
     * Sistemde kayıtlı öğrencileri tutan liste.
     */
    private List<Ogrenci> ogrenciler;

    /**
     * OgrenciService yapıcı metodu.
     */
    public OgrenciService() {
        this.ogrenciler = new ArrayList<>();
    }

    /**
     * Öğrenci numarasının 9 haneli olup olmadığını kontrol eder.
     * Örnek format: 192113001
     *
     * @param ogrenciNo Kontrol edilecek öğrenci numarası
     * @return Geçerliyse true, değilse false
     */
    private boolean ogrenciNoGecerliMi(int ogrenciNo) {
        return String.valueOf(ogrenciNo).length() == 9;
    }

    /**
     * Sisteme yeni öğrenci ekler.
     *
     * @param ogrenci Eklenecek öğrenci
     * @return Başarılıysa true, değilse false
     */
    public boolean ogrenciEkle(Ogrenci ogrenci) {
        if (ogrenci == null) {
            return false;
        }

        if (!ogrenciNoGecerliMi(ogrenci.getOgrenciNo())) {
            System.out.println("Öğrenci numarası 9 haneli olmalıdır (Örn: 192113001)");
            return false;
        }

        if (ogrenciVarMi(ogrenci.getOgrenciNo())) {
            return false;
        }

        ogrenciler.add(ogrenci);
        return true;
    }

    /**
     * Öğrenci numarasına göre öğrenci siler.
     *
     * @param ogrenciNo Silinecek öğrenci numarası
     * @return Başarılıysa true
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
     * @param ogrenciNo Aranacak numara
     * @return Ogrenci nesnesi veya null
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
     * Tüm öğrencileri listeler.
     *
     * @return Öğrenci listesi
     */
    public List<Ogrenci> ogrenciListele() {
        return ogrenciler;
    }

    /**
     * Öğrenci var mı kontrolü.
     *
     * @param ogrenciNo Kontrol edilecek numara
     * @return Varsa true
     */
    public boolean ogrenciVarMi(int ogrenciNo) {
        return ogrenciAra(ogrenciNo) != null;
    }

    /**
     * Öğrenci bilgilerini günceller.
     *
     * @param ogrenci Güncellenmiş öğrenci
     * @return Başarılıysa true
     */
    public boolean ogrenciGuncelle(Ogrenci ogrenci) {
        Ogrenci eski = ogrenciAra(ogrenci.getOgrenciNo());
        if (eski == null) {
            return false;
        }

        eski.setIsim(ogrenci.getIsim());
        eski.setSoyisim(ogrenci.getSoyisim());
        return true;
    }
}
