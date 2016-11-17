package com.example.laurute.fitimitiapp.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laurute.fitimitiapp.R;

public class CrunchesTaskFragment extends Fragment {

    TextView textViewCrunchesTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crunches_task, container, false);
        textViewCrunchesTask = (TextView)view.findViewById(R.id.textViewCrunchesTask);
        String taskDescription = getArguments().getString("TaskDescription");
        textViewCrunchesTask.setText(taskDescription);
        return view;
    }
}
