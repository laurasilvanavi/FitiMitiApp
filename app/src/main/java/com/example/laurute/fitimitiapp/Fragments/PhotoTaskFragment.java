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

public class PhotoTaskFragment extends Fragment {
    TextView textViewPhotoTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_task, container, false);
        textViewPhotoTask = (TextView)view.findViewById(R.id.textViewPhotoTask);
        String taskDescription = getArguments().getString("TaskDescription");
        textViewPhotoTask.setText(taskDescription);
        return view;
    }
}