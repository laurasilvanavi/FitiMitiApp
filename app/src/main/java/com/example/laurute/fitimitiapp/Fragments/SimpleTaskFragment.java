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

public class SimpleTaskFragment extends Fragment {
    TextView textViewSimpleTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_task, container, false);
        textViewSimpleTask = (TextView)view.findViewById(R.id.textViewSimpleTask);
        String taskDescription = getArguments().getString("TaskDescription");
        textViewSimpleTask.setText(taskDescription);
        return view;
    }
}
