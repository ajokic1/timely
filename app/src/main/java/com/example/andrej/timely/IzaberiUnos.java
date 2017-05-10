package com.example.andrej.timely;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Calendar;

public class IzaberiUnos extends AppCompatActivity {

    public static final String IZABRANO = "com.example.andrej.izabraniUnos";
    public static final String IZ_DATUM = "datum";
    public static final String IZ_NEODR = "neodr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izaberi_unos);

        RelativeLayout izaberiDanas = (RelativeLayout)findViewById(R.id.izaberiDanas);
        RelativeLayout izaberiSjutra = (RelativeLayout)findViewById(R.id.izaberiSjutra);
        RelativeLayout izaberiDatum = (RelativeLayout)findViewById(R.id.izaberiDatum);
        RelativeLayout izaberiNeodr = (RelativeLayout)findViewById(R.id.izaberiNeodr);

        izaberiDanas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IzaberiUnos.this, Unos.class);
                Calendar c = Calendar.getInstance();
                Datum datum = new Datum(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH),
                        c.get(Calendar.YEAR));
                intent.putExtra(IZABRANO, Integer.toString(datum.getIntDatum()));
                startActivity(intent);
            }
        });
        izaberiSjutra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IzaberiUnos.this, Unos.class);
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_YEAR,1); //Dodaj jedan dan = sjutra
                Datum datum = new Datum(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH),
                        c.get(Calendar.YEAR));
                intent.putExtra(IZABRANO, Integer.toString(datum.getIntDatum()));
                startActivity(intent);
            }
        });
        izaberiDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IzaberiUnos.this, Unos.class);
                intent.putExtra(IZABRANO, IZ_DATUM);
                startActivity(intent);
            }
        });
        izaberiNeodr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IzaberiUnos.this, Unos.class);
                intent.putExtra(IZABRANO, IZ_NEODR);
                startActivity(intent);
            }
        });


    }
}
