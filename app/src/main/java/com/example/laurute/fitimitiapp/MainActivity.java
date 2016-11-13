package com.example.laurute.fitimitiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, PictureActivity.class); //siunčiu 2 būdais - iš čia gavus per shared preferences idedu i extras arba kitam activityje pats pasiims is shared preferences
        startActivity(intent);
    }
}
