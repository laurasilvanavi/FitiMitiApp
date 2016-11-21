package com.example.laurute.fitimitiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.laurute.fitimitiapp.Database.GameDbHelper;

public class DeleteTaskActivity extends AppCompatActivity {

    TextView tv;
    private GameDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);

        tv = (TextView)findViewById(R.id.textV);
        db = new GameDbHelper(this);

        int skaicius = db.getTaskSize();

        tv.setText(String.valueOf(skaicius));

    }
}
