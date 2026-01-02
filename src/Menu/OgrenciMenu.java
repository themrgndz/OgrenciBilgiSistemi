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
 * Öğrencilerle ilgili CRUD işlemlerini sunar. Bölüm seçimi numaralandırılmış liste üzerinden yapılır.
 * </p>
 */
public class OgrenciMenu {

    private final OgrenciService ogrenciService;
    private final BolumService bolumService;

    public OgrenciMenu(OgrenciService ogrenciService, BolumService bolumService) {
        this.ogrenciService = ogrenciService;
        this.bolumService = bolumService;
    }

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
                    case 5:
                        ogrenciGuncelle();
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

    private void menuYazdir() {
        System.out.println("+---------------------------------------+");
        System.out.println("|           ÖĞRENCİ İŞLEMLERİ           |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   1 - Öğrenci Ekle                    |");
        System.out.println("|   2 - Öğrenci Sil                     |");
        System.out.println("|   3 - Öğrenci Ara                     |");
        System.out.println("|   4 - Öğrencileri Listele             |");
        System.out.println("|   5 - Öğrenci Güncelle                |");
        System.out.println("+---------------------------------------+");
        System.out.println("|   geri - Ana Menüye Dön               |");
        System.out.println("+---------------------------------------+");
    }

    /**
     * Numaralandırılmış bölüm seçimi ve tarih kontrolü ile öğrenci ekler.
     */
    private void ogrenciEkle() {
        boolean devamEt = true;

        while (devamEt) {
            System.out.println("\n--- Yeni Öğrenci Kaydı ---");

            List<Bolum> bolumler = bolumService.bolumListele();
            if (bolumler.isEmpty()) {
                System.out.println("Sistemde kayıtlı bölüm yok! Önce Bölüm İşlemleri menüsünden bir bölüm eklemelisiniz.");
                return;
            }

            String isim = InputUtil.readOnlyText("İsim: ");
            String soyisim = InputUtil.readOnlyText("Soyisim: ");
            int no = InputUtil.readInt("Öğrenci No: ");

            System.out.println("\nLütfen bir bölüm seçiniz:");
            for (int i = 0; i < bolumler.size(); i++) {
                System.out.println((i + 1) + " - " + bolumler.get(i).getAd());
            }

            Bolum bolum = null;
            while (bolum == null) {
                int secim = InputUtil.readInt("Bölüm No: ");
                if (secim > 0 && secim <= bolumler.size()) {
                    bolum = bolumler.get(secim - 1);
                } else {
                    System.out.println("Geçersiz seçim! Lütfen listedeki numaralardan birini girin.");
                }
            }

            LocalDate tarih;
            while (true) {
                String tarihStr = InputUtil.readString("Doğum Tarihi (gg.aa.yyyy): ");
                tarih = DateUtil.parseDate(tarihStr);

                if (tarih != null && DateUtil.isMantikliTarih(tarih)) {
                    break;
                } else if (tarih == null) {
                    System.out.println("Tarih formatı hatalı!");
                } else {
                    System.out.println("Geçersiz tarih! Gelecek bir tarih veya çok eski bir tarih giremezsiniz.");
                }
            }

            Ogrenci ogrenci = new Ogrenci(isim, soyisim, no, tarih, bolum);

            if (ogrenciService.ogrenciEkle(ogrenci)) {
                System.out.println("Öğrenci (" + bolum.getAd() + " bölümüne) başarıyla eklendi.");
            } else {
                System.out.println("Öğrenci eklenemedi (Numara zaten mevcut olabilir veya 9 hane kuralına uymuyor).");
            }

            String yanit = InputUtil.readString("\nYeni bir öğrenci daha eklemek ister misiniz? (E/H): ");
            devamEt = yanit.equalsIgnoreCase("E");
        }
    }

    /**
     * Öğrenci bilgilerini güncelleyen metot.
     */
    private void ogrenciGuncelle() {
        int no = InputUtil.readInt("Güncellenecek öğrenci no: ");
        Ogrenci mevcutOgrenci = ogrenciService.ogrenciAra(no);

        if (mevcutOgrenci == null) {
            System.out.println("Öğrenci bulunamadı.");
            return;
        }

        System.out.println("\nMevcut Bilgiler: " + mevcutOgrenci.getIsim() + " " + mevcutOgrenci.getSoyisim());
        System.out.println("--- Yeni Bilgileri Giriniz (İptal için Enter) ---");

        String yeniIsim = InputUtil.readOnlyText("Yeni İsim: ");
        String yeniSoyisim = InputUtil.readOnlyText("Yeni Soyisim: ");

        Ogrenci guncelOgrenci = new Ogrenci(yeniIsim, yeniSoyisim, no,
                mevcutOgrenci.getDogumTarihi(),
                mevcutOgrenci.getBolum());

        if (ogrenciService.ogrenciGuncelle(guncelOgrenci)) {
            System.out.println("Öğrenci bilgileri başarıyla güncellendi.");
        } else {
            System.out.println("Güncelleme sırasında bir hata oluştu.");
        }
    }

    private void ogrenciSil() {
        int no = InputUtil.readInt("Silinecek öğrenci no: ");
        if (ogrenciService.ogrenciSil(no)) {
            System.out.println("Öğrenci silindi.");
        } else {
            System.out.println("Öğrenci bulunamadı.");
        }
    }

    private void ogrenciAra() {
        int no = InputUtil.readInt("Aranacak öğrenci no: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(no);

        if (ogrenci != null) {
            ConsoleUtil.printLine();
            System.out.println("İsim     : " + ogrenci.getIsim());
            System.out.println("Soyisim  : " + ogrenci.getSoyisim());
            System.out.println("Öğrenci No: " + ogrenci.getOgrenciNo());
            System.out.println("Doğum    : " + DateUtil.formatDate(ogrenci.getDogumTarihi()));
            System.out.println("Bölüm    : " + ogrenci.getBolum().getAd());
            ConsoleUtil.printLine();
        } else {
            System.out.println("Öğrenci bulunamadı.");
        }
    }

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
            System.out.println("Doğum    : " + DateUtil.formatDate(ogrenci.getDogumTarihi()));
            System.out.println("Bölüm    : " + ogrenci.getBolum().getAd());
            ConsoleUtil.printLine();
        }
    }
}