package com.example.laurute.fitimitiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laurute.fitimitiapp.Database.GameDbHelper;

import java.util.Random;

public class DrinkRandomActivity extends AppCompatActivity {
    GameDbHelper db;
    Button buttonDrinkRandomTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_random);
        buttonDrinkRandomTask = (Button)findViewById(R.id.buttonDrinkRandomTask);
        db = new GameDbHelper(this);
        int random = getRandom(db.getAllDrinks().size()-1, 0);
        String task = db.getAllDrinks().get(random).toString();
        buttonDrinkRandomTask.setText(task);

    }

    public void doneDrinkRandom(View view){
        this.finish();
//        Intent intent = new Intent(this, GameActivity.class);
//        startActivity(intent);
    }

    private int getRandom(int max, int min)
    {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
