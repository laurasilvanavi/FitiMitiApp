package com.example.laurute.fitimitiapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laurute.fitimitiapp.Database.GameDbHelper;

public class TaskActivity extends AppCompatActivity {
    private GameDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        db = new GameDbHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTask);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        toolbar.setAlpha(0.5f);
        toolbar.setBackground(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addTask(View view) {
        EditText editTask = (EditText)findViewById(R.id.editTextAddTask);
        CheckBox isPartner = (CheckBox)findViewById(R.id.checkBoxCompanion);
        boolean isCheckedCompanion = ((CheckBox)findViewById(R.id.checkBoxCompanion)).isChecked();
        String task  = editTask.getText().toString();
        long mark;

        if(task.equals("")) {
            Toast.makeText(getApplicationContext(), "Tuščio laukelio pridėti negalima!", Toast.LENGTH_LONG).show();
        }
        else {
            mark = db.addTask(task, "task0", isCheckedCompanion);
            if (mark != -1) {
                Toast.makeText(getApplicationContext(), "Sėkmingai įrašyta.", Toast.LENGTH_LONG).show();
                editTask.setText("");
                isPartner.setChecked(false);
            } else {
                Toast.makeText(getApplicationContext(), "Klaida!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
