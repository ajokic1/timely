package com.example.andrej.timely;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    private int chartData[] = {10,50};
    PieChart pieChart;
    List<DnevnaObaveza> obaveze;

    ArrayList<PieEntry> podaci;
    PieDataSet pieDataSet;
    PieEntry p1;
    PieEntry p2;
    int broj;
    //PieData pieData;
    //Obaveza ob;

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    /*ob = new Obaveza(
                            intent.getStringExtra(editPost.IME),
                            intent.getStringExtra(editPost.FAKULTET),
                            R.drawable.froot,
                            intent.getStringExtra(editPost.TEKST));
                    postovip.add(post);
                    adapter1p.notifyDataSetChanged();*/


                }
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Text view - koliko je ispunjeno
        final TextView ispunjenoObaveza = (TextView)findViewById(R.id.pocIspunjenoText);
        ispunjenoObaveza.setText(Integer.toString(chartData[0]) + "/" + Integer.toString(chartData[1]));

        //Grafik
        Log.d(TAG, "onCreate: pravim chart");
        pieChart = (PieChart) findViewById(R.id.MainPieChart);
        addDataSet();

        //Dodajem obaveze
        //TODO: Kod za ucitavanje danasnjih obaveza
        obaveze = new LinkedList<>();
        DnevnaObaveza ob1 = new DnevnaObaveza("Kupi paprike",false,4,"Proba", new Date(2017,05,06), true, new Time(14,12,0));
        DnevnaObaveza ob2 = new DnevnaObaveza("Zavrsi aplikaciju", true, 2, "ProbaProba", new Date(2017,05,06), true, new Time(11,43,0));
        obaveze.add(ob1);
        obaveze.add(ob2);
        obaveze.add(ob1);
        obaveze.add(ob2);
        obaveze.add(ob2);
        chartData[1] =obaveze.size();

        //Lista
        final ListView listView = (ListView)findViewById(R.id.mainLista);
        listView.setItemsCanFocus(true);
        chartData[0]=0;
        final ArrayAdapter<DnevnaObaveza> adapter = new ArrayAdapter<DnevnaObaveza>(
                this, R.layout.pocetna_item, obaveze){
            @NonNull
            @Override
            public View getView(final int position, View view, ViewGroup parent) {
                if(view ==null){
                    view = getLayoutInflater().inflate(R.layout.pocetna_item, parent,false);
                }
                DnevnaObaveza obaveza = obaveze.get(position);
                ((CheckBox)view.findViewById(R.id.PocItemCheck)).setText(obaveza.getNaziv());
                ((CheckBox)view.findViewById(R.id.PocItemCheck)).setChecked(obaveza.isIspunjena());
                ((TextView)view.findViewById(R.id.pocItemTrajanje)).setText(trajanjeString(obaveza.getTrajanje()));
                ((TextView)view.findViewById(R.id.pocItemVrijeme)).setText(obaveza.getVrijeme().toString());

                CheckBox chk = (CheckBox)view.findViewById(R.id.PocItemCheck);
                if(obaveza.isIspunjena())chartData[0]++;
                ispunjenoObaveza.setText(Integer.toString(chartData[0]) + "/" + Integer.toString(chartData[1]));
                refreshChart();

                chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) chartData[0]++;
                        else chartData[0]--;
                        ispunjenoObaveza.setText(Integer.toString(chartData[0]) + "/" + Integer.toString(chartData[1]));
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

        //TODO: Ucitaj obaveze za danas i adapter.notifyDataSetChanged()
        //Da prebroji strikirane dok ih ucitava
        chartData[1]=obaveze.size();

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                obaveze.get(position).setIspunjena(((CheckBox)view.findViewById(R.id.PocItemCheck)).isChecked());
                //^Postavlja ischecked trenutne obaveze na vrijednost checkboxa
                //TODO: Nekako da se izbroje checkovani i updatuje ChartData
                chartData[0]=0;
                for(int i=0;i<obaveze.size();i++){
                    if(obaveze.get(i).isIspunjena())chartData[0]++;
                }
                ispunjenoObaveza.setText(Integer.toString(chartData[0]) + "/" + Integer.toString(chartData[1]));

            }
        });*/

        //FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Pozovi Unos Activity
                Intent intent = new Intent(MainActivity.this, Unos.class);
                startActivityForResult(intent,1);
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
        //p1 = new PieEntry(chartData[0],0);
        //p2 = new PieEntry(chartData[1],1);
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

        return super.onOptionsItemSelected(item);
    }

    public static String trajanjeString(float t){
        String s="";
        if((int)t != 0) s=Integer.toString((int)t) + " h";
        float minuti = t-(int)t;
        if(t-(int)t !=0){
            int minutiInt;
            minutiInt = (int)(minuti*60);
            s=s+" "+ minutiInt + " m";
        }




        return s;
    }
}
