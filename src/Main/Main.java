package Main;

import Menu.AnaMenu;
import Service.BolumService;
import Service.DersService;
import Service.GpaService;
import Service.OgrenciService;

public class Main {

    public static void main(String[] args) {

        // Service katmanı oluşturuluyor
        OgrenciService ogrenciService = new OgrenciService();
        DersService dersService = new DersService();
        BolumService bolumService = new BolumService();
        GpaService gpaService = new GpaService();

        // Ana menü başlatılıyor
        AnaMenu anaMenu = new AnaMenu(
                ogrenciService,
                dersService,
                bolumService,
                gpaService
        );

        anaMenu.baslat();
    }
}
