package com.example.laurute.fitimitiapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laurute.fitimitiapp.Adapter.ArrayAdapterTask;
import com.example.laurute.fitimitiapp.Database.GameDbHelper;
import com.example.laurute.fitimitiapp.Object.Task;

import java.util.ArrayList;

public class DeleteTaskActivity extends AppCompatActivity {

    private GameDbHelper db;
    ListView lv;
    TextView tv;
    Button buttonDelete;

    ArrayAdapterTask adapterT;
    ArrayList<String> tasksDescriptions = new ArrayList<String>();
    ArrayList<Integer> tasksId = new ArrayList<Integer>();
    ArrayList<Task> taskList;
    ArrayList<Boolean> taskCheck = new ArrayList<Boolean>();;
    int notDeleteTaskCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDeleteTask);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        toolbar.setAlpha(0.5f);
        toolbar.setBackground(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new GameDbHelper(this);
        lv = (ListView)findViewById(R.id.tasklist);
        tv = (TextView)findViewById(R.id.title);
        buttonDelete = (Button)findViewById(R.id.buttonDelete);
        buttonDelete.setVisibility(View.INVISIBLE);

        taskList = db.getAllTasks();
        notDeleteTaskCount = db.getTaskSize();
        for (int i = notDeleteTaskCount; i < taskList.size(); i++) {
            tasksDescriptions.add(taskList.get(i).get_description());
            tasksId.add(taskList.get(i).getID());
        }

        adapterT = new ArrayAdapterTask(this, R.layout.task_info,tasksDescriptions);

        if (adapterT.getCount() == 0) tv.setText("Užduočių nėra");
        else {
            lv.setAdapter(adapterT);
            buttonDelete.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //Intent intent = new Intent(this, GameActivity.class);
                //startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteTask(View v) {
        for (int i = 0; i < adapterT.getCount(); i++) {
            taskCheck.add(adapterT.getIfChecked(i));
            adapterT.setIfChecked(i);
        }

        for (int i = 0; i < taskCheck.size(); i++) {
            if (taskCheck.get(i) == true) {
                db.deleteTask(tasksId.get(i));
                taskCheck.remove(i);
                tasksDescriptions.remove(i);
                tasksId.remove(i);
                adapterT.notifyDataSetChanged();
                adapterT.notifyDataSetInvalidated();
                i--;
            }
        }
        adapterT = new ArrayAdapterTask(this, R.layout.task_info,tasksDescriptions);
        lv.setAdapter(adapterT);
        taskCheck.clear();
        db.close();
    }

    @Override
    public void onBackPressed() {
        finish();
        // Intent intent = new Intent(this, GameActivity.class);
        // startActivity(intent);
    }
}
