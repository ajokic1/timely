package com.example.andrej.timely;

import java.util.Date;

/**
 * Created by kokik on 6.5.2017..
 */

public class NeodredjenaObaveza extends Obaveza {
    Date rok; //Do kad treba da se zavrsi
    private boolean visePuta; //Ako se obaveza ne moze zavrsiti odjednom
    //Ako je ovo true, ne brise se iz Neodredjenih



    public Date getRok() {
        return rok;
    }

    public void setRok(Date rok) {
        this.rok = rok;
    }

    public boolean isVisePuta() {
        return visePuta;
    }

    public void setVisePuta(boolean visePuta) {
        this.visePuta = visePuta;
    }

    public NeodredjenaObaveza(Date rok, boolean visePuta) {
        this.rok = rok;
        this.visePuta = visePuta;
    }

    public NeodredjenaObaveza(String naziv, boolean ispunjena, float trajanje, String komentar, Date rok, boolean visePuta) {
        super(naziv, ispunjena, trajanje, komentar);
        this.rok = rok;
        this.visePuta = visePuta;
    }
}
