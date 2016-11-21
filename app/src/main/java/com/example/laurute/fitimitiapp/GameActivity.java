package com.example.laurute.fitimitiapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    public final static String DRINK_RANDOM = "DRINK_RANDOM";
    private static final String TYPE0 = "task0";
    private static final String TYPE1 = "task1";
    private static final String TYPE2 = "task2";
    private static final String TYPE3 = "task3";
    private Task task;
    private String player = "";
    private String companion = "";
    private int randomPlayer = -1;
    private int randomCompanion = -1;
    public int drinkRandom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // pridedam menu bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        toolbar.setAlpha(0.5f);
        toolbar.setBackground(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);     uzdeda grizimo mygtuka


        android.app.FragmentManager fragmentManager = getFragmentManager();
        final android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Intent intent = getIntent();
        drinkRandom = intent.getIntExtra(GameActivity.DRINK_RANDOM, 0); // jei nk neduos, tai 0 liks

//        if(drinkRandom>2){
//            //drinkRandom=0;
//            Intent intent2 = new Intent(GameActivity.this, DrinkRandomActivity.class);
//            startActivity(intent2);
//        }
//        else{
            WhoFragment newFragment = new WhoFragment();
            fragmentTransaction.add(R.id.fragment_container1, newFragment);

            WhatFragment newFragment2 = new WhatFragment();
            fragmentTransaction.add(R.id.fragment_container2, newFragment2);
            fragmentTransaction.commit();
        }
//    }
    GameDbHelper db;

    @Override
    public void showWhat() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        db = new GameDbHelper(this);
        int random = getRandom(db.getTaskCount(), 1);
        task = db.getTask(random);
        if (task.is_partner()) findCompanion();
        Bundle info = new Bundle();
        info.putString("TaskDescription", task.get_description());

        if(drinkRandom > 2){
            drinkRandom=0;
            Intent intent = new Intent(GameActivity.this, DrinkRandomActivity.class);
            startActivity(intent);
        }
        else{
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

    }

    @Override
    public void showWho() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerFragment playerFragment = new PlayerFragment();

        db = new GameDbHelper(this);
        randomPlayer = getRandom(db.getAllPlayers().size()-1, 0);
        if (randomCompanion == randomPlayer) {
            if (randomPlayer > 0) {
                randomPlayer--;
            } else {
                randomPlayer++;
            }
        }
        player = db.getAllPlayers().get(randomPlayer).toString();

        Bundle info = new Bundle();
        info.putString("Player", player);
        info.putString("Companion", companion);
        playerFragment.setArguments(info);

        fragmentTransaction.replace(R.id.fragment_container1, playerFragment);
        fragmentTransaction.commit();
    }

    public void findCompanion() {
        String comp;
        randomCompanion = getRandom(db.getAllPlayers().size()-1, 0);
        if (randomCompanion == randomPlayer) {
            if (randomCompanion > 0) {
                randomCompanion--;
            } else {
                randomCompanion++;
            }
        }
        comp = db.getAllPlayers().get(randomCompanion).toString();
        companion = comp;
        if (player != "") {
            TextView tv = (TextView)findViewById(R.id.textViewCompanion);
            tv.setText("Partneris: "+comp);
            tv.setBackgroundResource(R.drawable.dotted_border);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                return true;

            case R.id.action_player:
                Intent intentP = new Intent(GameActivity.this, PlayerActivity.class);
                startActivity(intentP);
                //finish();
                return true;

            case R.id.action_addTask:
                Intent intent = new Intent(GameActivity.this, TaskActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_deleteTask:
                Intent intentT = new Intent(GameActivity.this, DeleteTaskActivity.class);
                startActivity(intentT);
                return true;

            case R.id.action_finish:
                this.finishAffinity();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
               // android.os.Process.killProcess(android.os.Process.myPid());

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
        intent.putExtra(DRINK_RANDOM, drinkRandom);
        startActivity(intent);
    }

    public void choosePhoto(View view) {
        String str = task.get_description();
        str = str.replaceAll("\\D+","");
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra(EXTRA_MESSAGE, str);
        intent.putExtra(DRINK_RANDOM, drinkRandom);
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

    public void simpleTaskDone(View view){
        drinkRandom++;
        android.app.FragmentManager fragmentManager = getFragmentManager();
        final android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        player = "";
        companion = "";
        randomPlayer = -1;
        randomCompanion = -1;

        WhoFragment newFragment = new WhoFragment();
        fragmentTransaction.replace(R.id.fragment_container1, newFragment);

        WhatFragment newFragment2 = new WhatFragment();
        fragmentTransaction.replace(R.id.fragment_container2, newFragment2);
        fragmentTransaction.commit();
    }
    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onBackPressed() {
    }
}
