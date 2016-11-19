package com.example.laurute.fitimitiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
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
    //ListView lv;
    GridView gv;
    ArrayAdapter<String> adapter;
    ArrayList<String> players;
    int countTask;
    int countDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        db = new GameDbHelper(this);

        //------kai nera duomenu TASK ir DRINK lentelese, tai iraso defaultinius
        countTask = db.getTaskCount();
        countDrink = db.getDrinkCount();
        if (countTask == 0) db.generateAllDefaultTasks();
        if (countDrink == 0) db.generateAllDefaultDrinks();


        //lv = (ListView) findViewById(R.id.playerlist);
        gv = (GridView) findViewById(R.id.gridview);


        //--------------BANDYMAI------

       /* db.addTask("Nusifotografuoti su 2 mamomis", "task1", false);
        db.addTask("Nusifotografuoti su 3 geriausiais draugais", "task1", false);

       /* db.addTask("Pakakoti po pakopa", "task0", false);
        db.addTask("Padainuoti visu balsu 1 min bet kokią dainą", "task0", false);
        db.addTask("Išriaugėti abėcėlę", "task0", false);
        db.addTask("Pašokti", "task0", false);
        db.addTask("Apeiti aplink namą 3 kartus", "task0", false);
        db.addTask("Padaryti 10 pritūpimų", "task2", false);
        db.addTask("Nusifotografuoti su 2 blondinėmis", "task1", false);*/


        //----------------------------

        players = db.getAllPlayers();

        if (players != null) {
            adapter = new ArrayAdapter<String>(
                    this, R.layout.player_info,
                    R.id.name,players);
            //lv.setAdapter(adapter);
            gv.setAdapter(adapter);
        }

        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
               // TextView text = (TextView) findViewById (R.id.test);
                String name = gv.getItemAtPosition(position).toString();
               // text.setText(name);
                deletePlayer(position, name);
                return true;
            }
        });
       // this.deleteDatabase("Game.db");
    }

    public void playGame (View view) {
        ArrayList<String> playerList = db.getAllPlayers();
        int count = playerList.size();
        if (count > 0 ) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Norėdami žaisti pridėkite nors vieną žaidėją!", Toast.LENGTH_LONG).show();
        }
    }

    public void addPlayer(View view) {
        EditText ed1=(EditText)findViewById(R.id.playername);
        String name  = ed1.getText().toString();
        long mark;

        if(name.equals("")) {
            Toast.makeText(getApplicationContext(), "Tuščio laukelio pridėti negalima!", Toast.LENGTH_LONG).show();
        }
        else {
            mark = db.addPlayer(name);
            if (mark != -1) {
                Toast.makeText(getApplicationContext(), "Sėkmingai įrašyta.", Toast.LENGTH_LONG).show();
                adapter.add(name);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
                ed1.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Žaidėjas tokiu vardu jau yra!", Toast.LENGTH_LONG).show();
            }
        }
    }

   /* public void findTask(View view) {
        int skaicius = db.getTaskCount();
        Task t = db.getTask(1);
        TextView text = (TextView) findViewById (R.id.test);
        text.setText(t.get_type());
    }*/

   /* private void displayListView() {
        ArrayList<String> players = db.getAllPlayers();

        if (players != null) {
            ListView listView = (ListView) findViewById(R.id.playerlist);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, R.layout.player_info,
                    R.id.name,players);
            listView.setAdapter(adapter);
        }
    }*/

    protected void deletePlayer (int position, String name) {
        final int deletePosition = position;
        final String playerName = name;

        AlertDialog.Builder alert = new AlertDialog.Builder(PlayerActivity.this);

        alert.setTitle("Dėmesio!");
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
