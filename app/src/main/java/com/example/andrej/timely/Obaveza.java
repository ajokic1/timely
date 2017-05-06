package com.example.andrej.timely;

import java.util.Date;

/**
 * Created by kokik on 6.5.2017..
 */

public class Obaveza {
    private String naziv;
    private boolean ispunjena;
    private float trajanje; //trajanje u satima 1 = 1 sat, 0.5 = 30 minuta...
    private String komentar;


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isIspunjena() {
        return ispunjena;
    }

    public void setIspunjena(boolean ispunjena) {
        this.ispunjena = ispunjena;
    }

    public float getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(float trajanje) {
        this.trajanje = trajanje;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Obaveza() {
    }

    public Obaveza(String naziv, boolean ispunjena, int trajanje, String komentar) {
        this.naziv = naziv;
        this.ispunjena = ispunjena;
        this.trajanje = trajanje;
        this.komentar = komentar;
    }
}
