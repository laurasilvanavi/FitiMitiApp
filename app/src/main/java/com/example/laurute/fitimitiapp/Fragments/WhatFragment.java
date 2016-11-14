package com.example.laurute.fitimitiapp.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laurute.fitimitiapp.R;

/**
 * Created by Laurute on 11/13/2016.
 */

public class WhatFragment extends Fragment {

    //Button buttonWhat;
    TextView textViewWhat;
    WhatListener whatListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_what, container, false);
        textViewWhat = (TextView) view.findViewById(R.id.textViewWhat);

        textViewWhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatListener.showWhat();

            }
        });

        return view;
    }

    public interface WhatListener
    {
        void showWhat();

    }

    @Override
    public void onAttach (Activity activity)
    {
        super.onAttach(activity);
        try {
            whatListener = (WhatListener) activity;
        }
        catch (Exception e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NameListener");
        }
    }

}
