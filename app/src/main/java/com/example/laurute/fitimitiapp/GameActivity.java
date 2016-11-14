package com.example.laurute.fitimitiapp;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.laurute.fitimitiapp.Database.GameDbHelper;
import com.example.laurute.fitimitiapp.Fragments.PlayerFragment;
import com.example.laurute.fitimitiapp.Fragments.SimpleTaskFragment;
import com.example.laurute.fitimitiapp.Fragments.WhatFragment;
import com.example.laurute.fitimitiapp.Fragments.WhoFragment;
import com.example.laurute.fitimitiapp.Object.Task;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements WhatFragment.WhatListener, WhoFragment.WhoListener {

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
    public void ShowWhat() {
        android.app.FragmentManager fragmentManager = getFragmentManager();

        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SimpleTaskFragment simpleTaskFragment = new SimpleTaskFragment();

        fragmentTransaction.replace(R.id.fragment_container2, simpleTaskFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void showWho() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerFragment playerFragment = new PlayerFragment();

        db = new GameDbHelper(this);
        int random = getRandom(db.getAllPlayers().size(), 0);
        String player = db.getAllPlayers().get(random).toString(); // cia veliau turi buti ne uzduoties
//        String task_description =task.get_description();

        Bundle info = new Bundle();
        info.putString("Player", player);
        playerFragment.setArguments(info);


        fragmentTransaction.replace(R.id.fragment_container1, playerFragment);
        fragmentTransaction.commit();
    }

    private int getRandom(int max, int min)
    {
        Random r = new Random();
        int result = r.nextInt(max - min + 1) + min;
        return result;
    }
}
