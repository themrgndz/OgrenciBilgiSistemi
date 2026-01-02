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
 * Not girişleri, GPA hesaplamaları ve başarı sıralaması işlemlerini yöneten menü sınıfı.
 * <p>
 * Bu sınıf; öğrencilere not atanması, mevcut notların güncellenmesi, tekil veya toplu
 * GPA hesaplamaları ve sonuçların dosyaya yazdırılması gibi işlevleri koordine eder.
 * </p>
 */
public class GpaMenu {

    /** Not ve GPA hesaplama mantığını yürüten servis. */
    private final GpaService gpaService;
    /** Öğrenci bilgilerine erişim sağlayan servis. */
    private final OgrenciService ogrenciService;
    /** Ders bilgilerine erişim sağlayan servis. */
    private final DersService dersService;

    /**
     * GpaMenu nesnesi oluşturur ve gerekli servis bağımlılıklarını enjekte eder.
     *
     * @param gpaService     Not ve ortalama işlemlerini yöneten servis.
     * @param ogrenciService Öğrenci arama ve listeleme işlemleri için servis.
     * @param dersService    Ders arama ve listeleme işlemleri için servis.
     */
    public GpaMenu(GpaService gpaService,
                   OgrenciService ogrenciService,
                   DersService dersService) {
        this.gpaService = gpaService;
        this.ogrenciService = ogrenciService;
        this.dersService = dersService;
    }

    /**
     * Not ve GPA işlemleri menü döngüsünü başlatır.
     * <p>
     * Kullanıcı "geri" yazana kadar aktif kalır. Seçilen işleme göre ilgili alt metodları
     * çağırır ve sayısal olmayan hatalı girişleri yönetir.
     * </p>
     */
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
                    default -> System.out.println("Geçersiz seçim! Lütfen menüdeki rakamlardan birini kullanın.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Hata: Lütfen geçerli bir seçim (rakam veya 'geri') yapınız!");
            }

            ConsoleUtil.waitForEnter();
        }
    }

    /**
     * Not ve GPA işlemleri seçeneklerini görsel bir çerçeve içinde ekrana yazdırır.
     */
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

    /**
     * Belirli bir öğrenciye seçilen bir ders için harf notu atar.
     * <p>
     * Önce öğrenciyi doğrular, ardından mevcut dersleri listeler. Geçerli bir ders kodu
     * ve harf notu (AA, BA vb.) girildiğinde kaydı tamamlar.
     * </p>
     */
    private void notEkle() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);

        if (ogrenci == null) {
            System.out.println("Hata: Öğrenci bulunamadı.");
            return;
        }

        List<Ders> dersler = dersService.dersListele();
        if (dersler.isEmpty()) {
            System.out.println("Hata: Sistemde kayıtlı ders yok. Önce ders eklemelisiniz.");
            return;
        }

        System.out.println("\n--- Mevcut Dersler ---");
        for (Ders d : dersler) {
            System.out.println(d.getKod() + " - " + d.getAd() + " (" + d.getAkts() + " AKTS)");
        }

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);
        if (ders == null) {
            System.out.println("Hata: Ders bulunamadı.");
            return;
        }

        String harfNotu = InputUtil.readString("Harf Notu (AA, BA, BB...): ");
        if (gpaService.notEkle(ogrenci, ders, harfNotu)) {
            System.out.println("Not başarıyla sisteme eklendi.");
        } else {
            System.out.println("Hata: Not eklenemedi (Harf notu geçersiz veya kayıt zaten mevcut).");
        }
    }

    /**
     * Öğrencinin daha önce girilmiş bir ders notunu yenisiyle değiştirir.
     */
    private void notGuncelle() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);

        String yeniNot = InputUtil.readString("Yeni Harf Notu: ");
        if (gpaService.notGuncelle(ogrenci, ders, yeniNot)) {
            System.out.println("Not başarıyla güncellendi.");
        } else {
            System.out.println("Hata: Not güncellenemedi (Öğrenci/Ders bulunamadı veya not formatı hatalı).");
        }
    }

    /**
     * Öğrencinin belirli bir dersten aldığı güncel harf notunu ekrana yazdırır.
     */
    private void notGoruntule() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);

        String not = gpaService.harfNotuBul(ogrenci, ders);
        System.out.println(not != null ? "Harf Notu: " + not : "Kayıt bulunamadı.");
    }

    /**
     * Öğrencinin tüm derslerinden elde ettiği ağırlıklı genel not ortalamasını (GPA) hesaplar.
     */
    private void gpaHesapla() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci != null) {
            System.out.printf("%s %s için hesaplanan GPA: %.2f\n", ogrenci.getIsim(), ogrenci.getSoyisim(), gpaService.gpaHesapla(ogrenci));
        } else {
            System.out.println("Hata: Öğrenci bulunamadı.");
        }
    }

    /**
     * Belirli bir öğrencinin sistemde not kaydı bulunan tüm derslerini listeler.
     */
    private void ogrencininDersleriniListele() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);

        if (ogrenci == null) {
            System.out.println("Hata: Öğrenci bulunamadı.");
            return;
        }

        List<Ders> dersler = gpaService.ogrencininDersleri(ogrenci);
        if (dersler.isEmpty()) {
            System.out.println("Bu öğrenciye ait ders kaydı bulunmamaktadır.");
        } else {
            System.out.println("\n--- " + ogrenci.getIsim() + " Tarafından Alınan Dersler ---");
            for (Ders d : dersler) {
                System.out.println(d.getKod() + " - " + d.getAd());
            }
        }
    }

    /**
     * Tüm öğrencileri GPA değerlerine göre büyükten küçüğe sıralar ve 'sonuclar.txt' dosyasına kaydeder.
     * <p>
     * Sıralama işlemi sırasında {@link GpaService#gpaHesapla(Ogrenci)} metodu kullanılır.
     * Sonuçlar hem konsola basılır hem de kalıcı olarak dosyaya yazılır.
     * </p>
     */
    private void gpaSiraliListeleVeKaydet() {
        List<Ogrenci> ogrenciler = new ArrayList<>(ogrenciService.ogrenciListele());
        if (ogrenciler.isEmpty()) {
            System.out.println("Sistemde sıralanacak öğrenci bulunmamaktadır.");
            return;
        }

        ogrenciler.sort((a, b) -> Double.compare(
                gpaService.gpaHesapla(b),
                gpaService.gpaHesapla(a)));

        try (PrintWriter pw = new PrintWriter(new FileWriter("sonuclar.txt"))) {
            System.out.println("\n--- GPA BAŞARI SIRALAMASI ---");
            for (Ogrenci o : ogrenciler) {
                String satir = o.getOgrenciNo() + " - " + o.getIsim() + " " + o.getSoyisim() +
                        " | GPA: " + String.format("%.2f", gpaService.gpaHesapla(o));
                System.out.println(satir);
                pw.println(satir);
            }
            System.out.println("\nSonuçlar 'sonuclar.txt' dosyasına başarıyla kaydedildi.");
        } catch (IOException e) {
            System.out.println("Hata: Dosya yazma işlemi sırasında bir sorun oluştu.");
        }
    }
}