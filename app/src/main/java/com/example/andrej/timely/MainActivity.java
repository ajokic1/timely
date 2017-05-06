package com.example.andrej.timely;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    private int chartData[] = {10,15};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Log.d(TAG, "onCreate: pravim chart");
        pieChart = (PieChart) findViewById(R.id.MainPieChart);
        addDataSet();

    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet: Dodajem podatke chartu");
        ArrayList<PieEntry> podaci = new ArrayList<>();
        podaci.add(new PieEntry(chartData[0],0));
        podaci.add(new PieEntry(chartData[1],1));

        //Create PieDataSet
        PieDataSet pieDataSet = new PieDataSet(podaci, "Ispunjene obaveze");
        pieDataSet.setSliceSpace(0);


        //PieDataSet preferences
        pieDataSet.setColors(new int []{getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent)});
        pieChart.setDrawCenterText(false);
        pieDataSet.setDrawValues(false);
        pieChart.setDescription(null);
        pieChart.setRotationEnabled(false);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        //Create PieData object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
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
}
