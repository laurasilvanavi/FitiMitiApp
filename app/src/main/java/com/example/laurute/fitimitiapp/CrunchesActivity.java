package com.example.laurute.fitimitiapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CrunchesActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv;
    Button mainButton, startButton;
    SensorManager sm;
    int moveNumber = 0;

    float count = 0;
    int minT = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunches);
        tv = (TextView) findViewById(R.id.textView3);
        startButton = (Button) findViewById(R.id.button9);
        tv.setText("Padaryta: " + (int) count);
        mainButton = (Button) findViewById(R.id.button11);
        mainButton.setText("Atsilenkimai\nPakartojimų: 15");
    }

    public void startButtonClick(View v) {
        if (startButton.getText().toString().contentEquals("Pradėti")) {
            startButton.setText("Baigti");
            doCrunches();
        }
        else if (startButton.getText().toString().contentEquals("Baigti")) {
            unregister();
            System.exit(0);
        }
    }



    private void doCrunches() {
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sm.getSensorList(Sensor.TYPE_GYROSCOPE).size() != 0) {
            Sensor s = sm.getSensorList(Sensor.TYPE_GYROSCOPE).get(0);
            sm.registerListener(this, s, 60000);
        }
        else {
            tv.setText("Atsiprašome, bet Jūsų įrenginys neturi reikiamo sensoriaus");
        }
    }

    private void unregister() {
        sm.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void skaiciuotiKartus(float sensorX, float sensorY, float sensorZ) {
            tv.setText(Float.toString(sensorY));
            if (sensorY > 4.0 || sensorY < -4.0) {
                sm.unregisterListener(this);
                count += 0.5f;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (count == 15) {
                            mainButton.setText("Treniruotė baigta");
                            tv.setText("");
                        }
                        else {
                            doCrunches();
                        }
                    }
                }, 200);
                if (count % 1 == 0)
                    tv.setText("Padaryta: " + (int) count + "");
            }
    }

    public void onSensorChanged(SensorEvent se) {
        float sensorX = se.values[0];
        float sensorY = se.values[1];
        float sensorZ = se.values[2];
        skaiciuotiKartus(sensorX, sensorY, sensorZ);
    }

}
