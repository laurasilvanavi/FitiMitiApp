package com.example.laurute.fitimitiapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class DrinkActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager senManager;
    private Sensor accelerometer;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    private int mark;
    Button btn;

    int drinkRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        mark = 0;
        btn = (Button)findViewById(R.id.buttonContinue);

        senManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = senManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senManager.registerListener(this, accelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        Intent intent = getIntent();
        drinkRandom = intent.getIntExtra(GameActivity.DRINK_RANDOM, 0);
        drinkRandom++;
    }

    private void getRandomNumber() {
        Random randNumber = new Random();
        int number = randNumber.nextInt(9) + 1;

        TextView text = (TextView)findViewById(R.id.number);
        text.setText(""+number);
    }

    public void continueGame(View view){
        btn.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.DRINK_RANDOM, drinkRandom);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 200) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (mark == 0 && speed > SHAKE_THRESHOLD) {
                    getRandomNumber();
                    btn.setVisibility(View.VISIBLE);
                    mark++;
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // atlaisvinam resursus
    @Override
    protected void onPause() {
        super.onPause();
        senManager.unregisterListener(this);
    }

    // vel pajungiam akselerometra
    @Override
    protected void onResume() {
        super.onResume();
        senManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onBackPressed() {
    }
}
