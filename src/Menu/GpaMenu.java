package Menu;

import Model.Ders;
import Model.Ogrenci;
import Service.GpaService;
import Service.OgrenciService;
import Service.DersService;
import Util.ConsoleUtil;
import Util.InputUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Not ve GPA işlemlerini yöneten sınıf.
 * Harf notu sistemi ve AKTS ağırlıklı GPA hesaplama işlemlerini kullanıcıya sunar.
 * Ayrıca sonuçları GPA'ya göre sıralayıp dosyaya kaydeder.
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
                    case 6:
                        gpaSiraliListeleVeKaydet();
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
        System.out.println("|   6 - Sıralı Listele ve Dosyaya Kaydet |");
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

    /**
     * Öğrencileri genel not ortalamalarına (GPA) göre azalan sırada konsola yazdırır
     * ve aynı çıktıyı 'sonuclar.txt' dosyasına kaydeder[cite: 57, 58].
     */
    private void gpaSiraliListeleVeKaydet() {
        List<Ogrenci> ogrenciler = new ArrayList<>(ogrenciService.ogrenciListele());

        if (ogrenciler.isEmpty()) {
            System.out.println("Sistemde kayıtlı öğrenci yok.");
            return;
        }

        // GPA'ya göre azalan (büyükten küçüğe) sıralama
        ogrenciler.sort((o1, o2) -> Double.compare(gpaService.gpaHesapla(o2), gpaService.gpaHesapla(o1)));

        try (PrintWriter writer = new PrintWriter(new FileWriter("sonuclar.txt"))) {
            System.out.println("\n--- GPA SIRALI LİSTE ---");
            writer.println("--- GPA SIRALI LİSTE ---");

            for (int i = 0; i < ogrenciler.size(); i++) {
                Ogrenci ogrenci = ogrenciler.get(i);
                double gpa = gpaService.gpaHesapla(ogrenci);

                // Sıra numarası, isim, soyisim ve GPA formatı
                String satir = String.format("%d. %s %s - No: %d - GPA: %.2f",
                        (i + 1), ogrenci.getIsim(), ogrenci.getSoyisim(),
                        ogrenci.getOgrenciNo(), gpa);

                System.out.println(satir);
                writer.println(satir); // Dosyaya yazdır
            }

            System.out.println("\nSonuçlar 'sonuclar.txt' dosyasına başarıyla kaydedildi.");

        } catch (IOException e) {
            System.out.println("Dosya yazılırken bir hata oluştu: " + e.getMessage());
        }
    }
}