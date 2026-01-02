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
 * Bölüm işlemlerine ait kullanıcı arayüzünü (alt menü) yöneten sınıf.
 * <p>
 * Bu sınıf aracılığıyla kullanıcı; sisteme yeni bölümler ekleyebilir,
 * mevcut bölümleri silebilir veya tüm bölümleri listeleyebilir.
 * </p>
 */
public class BolumMenu {

    private final OgrenciService ogrenciService;
    private final BolumService bolumService;

    /**
     * BolumMenu nesnesi oluşturur.
     *
     * @param bolumService   Bölüm verilerini yöneten servis.
     * @param ogrenciService Öğrenci kontrolü yapan servis.
     */
    public BolumMenu(BolumService bolumService, OgrenciService ogrenciService) {
        this.bolumService = bolumService;
        this.ogrenciService = ogrenciService;
    }

    /**
     * Bölüm işlemleri menü döngüsünü başlatır.
     * <p>
     * Kullanıcı "geri" yazana kadar aktif kalır ve seçilen işleme göre
     * ilgili metodları (ekle, sil, listele) çağırır.
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
                        System.out.println("Geçersiz seçim! Lütfen menüdeki rakamlardan birini kullanın.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Hata: Lütfen geçerli bir seçim (rakam veya 'geri') yapınız!");
            }

            ConsoleUtil.waitForEnter();
        }
    }

    /**
     * Bölüm işlemleri seçeneklerini ekrana yazdırır.
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
     * Kullanıcıdan alınan bilgilerle sisteme yeni bir bölüm ekler.
     * <p>
     * Bölüm adı ve web sayfası bilgilerini aldıktan sonra, kuruluş tarihi için
     * {@link DateUtil} kullanarak format sorunu ve mantık hatalarının olmaması sağlanır. (01.01.2099 yılında doğmuş biri gibi)
     * </p>
     */
    private void bolumEkle() {
        System.out.println("\n--- Mevcut Bölümler ---");
        bolumListele();

        String ad = InputUtil.readOnlyText("Bölüm adı: ");
        String web = InputUtil.readString("Web sayfası: ");

        LocalDate tarih;
        while (true) {
            String tarihStr = InputUtil.readString("Kuruluş tarihi (gg.aa.yyyy): ");
            tarih = DateUtil.parseDate(tarihStr);

            if (tarih != null && DateUtil.isMantikliTarih(tarih)) {
                break;
            } else if (tarih == null) {
                System.out.println("Hata: Tarih formatı hatalı (Örn: 29.10.1923)!");
            } else {
                System.out.println("Geçersiz tarih! Gelecek bir tarih veya çok eski bir tarih giremezsiniz.");
            }
        }

        Bolum bolum = new Bolum(ad, web, tarih);

        if (bolumService.bolumEkle(bolum)) {
            System.out.println("Bölüm başarıyla eklendi.");
        } else {
            System.out.println("Bölüm eklenemedi (İsim boş veya bu isimde bir bölüm zaten mevcut).");
        }
    }

    /**
     * Belirtilen bölümü sistemden siler.
     * <p>
     * Silme işleminden önce, ilgili bölüme kayıtlı öğrenci olup olmadığını kontrol eder.
     * Eğer bölüme bağlı öğrenciler varsa, veri bütünlüğünü korumak adına silme işlemine izin vermez.
     * </p>
     */
    private void bolumSil() {
        String ad = InputUtil.readString("Silinecek bölüm adı: ");

        boolean ogrenciVarMi = ogrenciService.ogrenciListele().stream()
                .anyMatch(o -> o.getBolum().getAd().equalsIgnoreCase(ad));

        if (ogrenciVarMi) {
            System.out.println("Hata: Bu bölüme kayıtlı öğrenciler var! Önce öğrencileri silmeli veya başka bölüme taşımalısınız.");
        } else {
            if (bolumService.bolumSil(ad)) {
                System.out.println("Bölüm başarıyla silindi.");
            } else {
                System.out.println("Bölüm bulunamadı.");
            }
        }
    }

    /**
     * Sistemde kayıtlı olan tüm bölümleri detaylı bir şekilde listeler.
     */
    private void bolumListele() {
        List<Bolum> bolumler = bolumService.bolumListele();

        if (bolumler.isEmpty()) {
            System.out.println("Kayıtlı bölüm bulunmamaktadır.");
            return;
        }

        ConsoleUtil.printLine();
        for (Bolum bolum : bolumler) {
            System.out.println("Bölüm Adı      : " + bolum.getAd());
            System.out.println("Web Sayfası   : " + bolum.getWebSayfasi());
            System.out.println("Kuruluş Tarihi: " + DateUtil.formatDate(bolum.getKurulusTarihi()));
            ConsoleUtil.printLine();
        }
    }
}