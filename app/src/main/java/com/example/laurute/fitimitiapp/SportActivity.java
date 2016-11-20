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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.rgb;

public class SportActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager senManager;
    private Sensor accelerometer;

    // isbandysim
    private boolean mInitialized;

    TextView xCoor; // declare X axis object
    TextView yCoor; // declare Y axis object
    TextView zCoor; // declare Z xis object

    private float[] mGravity = { 0.0f, 0.0f, 0.0f };
    private float[] delta = { 0.0f, 0.0f, 0.0f };
    long startTime = 0;
    private int MIN_SQUAT = 600;
    private int MAX_SQUAT = 1800;

    int squatCount;
    int counter;
    int between;
    int up;
    int squatToDo;
    int drinkRandom;

    float work = 0.0f;

    TextView texto;
    Button btn, infoButton;

    // private int TOTAL = 0;
    // private int SQUAT_TIME = 0;
    // int mark = 0;
    //private float startSpeed = 0;
    // private ArrayList<String> speedList = new ArrayList<String>();
    // private ArrayList<Float> speedL = new ArrayList<Float>();
    // private int MAX_TIME = 2200;
    /*private float sudDec = -400;
    private float sudInc = 400;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        Intent intent = getIntent();
        String message = intent.getStringExtra(GameActivity.EXTRA_MESSAGE);
        try
        {
            int number = Integer.parseInt(message);
            squatToDo = number;
        }
        catch (NumberFormatException e)
        {
            squatToDo = 0;
        }
        drinkRandom = intent.getIntExtra(GameActivity.DRINK_RANDOM, 0);
        drinkRandom++;

        btn = (Button)findViewById(R.id.buttonSport);
        infoButton = (Button)findViewById(R.id.prepar);

        mInitialized = false;
        counter = 0;
        squatCount = 0;
        between = 0;
        up = 0;
        texto = (TextView)findViewById(R.id.squat);
        texto.setText(Integer.toString(squatCount)+"/"+squatToDo);

       /* xCoor=(TextView)findViewById(R.id.xcoor); // create X axis object
        yCoor=(TextView)findViewById(R.id.ycoor); // create Y axis object
        zCoor=(TextView)findViewById(R.id.zcoor); // create Z axis object*/

        senManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = senManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senManager.registerListener(this, accelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onBackPressed()
    {

        //thats it
    }

    public void continGame(View view){
        btn.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.DRINK_RANDOM, drinkRandom);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(squatToDo > squatCount){
            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

           /*xCoor.setText("X: "+x);
            yCoor.setText("Y: "+y);
            zCoor.setText("Z: "+z);*/
                work = y;

                long now = System.currentTimeMillis();
                if (!mInitialized) {
                    startTime = now;
                    mInitialized = true;
                }

                long elapsedTime = now - startTime;

           /* if (TOTAL <= MAX_TIME && elapsedTime > 100) {
                TOTAL = TOTAL + (int) elapsedTime;
                speedList.add(Float.toString(work) + "   " + Long.toString(elapsedTime));
            }*/

                if (elapsedTime > 100) {
                    if (counter == 1) {
                        between++;
                        up = up + (int) elapsedTime;
                    }
                    if (work > 10.0 && counter == 0 && between >= 3) {
                        counter++;
                        between = 0;
                    } else {
                        between++;
                    }
                    if (counter == 1 && (work > 8.8 && work < 10.0)) {
                        if (up > MIN_SQUAT && up < MAX_SQUAT) {
                            counter++;
                            between = 0;
                        } else {
                            counter = 0;
                            up = 0;
                            between = 0;
                        }
                    }

                    if (up > MAX_SQUAT) {
                        counter = 0;
                        up = 0;
                        between = 0;
                    }
                }

                if (counter == 2) {
                    squatCount++;
                    texto.setText(Integer.toString(squatCount)+"/"+squatToDo);
                    counter = 0;
                    between++;
                    up = 0;
                }

                startTime = now;
            } else {
                texto.setText("");
                infoButton.setText("Atsiprašome, bet Jūsų įrenginys neturi reikiamo sensoriaus");
                infoButton.setTextColor(rgb(255, 102, 102));
                //Toast.makeText(getApplicationContext(), "Atsiprašome, bet Jūsų įrenginys neturi reikiamo sensoriaus", Toast.LENGTH_LONG).show();
                btn.setVisibility(View.VISIBLE);
            }
        } else {
            btn.setVisibility(View.VISIBLE);
            texto.setTextColor(rgb(255, 102, 102));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        senManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        senManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void setCurrentAcceleration(SensorEvent event) {

        final float alpha = 0.8f;

        // Gravity components of x, y, and z acceleration
        mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
        mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
        mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];

        // Linear acceleration along the x, y, and z axes (gravity effects removed)
        delta[0] = event.values[0] - mGravity[0];
        delta[1] = event.values[1] - mGravity[1];
        delta[2] = event.values[2] - mGravity[2];
    }

   /* private boolean isSquat(){
        boolean isSquat = false;
        ArrayList<Float> diffL = new ArrayList<Float>();
        int count = speedL.size();
        int mark1 = 0;
        int mark2 = 0;
        int mark3 = 0;
        int mark4 = 0;
        int position = 0;
        int countToClear = 0;

        for (int i = 0; i < count; i++){
            if (i == 0) {
                diffL.add(0.0f);
            }
            else {
                diffL.add(speedL.get(i) - speedL.get(i-1));
            }
        }

        if (diffL.get(1) < sudDec || diffL.get(2) < sudDec || diffL.get(3) < sudDec) mark1++;
        if (diffL.get(1) < 0 || diffL.get(2) < 0 || diffL.get(3) < 0) mark2++;

        for (int i = 4; i < 8; i++){
            if(diffL.get(i) > sudInc && diffL.get(i+1) < sudDec){
                position = i+1;
                mark3++;
                break;
            }
            if(diffL.get(i) > sudInc && diffL.get(i+2) < sudDec){
                position = i+2;
                mark4++;
                break;
            }
        }

        if ((mark1 > 0 || mark2 > 0) && (mark3 > 0 || mark4 > 0)){
            isSquat = true;
            if ((count - position+1 -3) <= 0){
                speedL.clear();
            }
            else{
                countToClear = position+1 -3;
                while (speedL.size() != count - countToClear){
                    speedL.remove(0);
                }
            }
        }

        return isSquat;
    }


    // assign directions
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            xCoor.setText("X: "+x);
            yCoor.setText("Y: "+y);
            zCoor.setText("Z: "+z);

            setCurrentAcceleration(event);
            if (!mInitialized) {
                last_x = delta[0];
                last_y = delta[1];
                last_z = delta[2];
                mInitialized = true;
            }

            long now = System.currentTimeMillis();
            if (startTime == 0) startTime = now;

            long elapsedTime = now - startTime;

            if (TOTAL <= MAX_TIME && elapsedTime > 100) {
                TOTAL = TOTAL + (int) elapsedTime;
                float speed = Math.abs(delta[0] + delta[1] + delta[2] - last_x - last_y - last_z)*elapsedTime;
                //float speed = Math.abs(x + y + z - last_x - last_y - last_z)*elapsedTime;
                startSpeed = speed;
                startTime = now;
                speedList.add(Float.toString(delta[1]) + "   " + Long.toString(elapsedTime));
                speedL.add(delta[1]);
            }

            if (TOTAL >= MAX_TIME && mark == 0) {
                ListView lv = (ListView) findViewById(R.id.speedlist);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            this, R.layout.speed_info,
                            R.id.speed,speedList);
                    //lv.setAdapter(adapter);
                lv.setAdapter(adapter);
                mark++;
            }


    if (elapsedTime > 100) {
                TOTAL = TOTAL + (int) elapsedTime;
                float speed = Math.abs(delta[0] + delta[1] + delta[2] - last_x - last_y - last_z)*elapsedTime;
                //float speed = Math.abs(x + y + z - last_x - last_y - last_z)*elapsedTime;
                startSpeed = speed;
                startTime = now;
                speedList.add(Float.toString(speed) + "   " + Long.toString(elapsedTime));
                speedL.add(speed);
            }

            if (TOTAL >= MAX_TIME && mark == 0) {
                ListView lv = (ListView) findViewById(R.id.speedlist);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            this, R.layout.speed_info,
                            R.id.speed,speedList);
                    //lv.setAdapter(adapter);
                lv.setAdapter(adapter);
                mark++;
            }

            if (speedL.size() == 12) {
                if (isSquat()) {
                    squatCount++;
                    texto.setText(Integer.toString(squatCount));
                }
            } else if (speedL.size() > 12) {
                while (speedL.size() != 11){
                    speedL.remove(0);
                }
            }

            last_x = delta[0];
            last_y = delta[1];
            last_z = delta[2];



    */
}
