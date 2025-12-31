package Service;

import Model.Ders;
import Model.Ogrenci;

import java.util.ArrayList;
import java.util.List;

/**
 * Öğrencilerin dersleri ve harf notları üzerinden
 * AKTS ağırlıklı GPA (Genel Not Ortalaması) hesaplamalarını yöneten servis sınıfıdır. [cite: 54, 60]
 */
public class GpaService {

    /**
     * Öğrenci, ders ve harf notu bilgisini birlikte tutan iç sınıftır.
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

    private List<NotKaydi> notlar;

    public GpaService() {
        this.notlar = new ArrayList<>();
    }

    /**
     * Harf notunun sayısal katsayı karşılığını döndürür. [cite: 35, 36, 43]
     *
     * @param harfNotu AA, BA, BB vb. gibi harf notu
     * @return Harf notunun sayısal karşılığı (4.00, 3.50 vb.), geçersizse -1.0
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
     * Bir öğrenciye belirli bir ders için harf notu ekler. [cite: 34, 53]
     *
     * @param ogrenci  Not eklenecek öğrenci
     * @param ders     Notun ait olduğu ders
     * @param harfNotu AA, BA gibi harf notu girişi
     * @return İşlem başarılıysa true
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
     * Mevcut bir not kaydını yeni bir harf notuyla günceller.
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
     * Öğrencinin harf notlarının sayısal katsayıları ve ders AKTS'leri üzerinden
     * ağırlıklı genel not ortalamasını hesaplar. [cite: 54, 56]
     *
     * @param ogrenci GPA hesaplanacak öğrenci
     * @return Hesaplanan GPA değeri
     */
    public double gpaHesapla(Ogrenci ogrenci) {
        int toplamAKTS = 0;
        double agirlikliToplam = 0;

        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci)) {
                int akts = kayit.ders.getAkts(); // [cite: 32]
                double katsayi = harfNotuKarsiligi(kayit.harfNotu); //

                toplamAKTS += akts;
                agirlikliToplam += katsayi * akts; //
            }
        }

        if (toplamAKTS == 0) {
            return 0.0;
        }

        return agirlikliToplam / toplamAKTS;
    }

    /**
     * Bir öğrencinin dersten aldığı harf notunu döndürür.
     */
    public String harfNotuBul(Ogrenci ogrenci, Ders ders) {
        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci) && kayit.ders.equals(ders)) {
                return kayit.harfNotu;
            }
        }
        return null;
    }

    public List<Ders> ogrencininDersleri(Ogrenci ogrenci) {
        List<Ders> dersler = new ArrayList<>();
        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci)) {
                dersler.add(kayit.ders);
            }
        }
        return dersler;
    }

    private boolean kayitVarMi(Ogrenci ogrenci, Ders ders) {
        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci) && kayit.ders.equals(ders)) {
                return true;
            }
        }
        return false;
    }
}