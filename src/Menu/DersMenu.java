package Menu;

import Model.Ders;
import Service.DersService;
import Util.ConsoleUtil;
import Util.InputUtil;

import java.util.List;

/**
 * Ders işlemlerine ait kullanıcı arayüzünü (alt menü) yöneten sınıf.
 * <p>
 * Bu sınıf aracılığıyla kullanıcı; sisteme yeni dersler ekleyebilir,
 * mevcut dersleri silebilir ve kayıtlı tüm dersleri listeleyebilir.
 * Tüm işlemler {@link DersService} katmanı üzerinden gerçekleştirilir.
 * </p>
 * * @author kral
 * @version 1.0
 */
public class DersMenu {

    /**
     * Ders verilerini ve iş mantığını yöneten servis.
     */
    private final DersService dersService;

    /**
     * DersMenu nesnesi oluşturur ve gerekli servis bağımlılığını enjekte eder.
     *
     * @param dersService Ders işlemlerini yöneten servis katmanı.
     */
    public DersMenu(DersService dersService) {
        this.dersService = dersService;
    }

    /**
     * Ders işlemleri menü döngüsünü başlatır ve kullanıcı etkileşimini yönetir.
     * <p>
     * Kullanıcı "geri" komutunu girene kadar aktif kalır. Seçilen işleme göre
     * ilgili metodları (ekle, sil, listele) çağırır ve hatalı girişleri yönetir.
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
                        dersEkle();
                        break;
                    case 2:
                        dersSil();
                        break;
                    case 3:
                        dersListele();
                        break;
                    default:
                        System.out.println("Geçersiz seçim! Lütfen menüdeki rakamlardan birini giriniz.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Hata: Lütfen geçerli bir seçim (rakam veya 'geri') yapınız!");
            }

            ConsoleUtil.waitForEnter();
        }
    }

    /**
     * Ders işlemleri seçeneklerini görsel bir çerçeve içinde ekrana yazdırır.
     */
    private void menuYazdir() {
        System.out.println("+---------------------------------------+");
        System.out.println("|             DERS İŞLEMLERİ            |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   1 - Ders Ekle                        |");
        System.out.println("|   2 - Ders Sil                         |");
        System.out.println("|   3 - Dersleri Listele                 |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   geri - Ana Menüye Dön               |");
        System.out.println("+---------------------------------------+");
    }

    /**
     * Kullanıcıdan ders bilgilerini alarak sisteme yeni bir ders kaydeder.
     * <p>
     * Kullanıcıdan sırasıyla Ders Adı, Ders Kodu ve AKTS değerlerini alır.
     * İşlem başarılı olursa onay mesajı verir ve kullanıcıya yeni bir kayıt
     * eklemek isteyip istemediğini sorar.
     * </p>
     */
    private void dersEkle() {
        boolean devamEt = true;
        while (devamEt) {
            String ad = InputUtil.readOnlyText("Ders Adı: ");
            String kod = InputUtil.readString("Ders Kodu: ");
            int akts = InputUtil.readInt("AKTS: ");

            Ders ders = new Ders(ad, kod, akts);

            if (dersService.dersEkle(ders)) {
                System.out.println("Ders başarıyla eklendi.");
            } else {
                System.out.println("Hata: Ders eklenemedi (Kod zaten mevcut olabilir veya geçersiz veri).");
            }

            String yanit = InputUtil.readString("Yeni bir ders eklemek ister misiniz? (E/H): ");
            devamEt = yanit.equalsIgnoreCase("E");
        }
    }

    /**
     * Kullanıcıdan alınan ders koduna göre dersi sistemden siler.
     * * @see DersService#dersSil(String)
     */
    private void dersSil() {
        String kod = InputUtil.readString("Silinecek ders kodu: ");

        if (dersService.dersSil(kod)) {
            System.out.println("Ders başarıyla silindi.");
        } else {
            System.out.println("Hata: Belirtilen kodla bir ders bulunamadı.");
        }
    }

    /**
     * Sistemde kayıtlı olan tüm dersleri listeler.
     * <p>
     * Eğer sistemde kayıtlı ders yoksa kullanıcıyı bilgilendirir.
     * Kayıtlar varsa her dersin Adı, Kodu ve AKTS bilgisini ekrana yansıtır.
     * </p>
     */
    private void dersListele() {
        List<Ders> liste = dersService.dersListele();

        if (liste.isEmpty()) {
            System.out.println("Sistemde henüz kayıtlı bir ders bulunmamaktadır.");
            return;
        }

        ConsoleUtil.printLine();
        for (Ders ders : liste) {
            System.out.println("Ders Adı : " + ders.getAd());
            System.out.println("Ders Kodu: " + ders.getKod());
            System.out.println("AKTS     : " + ders.getAkts());
            ConsoleUtil.printLine();
        }
    }
}