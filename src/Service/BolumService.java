package Service;

import Model.Bolum;
import java.util.ArrayList;
import java.util.List;

public class BolumService {

    private List<Bolum> bolumler;

    public BolumService() {
        this.bolumler = new ArrayList<>();
    }

    public boolean bolumEkle(Bolum bolum) {
        if (bolum == null) {
            return false;
        }

        if (bolumVarMi(bolum.getAd())) {
            return false;
        }

        bolumler.add(bolum);
        return true;
    }

    public boolean bolumSil(String bolumAdi) {
        Bolum bolum = bolumAra(bolumAdi);
        if (bolum == null) {
            return false;
        }

        bolumler.remove(bolum);
        return true;
    }

    public Bolum bolumAra(String bolumAdi) {
        for (Bolum bolum : bolumler) {
            if (bolum.getAd().equalsIgnoreCase(bolumAdi)) {
                return bolum;
            }
        }
        return null;
    }

    public List<Bolum> bolumListele() {
        return bolumler;
    }

    public boolean bolumVarMi(String bolumAdi) {
        return bolumAra(bolumAdi) != null;
    }

    public boolean bolumGuncelle(Bolum bolum) {
        if (bolum == null) {
            return false;
        }

        Bolum eskiBolum = bolumAra(bolum.getAd());
        if (eskiBolum == null) {
            return false;
        }

        eskiBolum.setWebSayfasi(bolum.getWebSayfasi());
        // kuruluş tarihi değişmez kabul edildi
        return true;
    }
}
