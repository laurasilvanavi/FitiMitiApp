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

public class SportTaskFragment extends Fragment {
    TextView textViewSportTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sport_task, container, false);
        textViewSportTask = (TextView)view.findViewById(R.id.textViewSportTask);
        String taskDescription = getArguments().getString("TaskDescription");
        textViewSportTask.setText(taskDescription);
        return view;
    }
}