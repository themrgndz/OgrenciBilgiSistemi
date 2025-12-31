package Service;

import Model.Ogrenci;
import java.util.ArrayList;
import java.util.List;

public class OgrenciService {

    private List<Ogrenci> ogrenciler;

    public OgrenciService() {
        this.ogrenciler = new ArrayList<>();
    }

    public boolean ogrenciEkle(Ogrenci ogrenci) {
        if (ogrenci == null) {
            return false;
        }

        if (ogrenciVarMi(ogrenci.getOgrenciNo())) {
            return false;
        }

        ogrenciler.add(ogrenci);
        return true;
    }

    public boolean ogrenciSil(int ogrenciNo) {
        Ogrenci ogrenci = ogrenciAra(ogrenciNo);
        if (ogrenci == null) {
            return false;
        }

        ogrenciler.remove(ogrenci);
        return true;
    }

    public Ogrenci ogrenciAra(int ogrenciNo) {
        for (Ogrenci ogrenci : ogrenciler) {
            if (ogrenci.getOgrenciNo() == ogrenciNo) {
                return ogrenci;
            }
        }
        return null;
    }

    public List<Ogrenci> ogrenciListele() {
        return ogrenciler;
    }

    public boolean ogrenciVarMi(int ogrenciNo) {
        return ogrenciAra(ogrenciNo) != null;
    }

    public boolean ogrenciGuncelle(Ogrenci ogrenci) {
        if (ogrenci == null) {
            return false;
        }

        Ogrenci eskiOgrenci = ogrenciAra(ogrenci.getOgrenciNo());
        if (eskiOgrenci == null) {
            return false;
        }

        eskiOgrenci.setIsim(ogrenci.getIsim());
        eskiOgrenci.setSoyisim(ogrenci.getSoyisim());
        return true;
    }
}
