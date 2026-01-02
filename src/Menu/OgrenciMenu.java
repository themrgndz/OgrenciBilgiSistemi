package Menu;

import Model.Ogrenci;
import Model.Bolum;
import Service.GpaService;
import Service.OgrenciService;
import Service.BolumService;
import Util.ConsoleUtil;
import Util.InputUtil;
import Util.DateUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * Öğrenci işlemlerine ait kullanıcı arayüzünü (alt menü) yöneten sınıf.
 * <p>
 * Bu sınıf aracılığıyla kullanıcı; sisteme yeni öğrenciler ekleyebilir (CRUD),
 * mevcut öğrenci bilgilerini güncelleyebilir, öğrenci silebilir veya arama yapabilir.
 * Bölüm seçimi sistemdeki mevcut bölümler üzerinden numaralandırılmış bir liste ile yapılır.
 * </p>
 * @author kral
 * @version 1.0
 */
public class OgrenciMenu {

    /** Öğrenci verilerini ve iş mantığını yöneten servis. */
    private final OgrenciService ogrenciService;
    /** Öğrenciye atanacak bölümlerin listelenmesi için kullanılan servis. */
    private final BolumService bolumService;
    /** Öğrenci silindiğinde notlarını temizlemek için kullanılan servis. */
    private final GpaService gpaService;

    /**
     * OgrenciMenu nesnesi oluşturur ve gerekli servis bağımlılıklarını enjekte eder.
     *
     * @param ogrenciService Öğrenci işlemlerini yöneten servis katmanı.
     * @param bolumService   Bölüm verilerine erişim sağlayan servis katmanı.
     * @param gpaService     Not temizleme işlemleri için GPA servis katmanı.
     */
    public OgrenciMenu(OgrenciService ogrenciService, BolumService bolumService, GpaService gpaService) {
        this.ogrenciService = ogrenciService;
        this.bolumService = bolumService;
        this.gpaService = gpaService;
    }

    /**
     * Öğrenci işlemleri menü döngüsünü başlatır ve kullanıcı etkileşimini yönetir.
     * <p>
     * Kullanıcı "geri" komutunu girene kadar aktif kalır. Seçilen işleme göre
     * ilgili CRUD metodlarını çağırır ve sayısal olmayan hatalı girişleri yönetir.
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
                        System.out.println("Geçersiz seçim! Lütfen menüdeki rakamlardan birini giriniz.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Hata: Lütfen geçerli bir seçim (rakam veya 'geri') yapınız!");
            }

            ConsoleUtil.waitForEnter();
        }
    }

    /**
     * Öğrenci işlemleri seçeneklerini görsel bir çerçeve içinde ekrana yazdırır.
     */
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
     * Kullanıcıdan detaylı bilgiler alarak sisteme yeni bir öğrenci kaydeder.
     * <p>
     * İşlem sırasında şu kontroller yapılır:
     * 1. Sistemde en az bir bölümün kayıtlı olması gerekir.
     * 2. Öğrenci numarası tam olarak 9 haneli ve sistemde benzersiz olmalıdır.
     * 3. Doğum tarihi geçerli bir formatta ve mantıklı bir aralıkta olmalıdır.
     * </p>
     */
    private void ogrenciEkle() {
        boolean devamEt = true;

        while (devamEt) {
            System.out.println("\n--- Yeni Öğrenci Kaydı ---");

            List<Bolum> bolumler = bolumService.bolumListele();
            if (bolumler.isEmpty()) {
                System.out.println("Hata: Sistemde kayıtlı bölüm yok! Önce Bölüm İşlemleri menüsünden bir bölüm eklemelisiniz.");
                return;
            }

            String isim = InputUtil.readOnlyText("İsim: ");
            String soyisim = InputUtil.readOnlyText("Soyisim: ");
            int no;
            while (true) {
                no = InputUtil.readInt("Öğrenci No: ");

                if (String.valueOf(no).length() != 9) {
                    System.out.println("Hata: Öğrenci numarası 9 haneli olmalıdır (Örn: 192113001)!");
                    continue;
                }

                if (ogrenciService.ogrenciVarMi(no)) {
                    System.out.println("Hata: Bu öğrenci numarası sistemde zaten kayıtlı!");
                    continue;
                }
                break;
            }

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
                    System.out.println("Hata: Tarih formatı hatalı (Örn: 15.05.2000)!");
                } else {
                    System.out.println("Geçersiz tarih! Gelecek bir tarih veya çok eski bir tarih giremezsiniz.");
                }
            }

            Ogrenci ogrenci = new Ogrenci(isim, soyisim, no, tarih, bolum);

            if (ogrenciService.ogrenciEkle(ogrenci)) {
                System.out.println("Öğrenci (" + bolum.getAd() + " bölümüne) başarıyla eklendi.");
            } else {
                System.out.println("Hata: Öğrenci eklenemedi.");
            }

            String yanit = InputUtil.readString("\nYeni bir öğrenci daha eklemek ister misiniz? (E/H): ");
            devamEt = yanit.equalsIgnoreCase("E");
        }
    }

    /**
     * Belirtilen öğrenci numarasına sahip öğrencinin bilgilerini günceller.
     * <p>
     * Sadece İsim ve Soyisim bilgilerinin güncellenmesine izin verilir;
     * öğrenci numarası, doğum tarihi ve bölüm bilgisi sabit kalır.
     * </p>
     */
    private void ogrenciGuncelle() {
        int no = InputUtil.readInt("Güncellenecek öğrenci no: ");
        Ogrenci mevcutOgrenci = ogrenciService.ogrenciAra(no);

        if (mevcutOgrenci == null) {
            System.out.println("Hata: Öğrenci bulunamadı.");
            return;
        }

        System.out.println("\nMevcut Bilgiler: " + mevcutOgrenci.getIsim() + " " + mevcutOgrenci.getSoyisim());
        System.out.println("--- Yeni Bilgileri Giriniz ---");

        String yeniIsim = InputUtil.readOnlyText("Yeni İsim: ");
        String yeniSoyisim = InputUtil.readOnlyText("Yeni Soyisim: ");

        Ogrenci guncelOgrenci = new Ogrenci(yeniIsim, yeniSoyisim, no,
                mevcutOgrenci.getDogumTarihi(),
                mevcutOgrenci.getBolum());

        if (ogrenciService.ogrenciGuncelle(guncelOgrenci)) {
            System.out.println("Öğrenci bilgileri başarıyla güncellendi.");
        } else {
            System.out.println("Hata: Güncelleme sırasında bir hata oluştu.");
        }
    }

    /**
     * Belirtilen numaraya sahip öğrenciyi ve o öğrenciye ait tüm not kayıtlarını siler.
     */
    private void ogrenciSil() {
        int no = InputUtil.readInt("Silinecek öğrenci no: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(no);

        if (ogrenci != null) {
            gpaService.notlariTemizle(ogrenci);
            if (ogrenciService.ogrenciSil(no)) {
                System.out.println("Öğrenci ve ilgili tüm not kayıtları sistemden silindi.");
            }
        } else {
            System.out.println("Hata: Öğrenci bulunamadı.");
        }
    }

    /**
     * Numarası girilen öğrencinin tüm detaylı bilgilerini ekrana yansıtır.
     */
    private void ogrenciAra() {
        int no = InputUtil.readInt("Aranacak öğrenci no: ");
        Ogrenci ogrenci = ogrenciService.ogrenciAra(no);

        if (ogrenci != null) {
            ConsoleUtil.printLine();
            System.out.println("İsim       : " + ogrenci.getIsim());
            System.out.println("Soyisim    : " + ogrenci.getSoyisim());
            System.out.println("Öğrenci No : " + ogrenci.getOgrenciNo());
            System.out.println("Doğum Tarihi: " + DateUtil.formatDate(ogrenci.getDogumTarihi()));
            System.out.println("Bölüm      : " + ogrenci.getBolum().getAd());
            ConsoleUtil.printLine();
        } else {
            System.out.println("Hata: Öğrenci bulunamadı.");
        }
    }

    /**
     * Sistemde kayıtlı olan tüm öğrencileri detaylı bir şekilde listeler.
     */
    private void ogrenciListele() {
        List<Ogrenci> liste = ogrenciService.ogrenciListele();
        if (liste.isEmpty()) {
            System.out.println("Sistemde kayıtlı öğrenci bulunmamaktadır.");
            return;
        }

        ConsoleUtil.printLine();
        for (Ogrenci ogrenci : liste) {
            System.out.println("İsim       : " + ogrenci.getIsim());
            System.out.println("Soyisim    : " + ogrenci.getSoyisim());
            System.out.println("Öğrenci No : " + ogrenci.getOgrenciNo());
            System.out.println("Doğum Tarihi: " + DateUtil.formatDate(ogrenci.getDogumTarihi()));
            System.out.println("Bölüm      : " + ogrenci.getBolum().getAd());
            ConsoleUtil.printLine();
        }
    }
}