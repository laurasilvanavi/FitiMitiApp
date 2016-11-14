package com.example.laurute.fitimitiapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.laurute.fitimitiapp.Database.GameDbHelper;
import com.example.laurute.fitimitiapp.Fragments.PhotoTaskFragment;
import com.example.laurute.fitimitiapp.Fragments.PlayerFragment;
import com.example.laurute.fitimitiapp.Fragments.SimpleTaskFragment;
import com.example.laurute.fitimitiapp.Fragments.SportTaskFragment;
import com.example.laurute.fitimitiapp.Fragments.WhatFragment;
import com.example.laurute.fitimitiapp.Fragments.WhoFragment;
import com.example.laurute.fitimitiapp.Object.Task;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements WhatFragment.WhatListener, WhoFragment.WhoListener {
    private static final String TYPE0 = "task0";
    private static final String TYPE1 = "task1";
    private static final String TYPE2 = "task2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        final android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        WhoFragment newFragment = new WhoFragment();
        fragmentTransaction.add(R.id.fragment_container1, newFragment);

        WhatFragment newFragment2 = new WhatFragment();
        fragmentTransaction.add(R.id.fragment_container2, newFragment2);
        fragmentTransaction.commit();


    }
    GameDbHelper db;

    @Override
    public void showWhat() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        db = new GameDbHelper(this);
        int random = getRandom(db.getTaskCount(), 1);
        Task task = db.getTask(random);
        Bundle info = new Bundle();
        info.putString("TaskDescription", task.get_description());
        switch (task.get_type()){
            case TYPE1:
                PhotoTaskFragment photoTaskFragment = new PhotoTaskFragment();
                photoTaskFragment.setArguments(info);
                fragmentTransaction.replace(R.id.fragment_container2, photoTaskFragment);
                fragmentTransaction.commit();
                break;
            case TYPE2:
                SportTaskFragment sportTaskFragment = new SportTaskFragment();
                sportTaskFragment.setArguments(info);
                fragmentTransaction.replace(R.id.fragment_container2, sportTaskFragment);
                fragmentTransaction.commit();
                break;
            default:
                SimpleTaskFragment simpleTaskFragment = new SimpleTaskFragment();
                simpleTaskFragment.setArguments(info);
                fragmentTransaction.replace(R.id.fragment_container2, simpleTaskFragment);
                fragmentTransaction.commit();
        }
    }

    @Override
    public void showWho() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerFragment playerFragment = new PlayerFragment();

        db = new GameDbHelper(this);
        int random = getRandom(db.getAllPlayers().size()-1, 0);
        String player = db.getAllPlayers().get(random).toString();
        Bundle info = new Bundle();
        info.putString("Player", player);
        playerFragment.setArguments(info);

        fragmentTransaction.replace(R.id.fragment_container1, playerFragment);
        fragmentTransaction.commit();
    }

    private int getRandom(int max, int min)
    {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public void chooseDrink(View view){
        Intent intent = new Intent(GameActivity.this, DrinkActivity.class);
        startActivity(intent);
    }
}
