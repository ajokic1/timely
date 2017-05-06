package com.example.andrej.timely;

import java.sql.Time;
import java.util.Date;

/**
 * Created by kokik on 6.5.2017..
 */

public class DnevnaObaveza extends Obaveza {
    private Date datum;
    private boolean vrijemePoznato;
    private Time vrijeme;

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public boolean isVrijemePoznato() {
        return vrijemePoznato;
    }

    public void setVrijemePoznato(boolean vrijemePoznato) {
        this.vrijemePoznato = vrijemePoznato;
    }

    public Time getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Time vrijeme) {
        this.vrijeme = vrijeme;
    }

    public DnevnaObaveza(Date datum, boolean vrijemePoznato, Time vrijeme) {
        this.datum = datum;
        this.vrijemePoznato = vrijemePoznato;
        this.vrijeme = vrijeme;
    }

    public DnevnaObaveza(String naziv, boolean ispunjena, int trajanje, String komentar, Date datum, boolean vrijemePoznato, Time vrijeme) {
        super(naziv, ispunjena, trajanje, komentar);
        this.datum = datum;
        this.vrijemePoznato = vrijemePoznato;
        this.vrijeme = vrijeme;
    }
}
