package com.example.andrej.timely;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.andrej.timely.MainActivity.PREF_FILE;

public class Neodredjene extends AppCompatActivity {
    ArrayAdapter<NeodredjenaObaveza> adapter;
    List<NeodredjenaObaveza> obaveze = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neodredjene);


        //Ucitavanje spiska obaveza
        SharedPreferences mPrefs = getSharedPreferences(PREF_FILE,0);
        Gson gson = new Gson();
        String json = mPrefs.getString(IzaberiUnos.IZ_NEODR, "");
        Type type = new TypeToken<Collection<NeodredjenaObaveza>>(){}.getType();
        obaveze = gson.fromJson(json, type);
        if(obaveze==null) obaveze = new ArrayList<NeodredjenaObaveza>();
        Collections.sort(obaveze, new Comparator<NeodredjenaObaveza>() {
            @Override
            public int compare(NeodredjenaObaveza o1, NeodredjenaObaveza o2) {
                return o1.getRok().getIntDatum()>o2.getRok().getIntDatum()?1:
                        o1.getRok().getIntDatum()<o2.getRok().getIntDatum()?-1:0;
            }
        });


        //Kreiranje liste
        ListView listView = (ListView)findViewById(R.id.neoLista);
        adapter = new ArrayAdapter<NeodredjenaObaveza>(this, R.layout.neo_item, obaveze){
            @NonNull
            @Override
            public View getView(final int position, View view, ViewGroup parent) {
                if(view ==null){
                    view = getLayoutInflater().inflate(R.layout.neo_item, parent,false);
                }
                final NeodredjenaObaveza obaveza = obaveze.get(position);
                ((TextView)view.findViewById(R.id.neoItemText)).setText(obaveza.getNaziv());
                ((TextView)view.findViewById(R.id.neoItemTrajanje)).setText(obaveza.trajanjeString());
                ((TextView)view.findViewById(R.id.neoItemDatum)).setText(obaveza.getRok().toString());

                ImageView dodaj = (ImageView)view.findViewById(R.id.neoDodaj);
                dodaj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String danas = getIntent().getStringExtra("danas");

                        List<DnevnaObaveza> ob;
                        SharedPreferences mPrefs = getSharedPreferences(PREF_FILE,0);
                        Gson gson = new Gson();
                        String json = mPrefs.getString(danas, "");
                        Type type = new TypeToken<Collection<DnevnaObaveza>>(){}.getType();
                        ob = gson.fromJson(json, type);
                        if(ob==null) ob = new ArrayList<DnevnaObaveza>();

                        ob.add(new DnevnaObaveza(
                                obaveza.getNaziv(),
                                false,
                                obaveza.getTrajanje(),
                                obaveza.getKomentar(),
                                new Datum(danas, true),
                                false,
                                null
                        ));

                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        json = gson.toJson(ob);
                        prefsEditor.putString(danas, json);
                        prefsEditor.apply();
                    }
                });

                ImageView obrisi = (ImageView)view.findViewById(R.id.neoItemBrisi);
                obrisi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obaveze.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                return view;
            }
        };
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences mPrefs = getSharedPreferences(PREF_FILE,0);
        Gson gson = new Gson();
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String json = gson.toJson(obaveze);
        prefsEditor.putString(IzaberiUnos.IZ_NEODR, json);
        prefsEditor.apply();
    }
}
