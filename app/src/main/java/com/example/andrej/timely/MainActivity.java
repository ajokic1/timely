package com.example.andrej.timely;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private static String TAG = "MainActivity";
    public static final String DANAS_OBAVEZE = "com.example.andrej.obaveze_lista";
    public static final String PREF_FILE = "com.example.andrej.preference_file";

    private int chartData[] = {10,50};
    PieChart pieChart;
    List<DnevnaObaveza> obaveze=null;

    ArrayList<PieEntry> podaci;
    PieDataSet pieDataSet;
    PieEntry p1;
    PieEntry p2;
    int broj;
    ArrayAdapter<DnevnaObaveza> adapter;
    String danas;
    TextView ispunjenoObaveza;
    //PieData pieData;
    //Obaveza ob;

    /*protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    ucitajObaveze();
                    adapter.notifyDataSetChanged();
                    refreshChart();


                }
                break;
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar c = Calendar.getInstance();
        danas = Integer.toString(new Datum(c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH),c.get(Calendar.YEAR)).getIntDatum());

        //Dodajem obaveze
        //TODO: Kod za ucitavanje danasnjih obaveza
        ucitajObaveze();

        //Text view - koliko je ispunjeno
        ispunjenoObaveza = (TextView)findViewById(R.id.pocIspunjenoText);
        ispunjenoObaveza.setText(Integer.toString(chartData[0]) + "/" + Integer.toString(chartData[1]));

        //Grafik
        Log.d(TAG, "onCreate: pravim chart");
        pieChart = (PieChart) findViewById(R.id.MainPieChart);
        addDataSet();




        //Lista
        final ListView listView = (ListView)findViewById(R.id.mainLista);
        listView.setItemsCanFocus(true);
        adapter = new ArrayAdapter<DnevnaObaveza>(
                this, R.layout.pocetna_item, obaveze){
            @NonNull
            @Override
            public View getView(final int position, View view, ViewGroup parent) {
                if(view ==null){
                    view = getLayoutInflater().inflate(R.layout.pocetna_item, parent,false);
                }
                final DnevnaObaveza obaveza = obaveze.get(position);
                ((CheckBox)view.findViewById(R.id.PocItemCheck)).setChecked(obaveza.isIspunjena());
                ((TextView)view.findViewById(R.id.pocItemTrajanje)).setText(obaveza.trajanjeString());
                ((TextView)view.findViewById(R.id.PocItemText)).setText(obaveza.getNaziv());
                //if(position%2==0)(view.findViewById(R.id.itemLinLay)).setBackgroundColor(getResources()
                //        .getColor(R.color.svijetloSiva));
                if(obaveza.isVrijemePoznato())((TextView)view.findViewById(R.id.pocItemVrijeme))
                        .setText(obaveza.getVrijeme().toString());
                else
                    ((TextView)view.findViewById(R.id.pocItemVrijeme)).setVisibility(View.INVISIBLE);

                CheckBox chk = (CheckBox)view.findViewById(R.id.PocItemCheck);
                ispunjenoObaveza.setText(Integer.toString(chartData[0]) + "/" + Integer.toString(chartData[1]));
                refreshChart();

                chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            chartData[0]++;
                            obaveza.setIspunjena(true);
                        }
                        else {
                            chartData[0]--;
                            obaveza.setIspunjena(false);
                        }
                        ispunjenoObaveza.setText(Integer.toString(chartData[0]) + "/" + Integer.toString(chartData[1]));
                        refreshChart();
                    }
                });

                ImageView obrisi = (ImageView)view.findViewById(R.id.pocItemBrisi);
                obrisi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obaveze.remove(position);
                        adapter.notifyDataSetChanged();
                        chartData[1]--;
                        refreshChart();
                    }
                });

                return view;
            }
        };



        listView.setAdapter(adapter);
        refreshChart();
        adapter.notifyDataSetChanged();


        ispunjenoObaveza.setText(Integer.toString(chartData[0]) + "/" + Integer.toString(chartData[1]));





        //FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Pozovi Unos Activity
                Intent intent = new Intent(MainActivity.this, IzaberiUnos.class);
                startActivity(intent);
            }
        });



    }

    //Dataset za grafik
    private void addDataSet() {
        Log.d(TAG, "addDataSet: Dodajem podatke chartu");
        podaci = new ArrayList<>();
        p1=new PieEntry(chartData[0],0);
        p2=new PieEntry(chartData[1],1);
        podaci.add(p1);
        podaci.add(p2);

        //Create PieDataSet
        pieDataSet = new PieDataSet(podaci, "Ispunjene obaveze");
        pieDataSet.setSliceSpace(0);


        //PieDataSet preferences
        pieDataSet.setColors(new int []{getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.siva)});
        pieChart.setHoleColor(getResources().getColor(R.color.colorPrimary));

        pieChart.setDrawCenterText(false);
        pieDataSet.setDrawValues(false);
        pieChart.setDescription(null);
        pieChart.setRotationEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(75);
        pieChart.setTransparentCircleAlpha(0);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        //Create PieData object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
    private void refreshChart(){
        ispunjenoObaveza.setText(Integer.toString(chartData[0]) + "/" + Integer.toString(chartData[1]));
        p1.setY(chartData[0]);
        p2.setY(chartData[1]-chartData[0]);

        pieChart.notifyDataSetChanged();
        //pieDataSet.notifyDataSetChanged();
        pieChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.menuObrisiSve) {
            //TODO: Vidi zasto clear() ne radi
            obaveze.clear();
            adapter.notifyDataSetChanged();
            chartData[0]=0;
            chartData[1]=0;
            refreshChart();
        }
        if (id == R.id.menuNeodredjene){
            Intent intent = new Intent(MainActivity.this, Neodredjene.class);
            intent.putExtra("danas", danas);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Sacuvavam obaveze");
        SharedPreferences mPrefs = getSharedPreferences(PREF_FILE,0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obaveze);
        prefsEditor.putString(danas, json);
        prefsEditor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume pozvan");
        ucitajObaveze();
        adapter.notifyDataSetChanged();
        refreshChart();
    }

    private void ucitajObaveze(){
        SharedPreferences mPrefs = getSharedPreferences(PREF_FILE,0);
        Gson gson = new Gson();
        String json = mPrefs.getString(danas, "");
        Type type = new TypeToken<Collection<DnevnaObaveza>>(){}.getType();
        Log.d(TAG, "Ucitavam obaveze");
        if(obaveze == null) obaveze = new ArrayList<DnevnaObaveza>();
        obaveze.clear();
        obaveze.addAll((ArrayList<DnevnaObaveza>)gson.fromJson(json, type));
        if(obaveze==null) obaveze = new ArrayList<DnevnaObaveza>();
        chartData[1] = obaveze.size();
        chartData[0] = 0;
        for(int i=0; i<obaveze.size();i++){
            if(obaveze.get(i).isIspunjena()) chartData[0]++;
        }





    }
}
