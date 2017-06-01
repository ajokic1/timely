package com.example.andrej.timely;

/**
 * Created by Andrej on 7.5.2017..
 */

public class Datum {
    private int dan;
    private int mjesec;
    private int godina;
    private int intDatum;

    public int getDan() {
        return dan;
    }

    public void setDan(int dan) {
        this.dan = dan;
        intDatum = godina*10000+mjesec*100+dan;
    }

    public int getMjesec() {
        return mjesec;
    }

    public void setMjesec(int mjesec) {
        this.mjesec = mjesec;
        intDatum = godina*10000+mjesec*100+dan;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
        intDatum = godina*10000+mjesec*100+dan;
    }

    public int getIntDatum() {
        return intDatum;
    }

    public String toString(){
        String s = Integer.toString(intDatum);
        try{return s.substring(6,8) + "." + s.substring(4,6) + "." + s.substring(0,4);}
        catch (StringIndexOutOfBoundsException e){
            return "";
        }
    }



    public Datum(int dan, int mjesec, int godina) {
        this.dan = dan;
        this.mjesec = mjesec;
        this.godina = godina;
        this.intDatum = godina*10000+mjesec*100+dan;
    }
    public Datum(String datum){
        dan = Integer.parseInt(datum.substring(0,1));
        mjesec = Integer.parseInt(datum.substring(3,4));
        dan = Integer.parseInt(datum.substring(6,9));
    }
    public Datum(String intdatum, boolean flag){
        //Za datum u int formatu
        dan = Integer.parseInt(intdatum.substring(6,7));
        mjesec = Integer.parseInt(intdatum.substring(4,5));
        godina = Integer.parseInt(intdatum.substring(0,3));
        intDatum = Integer.parseInt(intdatum);
    }
}
