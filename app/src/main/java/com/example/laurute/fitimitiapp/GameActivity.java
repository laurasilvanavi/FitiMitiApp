package com.example.laurute.fitimitiapp;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.laurute.fitimitiapp.Fragments.PlayerFragment;
import com.example.laurute.fitimitiapp.Fragments.SimpleTaskFragment;
import com.example.laurute.fitimitiapp.Fragments.WhatFragment;
import com.example.laurute.fitimitiapp.Fragments.WhoFragment;

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

        fragmentTransaction.replace(R.id.fragment_container1, playerFragment);
        fragmentTransaction.commit();
    }
}
