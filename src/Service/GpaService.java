package Service;

import Model.Ders;
import Model.Ogrenci;

import java.util.ArrayList;
import java.util.List;

/**
 * Öğrencilerin ders başarılarını ve genel not ortalamalarını (GPA) yöneten servis sınıfıdır.
 * <p>
 * Bu sınıf; harf notlarının sayısal katsayılara dönüştürülmesi, not kayıtlarının tutulması
 * ve derslerin AKTS değerleri kullanılarak ağırlıklı genel not ortalamasının hesaplanması
 * işlemlerini gerçekleştirir.
 * </p>
 */
public class GpaService {

    /**
     * Öğrenci, ders ve harf notu eşleşmesini temsil eden veri yapısı.
     */
    private static class NotKaydi {
        Ogrenci ogrenci;
        Ders ders;
        String harfNotu;

        NotKaydi(Ogrenci ogrenci, Ders ders, String harfNotu) {
            this.ogrenci = ogrenci;
            this.ders = ders;
            this.harfNotu = harfNotu;
        }
    }

    /** Sistemdeki tüm not kayıtlarını tutan liste. */
    private final List<NotKaydi> notlar;

    /**
     * Yeni bir GpaService nesnesi oluşturur ve not listesini başlatır.
     */
    public GpaService() {
        this.notlar = new ArrayList<>();
    }

    /**
     * Girilen harf notunun 4.00'lık sistemdeki sayısal katsayı karşılığını döndürür.
     *
     * @param harfNotu AA, BA, BB gibi standart harf notu girişi.
     * @return Harf notunun katsayı karşılığı (Örn: AA için 4.0), geçersiz not için -1.0.
     */
    public double harfNotuKarsiligi(String harfNotu) {
        if (harfNotu == null) return -1.0;

        switch (harfNotu.toUpperCase().trim()) {
            case "AA": return 4.00;
            case "BA": return 3.50;
            case "BB": return 3.25;
            case "CB": return 3.00;
            case "CC": return 2.50;
            case "DC": return 2.25;
            case "DD": return 2.00;
            case "FD": return 1.50;
            case "FF": return 0.00;
            default: return -1.0;
        }
    }

    /**
     * Bir öğrenci için belirli bir derse ait not kaydı oluşturur.
     * <p>
     * Not eklenmeden önce öğrenci ve ders nesnelerinin geçerliliği ile harf notunun
     * formatı kontrol edilir. Aynı öğrenciye aynı ders için mükerrer kayıt eklenemez.
     * </p>
     *
     * @param ogrenci  Notun atanacağı {@link Ogrenci} nesnesi.
     * @param ders     Notun ait olduğu {@link Ders} nesnesi.
     * @param harfNotu Girilen harf notu.
     * @return İşlem başarılıysa true, geçersiz veri veya mükerrer kayıtta false döner.
     */
    public boolean notEkle(Ogrenci ogrenci, Ders ders, String harfNotu) {
        double katsayi = harfNotuKarsiligi(harfNotu);

        if (ogrenci == null || ders == null || katsayi == -1.0) {
            return false;
        }

        if (kayitVarMi(ogrenci, ders)) {
            return false;
        }

        notlar.add(new NotKaydi(ogrenci, ders, harfNotu.toUpperCase().trim()));
        return true;
    }

    /**
     * Sistemde mevcut olan bir not kaydını yeni bir harf notu ile günceller.
     *
     * @param ogrenci      Notu güncellenecek öğrenci.
     * @param ders         Notu güncellenecek ders.
     * @param yeniHarfNotu Atanacak yeni harf notu.
     * @return Güncelleme başarılıysa true, kayıt bulunamazsa false döner.
     */
    public boolean notGuncelle(Ogrenci ogrenci, Ders ders, String yeniHarfNotu) {
        double katsayi = harfNotuKarsiligi(yeniHarfNotu);

        if (ogrenci == null || ders == null || katsayi == -1.0) {
            return false;
        }

        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci) && kayit.ders.equals(ders)) {
                kayit.harfNotu = yeniHarfNotu.toUpperCase().trim();
                return true;
            }
        }
        return false;
    }
    /**
     * 0-100 arasındaki sayısal notu harf notuna dönüştürür.
     *
     * @param puan Kullanıcının girdiği sayısal not.
     * @return Harf notu karşılığı.
     */
    public String puaniHarfeCevir(int puan) {
        if (puan >= 90) return "AA";
        if (puan >= 85) return "BA";
        if (puan >= 80) return "BB";
        if (puan >= 75) return "CB";
        if (puan >= 70) return "CC";
        if (puan >= 65) return "DC";
        if (puan >= 60) return "DD";
        if (puan >= 50) return "FD";
        return "FF";
    }
    /**
     * Öğrencinin aldığı tüm derslerin AKTS ağırlıklı genel not ortalamasını hesaplar.
     * <p>
     * Hesaplama formülü: Σ(Ders Katsayısı * Ders AKTS) / Σ(Toplam AKTS).
     * </p>
     *
     * @param ogrenci GPA değeri hesaplanacak öğrenci.
     * @return Hesaplanan GPA değeri (0.00 - 4.00 arası), kayıt yoksa 0.0.
     */
    public double gpaHesapla(Ogrenci ogrenci) {
        int toplamAKTS = 0;
        double agirlikliToplam = 0;

        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci)) {
                int akts = kayit.ders.getAkts();
                double katsayi = harfNotuKarsiligi(kayit.harfNotu);

                toplamAKTS += akts;
                agirlikliToplam += katsayi * akts;
            }
        }

        if (toplamAKTS == 0) {
            return 0.0;
        }

        return agirlikliToplam / toplamAKTS;
    }

    /**
     * Bir öğrencinin belirli bir dersten aldığı güncel harf notunu döndürür.
     *
     * @param ogrenci Sorgulanan öğrenci.
     * @param ders    Sorgulanan ders.
     * @return Kayıtlı harf notu, bulunamazsa null.
     */
    public String harfNotuBul(Ogrenci ogrenci, Ders ders) {
        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci) && kayit.ders.equals(ders)) {
                return kayit.harfNotu;
            }
        }
        return null;
    }

    /**
     * Bir öğrencinin sistemde not kaydı bulunan tüm derslerinin listesini döndürür.
     *
     * @param ogrenci Dersleri listelenecek öğrenci.
     * @return Öğrencinin aldığı dersleri içeren {@link List}.
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
     * Öğrenci ve ders ikilisi için sistemde daha önceden girilmiş bir kayıt olup olmadığını kontrol eder.
     */
    private boolean kayitVarMi(Ogrenci ogrenci, Ders ders) {
        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci) && kayit.ders.equals(ders)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sistemden silinen bir öğrenciye ait tüm geçmiş not kayıtlarını temizler.
     * <p>
     * Bu işlem, veri bütünlüğünü korumak ve yetim kayıt oluşmasını engellemek için önemlidir.
     * </p>
     *
     * @param ogrenci Notları temizlenecek olan öğrenci nesnesi.
     */
    public void notlariTemizle(Ogrenci ogrenci) {
        if (ogrenci != null) {
            notlar.removeIf(kayit -> kayit.ogrenci.equals(ogrenci));
        }
    }
}