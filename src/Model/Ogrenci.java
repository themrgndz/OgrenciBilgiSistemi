package Model;

import java.time.LocalDate;

/**
 * Üniversite bünyesindeki bir öğrenciyi temsil eden temel model sınıfıdır.
 * <p>
 * Bu sınıf; öğrencinin kişisel bilgilerini (isim, soyisim, doğum tarihi),
 * benzersiz öğrenci numarasını ve akademik olarak bağlı olduğu {@link Bolum}
 * nesnesini saklar.
 * </p>
 */
public class Ogrenci {

    /** Öğrencinin adı */
    private String isim;
    /** Öğrencinin soyadı */
    private String soyisim;
    /** Öğrencinin benzersiz 9 haneli okul numarası */
    private final int ogrenciNo;
    /** Öğrencinin doğum tarihi (LocalDate formatında) */
    private final LocalDate dogumTarihi;
    /** Öğrencinin kayıtlı olduğu akademik bölüm */
    private final Bolum bolum;

    /**
     * Yeni bir Ogrenci nesnesi oluşturur.
     *
     * @param isim         Öğrencinin adı
     * @param soyisim      Öğrencinin soyadı
     * @param ogrenciNo    Öğrencinin benzersiz okul numarası
     * @param dogumTarihi  Öğrencinin doğum tarihi
     * @param bolum        Öğrencinin bağlı olduğu bölüm nesnesi
     */
    public Ogrenci(String isim, String soyisim, int ogrenciNo,
                   LocalDate dogumTarihi, Bolum bolum) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.ogrenciNo = ogrenciNo;
        this.dogumTarihi = dogumTarihi;
        this.bolum = bolum;
    }

    /**
     * Öğrencinin adını döndürür.
     * @return Öğrenci adı
     */
    public String getIsim() {
        return isim;
    }

    /**
     * Öğrencinin adını günceller.
     *
     * @param isim Yeni atanacak isim
     */
    public void setIsim(String isim) {
        this.isim = isim;
    }

    /**
     * Öğrencinin soyadını döndürür.
     * @return Öğrenci soyadı
     */
    public String getSoyisim() {
        return soyisim;
    }

    /**
     * Öğrencinin soyadını günceller.
     *
     * @param soyisim Yeni atanacak soyisim
     */
    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    /**
     * Öğrencinin kayıtlı olduğu bölüm bilgisini döndürür.
     * @return {@link Bolum} nesnesi
     */
    public Bolum getBolum() {
        return bolum;
    }

    /**
     * Öğrencinin doğum tarihini döndürür.
     * @return Doğum tarihi (LocalDate)
     */
    public LocalDate getDogumTarihi() {
        return dogumTarihi;
    }

    /**
     * Öğrencinin benzersiz okul numarasını döndürür.
     * @return Öğrenci numarası
     */
    public int getOgrenciNo() {
        return ogrenciNo;
    }
}