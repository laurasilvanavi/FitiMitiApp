package com.example.laurute.fitimitiapp.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.laurute.fitimitiapp.R;

/**
 * Created by Laurute on 11/13/2016.
 */

public class WhoFragment extends Fragment {

    TextView textViewWho;
    WhoListener whoListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_who, container, false);
        textViewWho = (TextView) view.findViewById(R.id.textViewWho);

        textViewWho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whoListener.showWho();
            }
        });
        return view;
    }

    public interface WhoListener
    {
        void showWho();

    }

    @Override
    public void onAttach (Activity activity)
    {
        super.onAttach(activity);
        try {
            whoListener = (WhoListener) activity;
        }
        catch (Exception e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NameListener");
        }
    }

}
