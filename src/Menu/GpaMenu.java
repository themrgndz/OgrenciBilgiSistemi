package Menu;

import Model.Ders;
import Model.Ogrenci;
import Service.DersService;
import Service.GpaService;
import Service.OgrenciService;
import Util.ConsoleUtil;
import Util.InputUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Not ve GPA işlemlerini yöneten menü sınıfı.
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
                    case 1 -> notEkle();
                    case 2 -> notGuncelle();
                    case 3 -> notGoruntule();
                    case 4 -> gpaHesapla();
                    case 5 -> ogrencininDersleriniListele();
                    case 6 -> gpaSiraliListeleVeKaydet();
                    default -> System.out.println("Geçersiz seçim!");
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
        System.out.println("|   1 - Not Ekle                         |");
        System.out.println("|   2 - Not Güncelle                    |");
        System.out.println("|   3 - Not Görüntüle                   |");
        System.out.println("|   4 - GPA Hesapla                     |");
        System.out.println("|   5 - Öğrencinin Aldığı Dersler       |");
        System.out.println("|   6 - GPA Sıralı Liste + Dosya        |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   geri - Ana Menüye Dön               |");
        System.out.println("+---------------------------------------+");
    }

    private void notEkle() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);

        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        List<Ders> dersler = dersService.dersListele();
        if (dersler.isEmpty()) {
            System.out.println("Sistemde kayıtlı ders yok.");
            return;
        }

        System.out.println("\n--- Mevcut Dersler ---");
        for (Ders d : dersler) {
            System.out.println(d.getKod() + " - " + d.getAd() + " (" + d.getAkts() + " AKTS)");
        }

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);
        if (ders == null) {
            System.out.println("Ders bulunamadı.");
            return;
        }

        String harfNotu = InputUtil.readString("Harf Notu (AA, BA, BB...): ");
        if (gpaService.notEkle(ogrenci, ders, harfNotu)) {
            System.out.println("Not başarıyla eklendi.");
        } else {
            System.out.println("Not eklenemedi.");
        }
    }

    private void notGuncelle() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);

        String yeniNot = InputUtil.readString("Yeni Harf Notu: ");
        if (gpaService.notGuncelle(ogrenci, ders, yeniNot)) {
            System.out.println("Not güncellendi.");
        } else {
            System.out.println("Not güncellenemedi.");
        }
    }

    private void notGoruntule() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);

        String not = gpaService.harfNotuBul(ogrenci, ders);
        System.out.println(not != null ? "Harf Notu: " + not : "Kayıt bulunamadı.");
    }

    private void gpaHesapla() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        System.out.printf("GPA: %.2f\n", gpaService.gpaHesapla(ogrenci));
    }

    private void ogrencininDersleriniListele() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);

        List<Ders> dersler = gpaService.ogrencininDersleri(ogrenci);
        for (Ders d : dersler) {
            System.out.println(d.getKod() + " - " + d.getAd());
        }
    }

    private void gpaSiraliListeleVeKaydet() {
        List<Ogrenci> ogrenciler = new ArrayList<>(ogrenciService.ogrenciListele());
        ogrenciler.sort((a, b) -> Double.compare(
                gpaService.gpaHesapla(b),
                gpaService.gpaHesapla(a)));

        try (PrintWriter pw = new PrintWriter(new FileWriter("sonuclar.txt"))) {
            for (Ogrenci o : ogrenciler) {
                String satir = o.getOgrenciNo() + " - " + o.getIsim() +
                        " GPA: " + String.format("%.2f", gpaService.gpaHesapla(o));
                System.out.println(satir);
                pw.println(satir);
            }
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası.");
        }
    }
}
