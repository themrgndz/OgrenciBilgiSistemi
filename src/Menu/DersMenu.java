package Menu;

import Model.Ders;
import Service.DersService;
import Util.ConsoleUtil;
import Util.InputUtil;

import java.util.List;

/**
 * Ders işlemleri menüsünü yöneten sınıf.
 * <p>
 * Bu sınıf, ders ekleme, silme ve listeleme işlemlerini kullanıcıya sunar.
 * DersService kullanılarak işlemler gerçekleştirilir.
 * Kullanıcı "geri" yazarak AnaMenu'ye dönebilir.
 * </p>
 */
public class DersMenu {

    private final DersService dersService;

    /**
     * DersMenu constructor.
     *
     * @param dersService Ders işlemlerini yöneten servis.
     */
    public DersMenu(DersService dersService) {
        this.dersService = dersService;
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
                        dersEkle();
                        break;
                    case 2:
                        dersSil();
                        break;
                    case 3:
                        dersListele();
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
     * Kullanıcıdan ders bilgisi alarak yeni ders ekler.
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
                System.out.println("Ders eklenemedi.");
            }

            String yanit = InputUtil.readString("Yeni bir ders eklemek ister misiniz? (E/H): ");
            devamEt = yanit.equalsIgnoreCase("E");
        }
    }

    /**
     * Kullanıcıdan ders kodu alarak dersi siler.
     */
    private void dersSil() {
        String kod = InputUtil.readString("Silinecek ders kodu: ");

        if (dersService.dersSil(kod)) {
            System.out.println("Ders silindi.");
        } else {
            System.out.println("Ders bulunamadı.");
        }
    }

    /**
     * Sistemde kayıtlı tüm dersleri listeler.
     */
    private void dersListele() {
        List<Ders> liste = dersService.dersListele();

        if (liste.isEmpty()) {
            System.out.println("Kayıtlı ders yok.");
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
