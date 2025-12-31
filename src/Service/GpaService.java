package Service;

import Model.Ders;
import Model.Ogrenci;

import java.util.ArrayList;
import java.util.List;

public class GpaService {

    // İç sınıf: Öğrenci–Ders–Not ilişkisi
    private static class NotKaydi {
        Ogrenci ogrenci;
        Ders ders;
        int not;

        NotKaydi(Ogrenci ogrenci, Ders ders, int not) {
            this.ogrenci = ogrenci;
            this.ders = ders;
            this.not = not;
        }
    }

    private List<NotKaydi> notlar;

    public GpaService() {
        this.notlar = new ArrayList<>();
    }

    // Öğrenciye ders ve not ekleme
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

    // Öğrencinin belirli bir dersini bulma
    public Integer notBul(Ogrenci ogrenci, Ders ders) {
        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci) &&
                    kayit.ders.equals(ders)) {
                return kayit.not;
            }
        }
        return null;
    }

    // Öğrencinin GPA hesaplaması (AKTS ağırlıklı)
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

    // Öğrencinin aldığı tüm dersler
    public List<Ders> ogrencininDersleri(Ogrenci ogrenci) {
        List<Ders> dersler = new ArrayList<>();

        for (NotKaydi kayit : notlar) {
            if (kayit.ogrenci.equals(ogrenci)) {
                dersler.add(kayit.ders);
            }
        }

        return dersler;
    }

    // Kayıt kontrolü
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
