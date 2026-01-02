package Service;

import Model.Ogrenci;
import java.util.ArrayList;
import java.util.List;

/**
 * Öğrenci kayıtları ve bu kayıtlar üzerindeki iş mantığını yöneten servis sınıfıdır.
 * <p>
 * Bu sınıf; yeni öğrenci ekleme (9 haneli numara kontrolü ile), öğrenci silme,
 * numara ile arama, tüm öğrencileri listeleme ve mevcut öğrenci bilgilerini
 * güncelleme işlemlerini koordine eder.
 * </p>
 * @author kral
 * @version 1.0
 */
public class OgrenciService {

    /** * Sistemde kayıtlı olan tüm öğrencileri bellekte tutan liste. */
    private List<Ogrenci> ogrenciler;

    /**
     * Yeni bir OgrenciService nesnesi oluşturur ve öğrenci listesini başlatır.
     */
    public OgrenciService() {
        this.ogrenciler = new ArrayList<>();
    }

    /**
     * Girilen öğrenci numarasının sistem standartlarına (9 hane) uygunluğunu kontrol eder.
     * <p>Örnek geçerli format: 192113001</p>
     *
     * @param ogrenciNo Kontrol edilecek tam sayı formatındaki öğrenci numarası.
     * @return Numara tam olarak 9 haneliyse true, aksi halde false.
     */
    private boolean ogrenciNoGecerliMi(int ogrenciNo) {
        return String.valueOf(ogrenciNo).length() == 9;
    }

    /**
     * Sisteme yeni bir öğrenci kaydı ekler.
     * <p>
     * Ekleme işlemi yapılmadan önce şu doğrulamalar gerçekleştirilir:
     * 1. Öğrenci nesnesi null olamaz.
     * 2. Öğrenci numarası tam 9 hane olmalıdır.
     * 3. Aynı öğrenci numarasıyla sistemde başka bir kayıt bulunmamalıdır.
     * </p>
     *
     * @param ogrenci Eklenecek olan {@link Ogrenci} nesnesi.
     * @return Kayıt başarıyla eklenirse true, kural ihlali durumunda false döner.
     */
    public boolean ogrenciEkle(Ogrenci ogrenci) {
        if (ogrenci == null) {
            return false;
        }

        if (!ogrenciNoGecerliMi(ogrenci.getOgrenciNo())) {
            System.out.println("Hata: Öğrenci numarası 9 haneli olmalıdır (Örn: 192113001)");
            return false;
        }

        if (ogrenciVarMi(ogrenci.getOgrenciNo())) {
            return false;
        }

        ogrenciler.add(ogrenci);
        return true;
    }

    /**
     * Verilen öğrenci numarasına sahip kaydı sistemden kalıcı olarak siler.
     *
     * @param ogrenciNo Silinecek öğrencinin benzersiz numarası.
     * @return Silme işlemi başarılıysa true, öğrenci bulunamazsa false döner.
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
     * Sistemde öğrenci numarasına göre arama yapar.
     *
     * @param ogrenciNo Aranacak benzersiz öğrenci numarası.
     * @return Eşleşen {@link Ogrenci} nesnesini döndürür, bulunamazsa null döndürür.
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
     * Sistemde kayıtlı olan tüm öğrencilerin listesini döndürür.
     *
     * @return Mevcut tüm öğrencileri içeren {@link List}.
     */
    public List<Ogrenci> ogrenciListele() {
        return ogrenciler;
    }

    /**
     * Belirtilen öğrenci numarasının sistemde kayıtlı olup olmadığını kontrol eder.
     *
     * @param ogrenciNo Kontrol edilecek numara.
     * @return Kayıt mevcutsa true, yoksa false döner.
     */
    public boolean ogrenciVarMi(int ogrenciNo) {
        return ogrenciAra(ogrenciNo) != null;
    }

    /**
     * Mevcut bir öğrencinin temel bilgilerini (isim ve soyisim) günceller.
     * <p>
     * İş kuralı gereği; öğrenci numarası, bölümü ve doğum tarihi gibi temel
     * kimlik bilgileri bu metod üzerinden değiştirilemez.
     * </p>
     *
     * @param ogrenci Güncel bilgileri taşıyan {@link Ogrenci} nesnesi.
     * @return Güncelleme başarılıysa true, öğrenci bulunamazsa false döner.
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