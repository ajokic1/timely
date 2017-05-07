package com.example.andrej.timely;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collection;

import java.util.List;

import static android.R.attr.startYear;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Unos extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText unosDatum;
    EditText unosVrijeme;
    View polje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unos);

        final EditText unosNaziv = (EditText)findViewById(R.id.unosNaziv);
        final EditText unosOpis = (EditText)findViewById(R.id.unosOpis);
        final EditText unosTrajanje = (EditText)findViewById(R.id.unosTrajanje);
        unosDatum = (EditText)findViewById(R.id.unosDatum);
        unosVrijeme = (EditText)findViewById(R.id.unosVrijeme);
        final EditText unosRok = (EditText)findViewById(R.id.unosRok);
        final Switch unosDatumSwitch = (Switch)findViewById(R.id.unosDatumSwitch);
        final Switch unosVrijemeSwitch = (Switch)findViewById(R.id.unosVrijemeSwitch);
        final TextView unosRokCap = (TextView)findViewById(R.id.unosRokCap);
        Button dodajBtn = (Button)findViewById(R.id.unosButtonDodaj);

        //unosDatum.setVisibility(GONE);
        //unosVrijeme.setVisibility(GONE);
        //unosVrijemeSwitch.setVisibility(GONE);
        unosRok.setVisibility(GONE);
        unosRokCap.setVisibility(GONE);

        //DatePicker
        Calendar c = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, Unos.this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));

        unosDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                polje=v;
                datePickerDialog.show();
            }
        });
        unosRok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                polje=v;
                datePickerDialog.show();
            }
        });


        //TimePicker
        final TimePickerDialog timePickerDialog = new TimePickerDialog(Unos.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                unosVrijeme.setText( selectedHour + ":" + selectedMinute);
            }
        }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), true);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
        unosVrijeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        unosDatumSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    unosRok.setVisibility(GONE);
                    unosRokCap.setVisibility(GONE);
                    unosDatum.setVisibility(VISIBLE);
                    unosVrijemeSwitch.setVisibility(VISIBLE);
                }else{
                    unosDatum.setVisibility(GONE);
                    unosVrijemeSwitch.setVisibility(GONE);
                    unosRokCap.setVisibility(VISIBLE);
                    unosRok.setVisibility(VISIBLE);
                }

            };

        });
        unosVrijemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    unosVrijeme.setVisibility(VISIBLE);
                }else unosVrijeme.setVisibility(GONE);
            }
        });

        dodajBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs = getSharedPreferences(MainActivity.PREF_FILE,0);
                Gson gson = new Gson();
                String json = mPrefs.getString(MainActivity.DANAS_OBAVEZE, "");
                Type type = new TypeToken<Collection<DnevnaObaveza>>(){}.getType();
                List<DnevnaObaveza> obaveze = gson.fromJson(json, type);


                if(unosDatumSwitch.isChecked()) {
                    DnevnaObaveza ob = new DnevnaObaveza(
                            unosNaziv.getText().toString(),
                            false,
                            Float.parseFloat(unosTrajanje.getText().toString()),
                            unosOpis.getText().toString(),
                            new Datum(unosDatum.getText().toString()),
                            unosVrijemeSwitch.isChecked(),
                            new Vrijeme(
                                    Integer.parseInt(unosVrijeme.getText().toString().substring(0,1)),
                                    Integer.parseInt(unosVrijeme.getText().toString().substring(3,4))
                            )
                    );
                    obaveze.add(ob);
                }else{
                    //TODO: Kreiraj objekat tipa NeodredjenaObaveza i dodaj u listu neodredjenih, sacuvaj u fajl
                }

                //sacuvaj promijenjenu listu
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                json = gson.toJson(obaveze);
                prefsEditor.putString(MainActivity.DANAS_OBAVEZE, json);
                prefsEditor.apply();

                //pozovi MainActivity
                Intent intent = new Intent(Unos.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        ((EditText)polje).setText((dayOfMonth<10?"0":"") + dayOfMonth+"."+ (month<10?"0":"") + month+"."+year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}