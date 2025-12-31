package Menu;

import Model.Bolum;
import Service.BolumService;
import Util.ConsoleUtil;
import Util.InputUtil;
import Util.DateUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * Bölüm işlemleri menüsünü yöneten sınıf.
 * <p>
 * Bu sınıf, bölüm ekleme, silme ve listeleme işlemlerini kullanıcıya sunar.
 * BolumService kullanılarak işlemler gerçekleştirilir.
 * Kullanıcı "geri" yazarak AnaMenu'ye dönebilir.
 * </p>
 */
public class BolumMenu {

    private final BolumService bolumService;

    /**
     * BolumMenu constructor.
     *
     * @param bolumService Bölüm işlemlerini yöneten servis.
     */
    public BolumMenu(BolumService bolumService) {
        this.bolumService = bolumService;
    }

    /**
     * Menü döngüsünü başlatır ve kullanıcı etkileşimini yönetir.
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

    /**
     * Menü seçeneklerini ekrana yazdırır.
     */
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
     */
    private void bolumEkle() {
        String ad = InputUtil.readString("Bölüm adı: ");
        String web = InputUtil.readString("Web sayfası: ");

        LocalDate tarih;
        while (true) {
            String tarihStr = InputUtil.readString("Kuruluş tarihi (gg.aa.yyyy): ");
            tarih = DateUtil.parseDate(tarihStr);
            if (tarih != null) {
                break;
            }
            System.out.println("Tarih formatı hatalı!");
        }

        Bolum bolum = new Bolum(ad, web, tarih);

        if (bolumService.bolumEkle(bolum)) {
            System.out.println("Bölüm başarıyla eklendi.");
        } else {
            System.out.println("Bölüm eklenemedi (zaten mevcut olabilir).");
        }
    }

    /**
     * Kullanıcıdan bölüm adı alarak bölümü siler.
     */
    private void bolumSil() {
        String ad = InputUtil.readString("Silinecek bölüm adı: ");

        if (bolumService.bolumSil(ad)) {
            System.out.println("Bölüm silindi.");
        } else {
            System.out.println("Bölüm bulunamadı.");
        }
    }

    /**
     * Sistemde kayıtlı tüm bölümleri listeler.
     */
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
            System.out.println("Kuruluş Tarihi: " + bolum.getKuruluşTarihi());
            ConsoleUtil.printLine();
        }
    }
}
