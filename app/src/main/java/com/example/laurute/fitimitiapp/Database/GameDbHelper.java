package com.example.laurute.fitimitiapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Kristaliukas on 13/11/2016.
 */

public class GameDbHelper extends SQLiteOpenHelper {

    // sukurimo sakiniai
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_PLAYERS =
            "CREATE TABLE " + GameContract.Player.TABLE_NAME + " (" +
                    GameContract.Player._ID + " INTEGER PRIMARY KEY," +
                    GameContract.Player.COLUMN_NAME_NAME + TEXT_TYPE + " UNIQUE IGNORE)";

    private static final String SQL_CREATE_TASKS =
            "CREATE TABLE " + GameContract.Task.TABLE_NAME + " (" +
                    GameContract.Task._ID + " INTEGER PRIMARY KEY," +
                    GameContract.Task.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    GameContract.Task.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    GameContract.Task.COLUMN_NAME_PARTNER + " BOOLEAN" + " )";

    private static final String SQL_CREATE_DRINKS =
            "CREATE TABLE " + GameContract.Drink.TABLE_NAME + " (" +
                    GameContract.Drink._ID + " INTEGER PRIMARY KEY," +
                    GameContract.Drink.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + " )";


    // istrynimo sakiniai
    private static final String SQL_DELETE_PLAYERS =
            "DROP TABLE IF EXISTS " + GameContract.Player.TABLE_NAME;
    private static final String SQL_DELETE_TASKS =
            "DROP TABLE IF EXISTS " + GameContract.Task.TABLE_NAME;
    private static final String SQL_DELETE_DRINKS =
            "DROP TABLE IF EXISTS " + GameContract.Drink.TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Game.db";

    public GameDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PLAYERS);
        db.execSQL(SQL_CREATE_TASKS);
        db.execSQL(SQL_CREATE_DRINKS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PLAYERS);
        db.execSQL(SQL_DELETE_TASKS);
        db.execSQL(SQL_DELETE_DRINKS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    void addPlayer (String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GameContract.Player.COLUMN_NAME_NAME, name);

        // iterpia eilute ir grazina jos id
        long newRowId = db.insert(GameContract.Player.TABLE_NAME, null, values);
    }

    public ArrayList<String> getAllPlayers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + GameContract.Player.TABLE_NAME, null);
        ArrayList<String> players = new ArrayList<String>();
        String player;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                player = cursor.getString(1);
                players.add(player);
            }
        }
        cursor.close();
        db.close();
        return players;
    }
}
