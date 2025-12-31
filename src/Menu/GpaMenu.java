package Menu;

import Model.Ders;
import Model.Ogrenci;
import Service.GpaService;
import Service.OgrenciService;
import Service.DersService;
import Util.ConsoleUtil;
import Util.InputUtil;

import java.util.List;

/**
 * Not ve GPA işlemleri menüsünü yöneten sınıf.
 * <p>
 * Bu sınıf, öğrencilerin ders notlarını ekleme, görüntüleme, GPA hesaplama ve öğrencinin aldığı dersleri listeleme işlemlerini kullanıcıya sunar.
 * GpaService, OgrenciService ve DersService kullanılır.
 * Menü üzerinden işlemler yapılabilir ve kullanıcı "geri" yazarak AnaMenu'ye dönebilir.
 * </p>
 */
public class GpaMenu {

    private final GpaService gpaService;
    private final OgrenciService ogrenciService;
    private final DersService dersService;

    /**
     * GpaMenu constructor.
     *
     * @param gpaService     Not ve GPA işlemlerini yöneten servis.
     * @param ogrenciService Öğrenci işlemlerini yöneten servis.
     * @param dersService    Ders işlemlerini yöneten servis.
     */
    public GpaMenu(GpaService gpaService,
                   OgrenciService ogrenciService,
                   DersService dersService) {

        this.gpaService = gpaService;
        this.ogrenciService = ogrenciService;
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
                        notEkle();
                        break;
                    case 2:
                        notGuncelle();
                        break;
                    case 3:
                        notGoruntule();
                        break;
                    case 4:
                        gpaHesapla();
                        break;
                    case 5:
                        ogrencininDersleriniListele();
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
        System.out.println("|           NOT / GPA İŞLEMLERİ          |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   1 - Not Ekle                         |");
        System.out.println("|   2 - Not Güncelle                     |");
        System.out.println("|   3 - Not Görüntüle                    |");
        System.out.println("|   4 - GPA Hesapla                       |");
        System.out.println("|   5 - Öğrencinin Aldığı Dersler        |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   geri - Ana Menüye Dön               |");
        System.out.println("+---------------------------------------+");
    }

    /**
     * Kullanıcıdan öğrenci ve ders bilgisi alarak not ekler.
     */
    private void notEkle() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);
        if (ders == null) {
            System.out.println("Ders bulunamadı.");
            return;
        }

        int not = InputUtil.readInt("Not (0-100): ");
        if (gpaService.notEkle(ogrenci, ders, not)) {
            System.out.println("Not başarıyla eklendi.");
        } else {
            System.out.println("Not eklenemedi (zaten kayıtlı veya geçersiz).");
        }
    }

    /**
     * Kullanıcıdan öğrenci ve ders bilgisi alarak notu görüntüler.
     */
    private void notGoruntule() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);
        if (ders == null) {
            System.out.println("Ders bulunamadı.");
            return;
        }

        Integer not = gpaService.notBul(ogrenci, ders);
        if (not != null) {
            System.out.println("Not: " + not);
        } else {
            System.out.println("Kayıt bulunamadı.");
        }
    }

    /**
     * Kullanıcıdan öğrenci, ders ve yeni not bilgisini alarak güncelleme yapar.
     */
    private void notGuncelle() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        String dersKodu = InputUtil.readString("Ders Kodu: ");
        Ders ders = dersService.dersAra(dersKodu);
        if (ders == null) {
            System.out.println("Ders bulunamadı.");
            return;
        }

        int yeniNot = InputUtil.readInt("Yeni Not (0-100): ");
        if (gpaService.notGuncelle(ogrenci, ders, yeniNot)) {
            System.out.println("Not başarıyla güncellendi.");
        } else {
            System.out.println("Not güncellenemedi (Kayıt bulunamadı veya geçersiz not).");
        }
    }

    /**
     * Kullanıcıdan öğrenci numarası alarak GPA hesaplar ve ekrana yazdırır.
     */
    private void gpaHesapla() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        double gpa = gpaService.gpaHesapla(ogrenci);
        System.out.println("Öğrencinin GPA'sı: " + gpa);
    }

    /**
     * Kullanıcıdan öğrenci numarası alarak öğrencinin aldığı tüm dersleri listeler.
     */
    private void ogrencininDersleriniListele() {
        int ogrNo = InputUtil.readInt("Öğrenci No: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        List<Ders> dersler = gpaService.ogrencininDersleri(ogrenci);

        if (dersler.isEmpty()) {
            System.out.println("Öğrencinin aldığı ders yok.");
            return;
        }

        ConsoleUtil.printLine();
        for (Ders ders : dersler) {
            System.out.println("Ders Adı : " + ders.getAd());
            System.out.println("Ders Kodu: " + ders.getKod());
            System.out.println("AKTS     : " + ders.getAkts());
            ConsoleUtil.printLine();
        }
    }
}
