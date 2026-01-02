package Menu;

import Service.BolumService;
import Service.DersService;
import Service.GpaService;
import Service.OgrenciService;
import Util.ConsoleUtil;
import Util.InputUtil;

/**
 * Programın ana kullanıcı arayüzünü (konsol menüsü) yöneten sınıf.
 * <p>
 * Bu sınıf, kullanıcıya Bölüm, Öğrenci, Ders ve Not/GPA işlemlerine erişim sağlayan
 * merkezi bir kontrol noktası sunar. Kullanıcının girdiği komutlara göre ilgili
 * alt menüleri (BolumMenu, OgrenciMenu vb.) başlatır.
 * </p>
 */
public class AnaMenu {

    private final OgrenciService ogrenciService;
    private final DersService dersService;
    private final BolumService bolumService;
    private final GpaService gpaService;

    /**
     * AnaMenu nesnesi oluşturur ve gerekli servisleri enjekte eder.
     *
     * @param ogrenciService Öğrenci verileri ve mantığını yöneten servis.
     * @param dersService    Ders verileri ve mantığını yöneten servis.
     * @param bolumService   Bölüm verileri ve mantığını yöneten servis.
     * @param gpaService     Not hesaplama ve GPA işlemlerini yöneten servis.
     */
    public AnaMenu(OgrenciService ogrenciService, DersService dersService, BolumService bolumService, GpaService gpaService) {
        this.ogrenciService = ogrenciService;
        this.dersService = dersService;
        this.bolumService = bolumService;
        this.gpaService = gpaService;
    }

    /**
     * Ana menü seçeneklerini görsel bir formatta konsola yazdırır.
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
     * Ana menü döngüsünü başlatır.
     * <p>
     * Kullanıcı "son" yazana kadar döngü devam eder. Kullanıcıdan alınan girdiler
     * doğrultusunda ilgili servislerin menü sınıfları örneklenir ve çalıştırılır.
     * Geçersiz sayısal girişler veya metin girişleri için hata mesajı gösterir.
     * </p>
     */
    public void baslat() {
        while (true) {
            menuYazdir();

            String secim = InputUtil.readString("Seçiminiz: ");

            // Çıkış kontrolü
            if (secim.equalsIgnoreCase("son")) {
                System.out.println("Program sonlandırılıyor... Güle güle kral 👋");
                return;
            }

            try {
                int secimNo = Integer.parseInt(secim);

                switch (secimNo) {
                    case 1:
                        new BolumMenu(bolumService, ogrenciService).baslat();
                        break;
                    case 2:
                        new OgrenciMenu(ogrenciService, bolumService, gpaService).baslat();
                        break;
                    case 3:
                        new DersMenu(dersService).baslat();
                        break;
                    case 4:
                        new GpaMenu(gpaService, ogrenciService, dersService).baslat();
                        break;
                    default:
                        System.out.println("Geçersiz seçim! Lütfen listedeki rakamlardan birini giriniz.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Hata: Lütfen geçerli bir seçim (rakam veya 'son') yapınız!");
            }

            ConsoleUtil.waitForEnter();
        }
    }
}