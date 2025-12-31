package Service;

import Model.Ders;
import Model.Ogrenci;

import java.util.ArrayList;
import java.util.List;

/**
 * Öğrencilerin dersleri ve notları üzerinden
 * GPA (Genel Not Ortalaması) hesaplamalarını yöneten servis sınıfıdır.
 * <p>
 * Öğrenci–Ders–Not ilişkisi domain modelini şişirmemek adına
 * bu servis içerisinde tutulmaktadır.
 * </p>
 */
public class GpaService {

    /**
     * Öğrenci, ders ve not bilgisini birlikte tutan iç sınıftır.
     * Bu sınıf sadece GpaService tarafından kullanılmak üzere tasarlanmıştır.
     */
    private static class NotKaydi {
        /** Notun ait olduğu öğrenci */
        Ogrenci ogrenci;

        /** Notun ait olduğu ders */
        Ders ders;

        /** Öğrencinin dersten aldığı not (0-100) */
        int not;

        /**
         * NotKaydi nesnesini oluşturur.
         *
         * @param ogrenci Notun ait olduğu öğrenci
         * @param ders Notun ait olduğu ders
         * @param not Öğrencinin aldığı not
         */
        NotKaydi(Ogrenci ogrenci, Ders ders, int not) {
            this.ogrenci = ogrenci;
            this.ders = ders;
            this.not = not;
        }
    }

    /**
     * Sistemdeki tüm not kayıtlarını tutan liste.
     */
    private List<NotKaydi> notlar;

    /**
     * GpaService nesnesini oluşturur ve not kayıt listesini başlatır.
     */
    public GpaService() {
        this.notlar = new ArrayList<>();
    }

    /**
     * Bir öğrenciye belirli bir ders için not ekler.
     * Aynı öğrenci ve ders için birden fazla kayıt eklenmesine izin verilmez.
     *
     * @param ogrenci Not eklenecek öğrenci
     * @param ders Notun ait olduğu ders
     * @param not Öğrencinin aldığı not (0-100)
     * @return Not başarıyla eklenirse true, aksi halde false döner
     */
    public boolean notEkle(Ogrenci ogrenci, Ders ders, int not) {
        if (ogrenci == null || ders == null) {
            return false;
        }

        if (not < 0 || not > 100) {
            return false;
        }

        if (kayitVarMi(ogrenci, ders)) {
            return false;
        }

        notlar.add(new NotKaydi(ogrenci, ders, not));
        return true;
    }

    /**
     * Belirli bir öğrencinin, belirli bir dersten aldığı notu bulur.
     *
     * @param ogrenci Notu aranacak öğrenci
     * @param ders Notu aranacak ders
     * @return Not bulunursa Integer olarak döner, bulunamazsa null döner
     */
    public Integer notBul(Ogrenci ogrenci, Ders ders) {
        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci) &&
                    kayit.ders.equals(ders)) {
                return kayit.not;
            }
        }
        return null;
    }

    /**
     * Var olan bir not kaydını günceller.
     *
     * @param ogrenci Notu güncellenecek öğrenci
     * @param ders    Notu güncellenecek ders
     * @param yeniNot Yeni not değeri (0-100)
     * @return Güncelleme başarılıysa true, kayıt bulunamazsa veya not geçersizse false döner
     */
    public boolean notGuncelle(Ogrenci ogrenci, Ders ders, int yeniNot) {
        if (ogrenci == null || ders == null || yeniNot < 0 || yeniNot > 100) {
            return false;
        }

        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci) && kayit.ders.equals(ders)) {
                kayit.not = yeniNot;
                return true;
            }
        }
        return false;
    }

    /**
     * Verilen öğrencinin GPA (Genel Not Ortalaması) değerini hesaplar.
     * Hesaplama AKTS ağırlıklı olarak yapılır.
     *
     * @param ogrenci GPA değeri hesaplanacak öğrenciW
     * @return Öğrencinin GPA değeri
     */
    public double gpaHesapla(Ogrenci ogrenci) {
        int toplamAKTS = 0;
        int agirlikliToplam = 0;

        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci)) {
                int akts = kayit.ders.getAkts();
                toplamAKTS += akts;
                agirlikliToplam += kayit.not * akts;
            }
        }

        if (toplamAKTS == 0) {
            return 0.0;
        }

        return (double) agirlikliToplam / toplamAKTS;
    }

    /**
     * Bir öğrencinin aldığı tüm dersleri listeler.
     *
     * @param ogrenci Dersleri listelenecek öğrenci
     * @return Öğrencinin aldığı derslerin listesi
     */
    public List<Ders> ogrencininDersleri(Ogrenci ogrenci) {
        List<Ders> dersler = new ArrayList<>();

        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci)) {
                dersler.add(kayit.ders);
            }
        }

        return dersler;
    }

    /**
     * Belirli bir öğrenci ve ders için daha önce not kaydı olup olmadığını kontrol eder.
     *
     * @param ogrenci Kontrol edilecek öğrenci
     * @param ders Kontrol edilecek ders
     * @return Kayıt varsa true, yoksa false döner
     */
    private boolean kayitVarMi(Ogrenci ogrenci, Ders ders) {
        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci) &&
                    kayit.ders.equals(ders)) {
                return true;
            }
        }
        return false;
    }
}
