package com.example.andrej.timely;

/**
 * Created by Andrej on 7.5.2017..
 */

public class Vrijeme {
    int sat;
    int minut;

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getMinut() {
        return minut;
    }

    public void setMinut(int minut) {
        this.minut = minut;
    }

    public String toString(){
        return (sat<10?"0":"")+sat+":"+(minut<10?"0":"")+minut;
    }

    public Vrijeme(int sat, int minut) {
        this.sat = sat;
        this.minut = minut;
    }
}
