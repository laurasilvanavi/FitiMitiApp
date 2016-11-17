package com.example.laurute.fitimitiapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.laurute.fitimitiapp.Database.GameDbHelper;
import com.example.laurute.fitimitiapp.Fragments.CrunchesTaskFragment;
import com.example.laurute.fitimitiapp.Fragments.PhotoTaskFragment;
import com.example.laurute.fitimitiapp.Fragments.PlayerFragment;
import com.example.laurute.fitimitiapp.Fragments.SimpleTaskFragment;
import com.example.laurute.fitimitiapp.Fragments.SportTaskFragment;
import com.example.laurute.fitimitiapp.Fragments.WhatFragment;
import com.example.laurute.fitimitiapp.Fragments.WhoFragment;
import com.example.laurute.fitimitiapp.Object.Task;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements WhatFragment.WhatListener, WhoFragment.WhoListener {
    public final static String EXTRA_MESSAGE = "com.example.laurute.fitimitiapp.MESSAGE";
    private static final String TYPE0 = "task0";
    private static final String TYPE1 = "task1";
    private static final String TYPE2 = "task2";
    private static final String TYPE3 = "task3";
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // pridedam menu bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        task = db.getTask(random);
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
            case TYPE3:
                CrunchesTaskFragment crunchesTaskFragment = new CrunchesTaskFragment();
                crunchesTaskFragment.setArguments(info);
                fragmentTransaction.replace(R.id.fragment_container2, crunchesTaskFragment);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.action_drink:
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
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

    public void choosePhoto(View view) {
        Intent intent = new Intent(this, PictureActivity.class);
        startActivity(intent);
    }

    public void chooseSport(View view) {
        String str = task.get_description();
        str = str.replaceAll("\\D+","");
        Intent intent = new Intent(this, SportActivity.class);
        intent.putExtra(EXTRA_MESSAGE, str);
        startActivity(intent);
    }

    public void chooseCrunches(View view) {
        String str = task.get_description();
        str = str.replaceAll("\\D+","");
        Intent intent = new Intent(this, CrunchesActivity.class);
        intent.putExtra(EXTRA_MESSAGE, str);
        startActivity(intent);
    }
}
