package Model;

import java.time.LocalDate;

/**
 * Ogrenci sınıfı, sistemdeki bir öğrencinin temel bilgilerini temsil eder.
 */
public class Ogrenci {

    private String isim;
    private String soyisim;
    private int ogrenciNo;
    private LocalDate dogumTarihi;
    private Bolum bolum;

    /**
     * Ogrenci nesnesi oluşturur.
     *
     * @param isim öğrencinin adı
     * @param soyisim öğrencinin soyadı
     * @param ogrenciNo öğrencinin numarası
     * @param dogumTarihi öğrencinin doğum tarihi
     * @param bolum öğrencinin bağlı olduğu bölüm
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
     */
    public String getIsim() {
        return isim;
    }

    /**
     * Öğrencinin adını günceller.
     *
     * @param isim atanmış isim
     */
    public void setIsim(String isim) {
        this.isim = isim;
    }

    /**
     * Öğrencinin soyadını döndürür.
     */
    public String getSoyisim() {
        return soyisim;
    }

    /**
     * Öğrencinin soyadını günceller.
     *
     * @param soyisim atanmış soyisim
     */
    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    /**
     * Öğrencinin bölüm bilgisini döndürür.
     */
    public Bolum getBolum() {
        return bolum;
    }

    /**
     * Öğrencinin doğum tarihini döndürür.
     */
    public LocalDate getDogumTarihi() {
        return dogumTarihi;
    }

    /**
     * Öğrencinin numarasını döndürür.
     */
    public int getOgrenciNo() {
        return ogrenciNo;
    }
}
