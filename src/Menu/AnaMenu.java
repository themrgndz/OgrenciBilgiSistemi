package Menu;

import Service.BolumService;
import Service.DersService;
import Service.GpaService;
import Service.OgrenciService;
import Util.ConsoleUtil;
import Util.InputUtil;

/**
 * Programın ana menüsünü yöneten sınıf.
 * <p>
 * Kullanıcıya Bölüm, Öğrenci, Ders ve Not/GPA işlemleri için
 * seçenekler sunar. Seçilen menüye göre ilgili alt menü başlatılır.
 * Kullanıcı "son" yazarak programı sonlandırabilir.
 * </p>
 */
public class AnaMenu {

    private final OgrenciService ogrenciService;
    private final DersService dersService;
    private final BolumService bolumService;
    private final GpaService gpaService;

    /**
     * AnaMenu constructor.
     *
     * @param ogrenciService Öğrenci işlemlerini yöneten servis.
     * @param dersService    Ders işlemlerini yöneten servis.
     * @param bolumService   Bölüm işlemlerini yöneten servis.
     * @param gpaService     Not/GPA işlemlerini yöneten servis.
     */
    public AnaMenu(OgrenciService ogrenciService,
                   DersService dersService,
                   BolumService bolumService,
                   GpaService gpaService) {

        this.ogrenciService = ogrenciService;
        this.dersService = dersService;
        this.bolumService = bolumService;
        this.gpaService = gpaService;
    }

    /**
     * Ana menü seçeneklerini ekrana yazdırır.
     */
    private void menuYazdir() {
        System.out.println("+---------------------------------------+");
        System.out.println("|                 MENÜ                  |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   1 - Bölüm İşlemleri                 |");
        System.out.println("|   2 - Öğrenci İşlemleri               |");
        System.out.println("|   3 - Ders İşlemleri                  |");
        System.out.println("|   4 - Not / GPA İşlemleri             |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   son - Programı Bitir                |");
        System.out.println("+---------------------------------------+");
    }

    /**
     * Menü döngüsünü başlatır ve kullanıcı etkileşimini yönetir.
     * <p>
     * Kullanıcı seçimlerine göre ilgili alt menüler başlatılır.
     * "son" seçeneği ile program sonlandırılır.
     * </p>
     */
    public void baslat() {
        while (true) {
            menuYazdir();

            String secim = InputUtil.readString("Seçiminiz: ");

            if (secim.equalsIgnoreCase("son")) {
                System.out.println("Program sonlandırılıyor... Güle güle kral 👋");
                return;
            }

            try {
                int secimNo = Integer.parseInt(secim);

                switch (secimNo) {
                    case 1:
                        new BolumMenu(bolumService).baslat();
                        break;
                    case 2:
                        new OgrenciMenu(ogrenciService, bolumService).baslat();
                        break;
                    case 3:
                        new DersMenu(dersService).baslat();
                        break;
                    case 4:
                        new GpaMenu(gpaService, ogrenciService, dersService).baslat();
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
}
