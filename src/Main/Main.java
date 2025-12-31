package Main;

import Menu.AnaMenu;
import Service.BolumService;
import Service.DersService;
import Service.GpaService;
import Service.OgrenciService;

/**
 * Programın giriş noktası (main sınıfı).
 * <p>
 * Bu sınıf çalıştırıldığında servis katmanları oluşturulur ve AnaMenu başlatılır. Kullanıcı, AnaMenu üzerinden Bölüm, Öğrenci, Ders ve Not/GPA işlemlerine erişebilir.
 * </p>
 */
public class Main {

    /**
     * Programın başlangıç metodu.
     * <p>
     * Servis katmanlarını başlatır ve AnaMenu'yi çalıştırır.
     * </p>
     */
    public static void main(String[] args) {

        OgrenciService ogrenciService = new OgrenciService();
        DersService dersService = new DersService();
        BolumService bolumService = new BolumService();
        GpaService gpaService = new GpaService();

        AnaMenu anaMenu = new AnaMenu(
                ogrenciService,
                dersService,
                bolumService,
                gpaService
        );

        anaMenu.baslat();
    }
}
