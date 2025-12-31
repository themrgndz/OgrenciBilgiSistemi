package Menu;

import Model.Ogrenci;
import Model.Bolum;
import Service.OgrenciService;
import Service.BolumService;
import Util.ConsoleUtil;
import Util.InputUtil;
import Util.DateUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * Öğrenci menüsünü yöneten sınıf.
 * <p>
 * Bu sınıf, öğrencilerle ilgili CRUD işlemlerini kullanıcıya sunar ve OgrenciService ile BolumService'i kullanır.
 * Menü üzerinden ekleme, silme, arama ve listeleme işlemleri yapılabilir.
 * Kullanıcı "geri" yazarak AnaMenu'ye dönebilir.
 * </p>
 */
public class OgrenciMenu {

    private final OgrenciService ogrenciService;
    private final BolumService bolumService;

    /**
     * OgrenciMenu constructor.
     *
     * @param ogrenciService Öğrenci işlemlerini yöneten servis.
     * @param bolumService   Bölüm işlemlerini yöneten servis.
     */
    public OgrenciMenu(OgrenciService ogrenciService, BolumService bolumService) {
        this.ogrenciService = ogrenciService;
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
                        ogrenciEkle();
                        break;
                    case 2:
                        ogrenciSil();
                        break;
                    case 3:
                        ogrenciAra();
                        break;
                    case 4:
                        ogrenciListele();
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
        System.out.println("|           ÖĞRENCİ İŞLEMLERİ           |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   1 - Öğrenci Ekle                    |");
        System.out.println("|   2 - Öğrenci Sil                     |");
        System.out.println("|   3 - Öğrenci Ara                     |");
        System.out.println("|   4 - Öğrencileri Listele             |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   geri - Ana Menüye Dön               |");
        System.out.println("+---------------------------------------+");
    }

    /**
     * Kullanıcıdan veri alarak yeni öğrenci ekler.
     * Bölüm kontrolü ve tarih formatı kontrolü içerir.
     */
    private void ogrenciEkle() {
        String isim = InputUtil.readString("İsim: ");
        String soyisim = InputUtil.readString("Soyisim: ");
        int no = InputUtil.readInt("Öğrenci No: ");

        Bolum bolum;
        while (true) {
            String bolumAdi = InputUtil.readString("Bölüm Adı: ");
            bolum = bolumService.bolumAra(bolumAdi);
            if (bolum != null) break;
            System.out.println("Bölüm bulunamadı!");
        }

        LocalDate tarih;
        while (true) {
            String tarihStr = InputUtil.readString("Doğum Tarihi (gg.aa.yyyy): ");
            tarih = DateUtil.parseDate(tarihStr);
            if (tarih != null) break;
            System.out.println("Tarih formatı hatalı!");
        }

        Ogrenci ogrenci = new Ogrenci(isim, soyisim, no, tarih, bolum);

        if (ogrenciService.ogrenciEkle(ogrenci)) {
            System.out.println("Öğrenci başarıyla eklendi.");
        } else {
            System.out.println("Öğrenci eklenemedi (zaten mevcut olabilir).");
        }
    }

    /**
     * Kullanıcıdan öğrenci numarası alarak öğrenciyi siler.
     */
    private void ogrenciSil() {
        int no = InputUtil.readInt("Silinecek öğrenci no: ");

        if (ogrenciService.ogrenciSil(no)) {
            System.out.println("Öğrenci silindi.");
        } else {
            System.out.println("Öğrenci bulunamadı.");
        }
    }

    /**
     * Kullanıcıdan öğrenci numarası alarak öğrenciyi arar ve detaylarını gösterir.
     */
    private void ogrenciAra() {
        int no = InputUtil.readInt("Aranacak öğrenci no: ");

        Ogrenci ogrenci = ogrenciService.ogrenciAra(no);

        if (ogrenci != null) {
            ConsoleUtil.printLine();
            System.out.println("İsim     : " + ogrenci.getIsim());
            System.out.println("Soyisim  : " + ogrenci.getSoyisim());
            System.out.println("Öğrenci No: " + ogrenci.getOgrenciNo());
            System.out.println("Doğum    : " + ogrenci.getDogumTarihi());
            System.out.println("Bölüm    : " + ogrenci.getBolum().getAd());
            ConsoleUtil.printLine();
        } else {
            System.out.println("Öğrenci bulunamadı.");
        }
    }

    /**
     * Tüm öğrencileri listeler.
     */
    private void ogrenciListele() {
        List<Ogrenci> liste = ogrenciService.ogrenciListele();

        if (liste.isEmpty()) {
            System.out.println("Kayıtlı öğrenci yok.");
            return;
        }

        ConsoleUtil.printLine();
        for (Ogrenci ogrenci : liste) {
            System.out.println("İsim     : " + ogrenci.getIsim());
            System.out.println("Soyisim  : " + ogrenci.getSoyisim());
            System.out.println("Öğrenci No: " + ogrenci.getOgrenciNo());
            System.out.println("Doğum    : " + ogrenci.getDogumTarihi());
            System.out.println("Bölüm    : " + ogrenci.getBolum().getAd());
            ConsoleUtil.printLine();
        }
    }
}
