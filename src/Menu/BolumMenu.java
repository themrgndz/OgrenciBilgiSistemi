package Menu;

import Model.Bolum;
import Service.BolumService;
import Service.OgrenciService;
import Util.ConsoleUtil;
import Util.InputUtil;
import Util.DateUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * Bölüm işlemleri menüsünü yöneten sınıf.
 */
public class BolumMenu {

    private final OgrenciService ogrenciService;
    private final BolumService bolumService;

    public BolumMenu(BolumService bolumService, OgrenciService ogrenciService) {
        this.bolumService = bolumService;
        this.ogrenciService = ogrenciService;
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
                        bolumEkle();
                        break;
                    case 2:
                        bolumSil();
                        break;
                    case 3:
                        bolumListele();
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
        System.out.println("|           BÖLÜM İŞLEMLERİ              |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   1 - Bölüm Ekle                      |");
        System.out.println("|   2 - Bölüm Sil                       |");
        System.out.println("|   3 - Bölümleri Listele               |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   geri - Ana Menüye Dön               |");
        System.out.println("+---------------------------------------+");
    }

    /**
     * Kullanıcıdan bölüm bilgisi alarak yeni bölüm ekler.
     * Kuruluş tarihi için format ve mantıklılık kontrolü yapar.
     */
    private void bolumEkle() {
        // Yeni bir bölüm eklemeden önce mevcut bölümleri gösteriyoruz
        System.out.println("\n--- Mevcut Bölümler ---");
        bolumListele();

        String ad = InputUtil.readOnlyText("Bölüm adı: ");
        String web = InputUtil.readString("Web sayfası: ");

        LocalDate tarih;
        while (true) {
            String tarihStr = InputUtil.readString("Kuruluş tarihi (gg.aa.yyyy): ");
            tarih = DateUtil.parseDate(tarihStr);

            // Tarih kontrolü: Hem formatın doğru olması hem de tarihin mantıklı (gelecek olmaması vb.) olması gerekir
            if (tarih != null && DateUtil.isMantikliTarih(tarih)) {
                break;
            } else if (tarih == null) {
                System.out.println("Tarih formatı hatalı!");
            } else {
                System.out.println("Geçersiz tarih! Gelecek bir tarih veya çok eski bir tarih giremezsiniz.");
            }
        }

        Bolum bolum = new Bolum(ad, web, tarih);

        if (bolumService.bolumEkle(bolum)) {
            System.out.println("Bölüm başarıyla eklendi.");
        } else {
            System.out.println("Bölüm eklenemedi (İsim boş veya zaten mevcut olabilir).");
        }
    }

    private void bolumSil() {
        String ad = InputUtil.readString("Silinecek bölüm adı: ");

        boolean ogrenciVarMi = ogrenciService.ogrenciListele().stream()
                .anyMatch(o -> o.getBolum().getAd().equalsIgnoreCase(ad));

        if (ogrenciVarMi) {
            System.out.println("Hata: Bu bölüme kayıtlı öğrenciler var! Önce öğrencileri silmeli veya taşımalısınız.");
        } else {
            if (bolumService.bolumSil(ad)) {
                System.out.println("Bölüm başarıyla silindi.");
            } else {
                System.out.println("Bölüm bulunamadı.");
            }
        }
    }

    private void bolumListele() {
        List<Bolum> bolumler = bolumService.bolumListele();

        if (bolumler.isEmpty()) {
            System.out.println("Kayıtlı bölüm yok.");
            return;
        }

        ConsoleUtil.printLine();
        for (Bolum bolum : bolumler) {
            System.out.println("Bölüm Adı      : " + bolum.getAd());
            System.out.println("Web Sayfası   : " + bolum.getWebSayfasi());
            System.out.println("Kuruluş Tarihi: " + DateUtil.formatDate(bolum.getKuruluşTarihi()));
            ConsoleUtil.printLine();
        }
    }
}