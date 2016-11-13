package com.example.laurute.fitimitiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laurute.fitimitiapp.Database.GameDbHelper;
import com.example.laurute.fitimitiapp.Object.Task;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    private GameDbHelper db;
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        db = new GameDbHelper(this);
        lv = (ListView) findViewById(R.id.playerlist);

        //--------------BANDYMAI------

       /* db.addTask("Pakakoti po pakopa", "task0", false);
        db.addTask("Padainuoti visu balsu 1 min bet kokią dainą", "task0", false);
        db.addTask("Išriaugėti abėcėlę", "task0", false);*/


        //----------------------------

        players = db.getAllPlayers();

        if (players != null) {
            adapter = new ArrayAdapter<String>(
                    this, R.layout.player_info,
                    R.id.name,players);
            lv.setAdapter(adapter);
        }

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
               // TextView text = (TextView) findViewById (R.id.test);
                String name = lv.getItemAtPosition(position).toString();
               // text.setText(name);
                deletePlayer(position, name);
                return true;
            }
        });
    }

    public void playGame (View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void addPlayer(View view) {
        EditText ed1=(EditText)findViewById(R.id.playername);
        String name  = ed1.getText().toString();

        long mark = db.addPlayer(name);
        if (mark != -1) {
            Toast.makeText(getApplicationContext(), "Sėkmingai įrašyta.", Toast.LENGTH_LONG).show();
            adapter.add(name);
            adapter.notifyDataSetChanged();
            adapter.notifyDataSetInvalidated();
            ed1.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Žaidėjas tokiu vardu jau yra!", Toast.LENGTH_LONG).show();
        }
    }

    public void findTask(View view) {
        int skaicius = db.getTaskCount();
        Task t = db.getTask(1);
        TextView text = (TextView) findViewById (R.id.test);
        text.setText(t.get_type());
    }

    private void displayListView() {
        ArrayList<String> players = db.getAllPlayers();

        if (players != null) {
            ListView listView = (ListView) findViewById(R.id.playerlist);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, R.layout.player_info,
                    R.id.name,players);
            listView.setAdapter(adapter);
        }
    }

    protected void deletePlayer (int position, String name) {
        final int deletePosition = position;
        final String playerName = name;

        AlertDialog.Builder alert = new AlertDialog.Builder(PlayerActivity.this);

        alert.setTitle("Įspėjimas");
        alert.setMessage("Ar tikrai norite ištrinti šį žaidėją?");
        alert.setPositiveButton("Taip", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deletePlayer(playerName);
                players.remove(deletePosition);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
            }
        });
        alert.setNegativeButton("Atšaukti", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
