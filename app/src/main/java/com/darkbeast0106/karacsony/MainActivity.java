package com.darkbeast0106.karacsony;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView szamlalo;
    private Timer myTimer;
    private Date karacsony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        szamlalo = findViewById(R.id.szamlalo);
        Calendar most = Calendar.getInstance();
        int ev = most.get(Calendar.YEAR);
        int honap = most.get(Calendar.MONTH);
        int nap = most.get(Calendar.DATE);
        if (honap == 11 && nap >= 24){
            ev++;
        }
        Calendar karacsony = Calendar.getInstance();
        karacsony.set(ev, 11, 24,0,0,0);
        this.karacsony = karacsony.getTime();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date most = Calendar.getInstance().getTime();
                long hatralevoIdo = karacsony.getTime() - most.getTime();

                long masodPercMili = 1000;
                long percMili = masodPercMili * 60;
                long oraMili = percMili * 60;
                long napMili = oraMili * 24;

                long nap = hatralevoIdo / napMili;
                hatralevoIdo = hatralevoIdo % napMili;
                long ora = hatralevoIdo / oraMili;
                hatralevoIdo = hatralevoIdo % oraMili;
                long perc = hatralevoIdo / percMili;
                hatralevoIdo = hatralevoIdo % percMili;
                long masodperc = hatralevoIdo / masodPercMili;

                String hatralevoSzoveg = getString(R.string.szamlaloFormatum, nap, ora, perc, masodperc);
                runOnUiThread(() -> szamlalo.setText(hatralevoSzoveg));
            }
        };
        myTimer.schedule(task, 0, 500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myTimer.cancel();
    }
}