package com.example.laurute.fitimitiapp.Fragments;

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

public class PlayerFragment extends Fragment {
    TextView textViewPlayer, textViewCompanion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        textViewPlayer = (TextView)view.findViewById(R.id.textViewPlayer);
        textViewCompanion = (TextView)view.findViewById(R.id.textViewCompanion);
        String player = getArguments().getString("Player");
        String comp = getArguments().getString("Companion");
        textViewPlayer.setText(player);
        if (comp != "") {
            textViewCompanion.setText("Partneris: "+comp);
            textViewCompanion.setBackgroundResource(R.drawable.dotted_border);
        }
        return view;
    }
}
