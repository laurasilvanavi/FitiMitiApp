package com.example.laurute.fitimitiapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.rgb;

public class CrunchesActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv;
    Button startButton, infoButton;
    SensorManager sm;

    int crunchesToDo;
    float count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches);

        Intent intent = getIntent();
        String message = intent.getStringExtra(GameActivity.EXTRA_MESSAGE);
        try
        {
            int number = Integer.parseInt(message);
            crunchesToDo = number;
        }
        catch (NumberFormatException e)
        {
            crunchesToDo = 0;
        }

        startButton = (Button)findViewById(R.id.buttonCrunch);
        infoButton = (Button)findViewById(R.id.button11);

        tv = (TextView)findViewById(R.id.crunch);
        tv.setText(Integer.toString((int)count)+"/"+crunchesToDo);
    }

    public void startButtonClick(View v) {
        if (startButton.getText().toString().contentEquals("Pradėti")) {
            startButton.setText("Tęsti žaidimą");
            startButton.setVisibility(View.INVISIBLE);
            doCrunches();
        }
        else if (startButton.getText().toString().contentEquals("Tęsti žaidimą")) {
            unregister();
            startButton.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void doCrunches() {
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sm.getSensorList(Sensor.TYPE_GYROSCOPE).size() != 0) {
            Sensor s = sm.getSensorList(Sensor.TYPE_GYROSCOPE).get(0);
            sm.registerListener(this, s, 60000);
        }
        else {
            tv.setText("");
            infoButton.setText("Atsiprašome, bet Jūsų įrenginys neturi reikiamo sensoriaus");
            infoButton.setTextColor(rgb(255, 102, 102));
            //Toast.makeText(getApplicationContext(), "Atsiprašome, bet Jūsų įrenginys neturi reikiamo sensoriaus", Toast.LENGTH_LONG).show();
            startButton.setVisibility(View.VISIBLE);
        }
    }

    private void unregister() {
        sm.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void countRepeat(float sensorX, float sensorY, float sensorZ) {
            if (sensorY > 3.0 || sensorY < -3.0) {
                sm.unregisterListener(this);
                count += 0.5f;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (count == crunchesToDo) {
                            startButton.setVisibility(View.VISIBLE);
                            tv.setTextColor(rgb(255, 102, 102));
                        }
                        else {
                            doCrunches();
                        }
                    }
                }, 200);
                if (count % 1 == 0)
                    tv.setText(Integer.toString((int)count)+"/"+crunchesToDo);
            }
    }

    public void onSensorChanged(SensorEvent se) {
        float sensorX = se.values[0];
        float sensorY = se.values[1];
        float sensorZ = se.values[2];
        countRepeat(sensorX, sensorY, sensorZ);
    }

}
