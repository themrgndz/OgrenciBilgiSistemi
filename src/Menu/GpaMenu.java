package Menu;

import Model.Ders;
import Model.Ogrenci;
import Service.GpaService;
import Service.OgrenciService;
import Service.DersService;
import Util.ConsoleUtil;
import Util.InputUtil;

import java.util.List;

/**
 * Not ve GPA işlemlerini yöneten sınıf.
 * Harf notu sistemi ve AKTS ağırlıklı GPA hesaplama işlemlerini kullanıcıya sunar.
 */
public class GpaMenu {

    private final GpaService gpaService;
    private final OgrenciService ogrenciService;
    private final DersService dersService;

    public GpaMenu(GpaService gpaService,
                   OgrenciService ogrenciService,
                   DersService dersService) {
        this.gpaService = gpaService;
        this.ogrenciService = ogrenciService;
        this.dersService = dersService;
    }

    public void baslat() {
        while (true) {
            menuYazdir();

            String secim = InputUtil.readString("Seçiminiz: ");

            if (secim.equalsIgnoreCase("geri")) {
                return;
            }

            try {
                int secimNo = Integer.parseInt(secim);

                switch (secimNo) {
                    case 1:
                        notEkle();
                        break;
                    case 2:
                        notGuncelle();
                        break;
                    case 3:
                        notGoruntule();
                        break;
                    case 4:
                        gpaHesapla();
                        break;
                    case 5:
                        ogrencininDersleriniListele();
                        break;
                    default:
                        System.out.println("Geçersiz seçim!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir seçim yapınız!");
            }

            ConsoleUtil.waitForEnter();
        }
    }

    private void menuYazdir() {
        System.out.println("+---------------------------------------+");
        System.out.println("|           NOT / GPA İŞLEMLERİ          |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   1 - Not Ekle (Harf Notu)             |");
        System.out.println("|   2 - Not Güncelle                     |");
        System.out.println("|   3 - Not Görüntüle                    |");
        System.out.println("|   4 - GPA Hesapla                      |");
        System.out.println("|   5 - Öğrencinin Aldığı Dersler        |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   geri - Ana Menüye Dön               |");
        System.out.println("+---------------------------------------+");
    }

    /**
     * Kullanıcıdan öğrenci, ders ve harf notu alarak ekleme yapar.
     */
    private void notEkle() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);
        if (ders == null) {
            System.out.println("Ders bulunamadı.");
            return;
        }

        String harfNotu = InputUtil.readString("Harf Notu (AA, BA, BB...): ");
        if (gpaService.notEkle(ogrenci, ders, harfNotu)) {
            System.out.println("Harf notu başarıyla eklendi.");
        } else {
            System.out.println("Not eklenemedi (Geçersiz harf notu veya kayıt zaten mevcut).");
        }
    }

    /**
     * Mevcut bir harf notunu günceller.
     */
    private void notGuncelle() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);
        if (ders == null) {
            System.out.println("Ders bulunamadı.");
            return;
        }

        String yeniHarfNotu = InputUtil.readString("Yeni Harf Notu (AA, BA, BB...): ");
        if (gpaService.notGuncelle(ogrenci, ders, yeniHarfNotu)) {
            System.out.println("Not başarıyla güncellendi.");
        } else {
            System.out.println("Not güncellenemedi (Kayıt bulunamadı veya geçersiz harf notu).");
        }
    }

    /**
     * Öğrencinin bir dersteki harf notunu görüntüler.
     */
    private void notGoruntule() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);
        if (ders == null) {
            System.out.println("Ders bulunamadı.");
            return;
        }

        String harfNotu = gpaService.harfNotuBul(ogrenci, ders);
        if (harfNotu != null) {
            System.out.println("Harf Notu: " + harfNotu);
        } else {
            System.out.println("Kayıt bulunamadı.");
        }
    }

    private void gpaHesapla() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        double gpa = gpaService.gpaHesapla(ogrenci);
        System.out.printf("Öğrencinin GPA'sı: %.2f\n", gpa);
    }

    private void ogrencininDersleriniListele() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        List<Ders> dersler = gpaService.ogrencininDersleri(ogrenci);

        if (dersler.isEmpty()) {
            System.out.println("Öğrencinin aldığı ders yok.");
            return;
        }

        ConsoleUtil.printLine();
        for (Ders ders : dersler) {
            String harfNotu = gpaService.harfNotuBul(ogrenci, ders);
            System.out.println("Ders Adı  : " + ders.getAd());
            System.out.println("Ders Kodu : " + ders.getKod());
            System.out.println("AKTS      : " + ders.getAkts());
            System.out.println("Harf Notu : " + (harfNotu != null ? harfNotu : "Girilmemiş"));
            ConsoleUtil.printLine();
        }
    }
}