package Service;

import Model.Ders;
import java.util.ArrayList;
import java.util.List;

public class DersService {

    private List<Ders> dersler;

    public DersService() {
        this.dersler = new ArrayList<>();
    }

    public boolean dersEkle(Ders ders) {
        if (ders == null) {
            return false;
        }

        if (dersVarMi(ders.getKod())) {
            return false;
        }

        dersler.add(ders);
        return true;
    }

    public boolean dersSil(String dersKodu) {
        Ders ders = dersAra(dersKodu);
        if (ders == null) {
            return false;
        }

        dersler.remove(ders);
        return true;
    }

    public Ders dersAra(String dersKodu) {
        for (Ders ders : dersler) {
            if (ders.getKod().equalsIgnoreCase(dersKodu)) {
                return ders;
            }
        }
        return null;
    }

    public List<Ders> dersListele() {
        return dersler;
    }

    public boolean dersVarMi(String dersKodu) {
        return dersAra(dersKodu) != null;
    }

    public boolean dersGuncelle(Ders ders) {
        if (ders == null) {
            return false;
        }

        Ders eskiDers = dersAra(ders.getKod());
        if (eskiDers == null) {
            return false;
        }

        eskiDers.setAd(ders.getAd());
        // AKTS değişebilir kabul ediliyor
        // Kod immutable → setter YOK (bilinçli tasarım)
        return true;
    }
}
