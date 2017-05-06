package com.example.andrej.timely;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

import static android.R.attr.startYear;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Unos extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText unosDatum;
    EditText unosVrijeme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unos);

        EditText unosNaziv = (EditText)findViewById(R.id.unosNaziv);
        EditText unosOpis = (EditText)findViewById(R.id.unosOpis);
        EditText unosTrajanje = (EditText)findViewById(R.id.unosTrajanje);
        unosDatum = (EditText)findViewById(R.id.unosDatum);
        unosVrijeme = (EditText)findViewById(R.id.unosVrijeme);
        final EditText unosRok = (EditText)findViewById(R.id.unosRok);
        final Switch unosDatumSwitch = (Switch)findViewById(R.id.unosDatumSwitch);
        final Switch unosVrijemeSwitch = (Switch)findViewById(R.id.unosVrijemeSwitch);
        final TextView unosRokCap = (TextView)findViewById(R.id.unosRokCap);
        Button dodajBtn = (Button)findViewById(R.id.unosButtonDodaj);

        unosDatum.setVisibility(GONE);
        unosVrijeme.setVisibility(GONE);
        unosVrijemeSwitch.setVisibility(GONE);

        //DatePicker
        Calendar c = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, Unos.this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        unosDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if(unosDatumSwitch.isChecked()){
                    //TODO: Kreiraj objekat tipa DnevnaObaveza i sacuvaj u fajl za taj datum
                    if(unosVrijemeSwitch.isChecked()){
                        //TODO:setuj vrijeme kreiranom objektu
                    }
                }else{
                    //TODO: Kreiraj objekat tipa NeodredjenaObaveza i dodaj u listu neodredjenih, sacuvaj u fajl
                }



                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        unosDatum.setText(dayOfMonth+"/"+month+"/"+year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}