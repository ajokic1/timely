package com.example.andrej.timely;

import java.sql.Time;
import java.sql.Date;

/**
 * Created by kokik on 6.5.2017..
 */

public class DnevnaObaveza extends Obaveza {
    private Datum datum;
    private boolean vrijemePoznato;
    private Vrijeme vrijeme;

    public Datum getDatum() {
        return datum;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }

    public boolean isVrijemePoznato() {
        return vrijemePoznato;
    }

    public void setVrijemePoznato(boolean vrijemePoznato) {
        this.vrijemePoznato = vrijemePoznato;
    }

    public Vrijeme getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Vrijeme vrijeme) {
        this.vrijeme = vrijeme;
    }

    public DnevnaObaveza(Datum datum, boolean vrijemePoznato, Vrijeme vrijeme) {
        this.datum = datum;
        this.vrijemePoznato = vrijemePoznato;
        this.vrijeme = vrijeme;
    }

    public DnevnaObaveza(String naziv, boolean ispunjena, float trajanje, String komentar, Datum datum, boolean vrijemePoznato, Vrijeme vrijeme) {
        super(naziv, ispunjena, trajanje, komentar);
        this.datum = datum;
        this.vrijemePoznato = vrijemePoznato;
        this.vrijeme = vrijeme;
    }
}
