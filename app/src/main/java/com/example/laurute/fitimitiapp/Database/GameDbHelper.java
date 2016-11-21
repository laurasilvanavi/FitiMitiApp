package com.example.laurute.fitimitiapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.laurute.fitimitiapp.Object.Task;

import java.util.ArrayList;

/**
 * Created by Kristaliukas on 13/11/2016.
 */

public class GameDbHelper extends SQLiteOpenHelper {

    // sukurimo sakiniai
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static int taskSize = 0;

    private static final String SQL_CREATE_PLAYERS =
            "CREATE TABLE " + GameContract.Player.TABLE_NAME + " (" +
                    GameContract.Player._ID + " INTEGER PRIMARY KEY," +
                    GameContract.Player.COLUMN_NAME_NAME + TEXT_TYPE + " UNIQUE )";

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

    public static int getTaskSize() {
        return taskSize;
    }

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Game.db";

    public GameDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        generateAllDefaultDrinks();
        generateAllDefaultTasks();
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

    public long addPlayer(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GameContract.Player.COLUMN_NAME_NAME, name);

        // iterpia eilute ir grazina jos id
        long newRowId = db.insert(GameContract.Player.TABLE_NAME, null, values);
        return newRowId;
    }

    public long addTask(String description, String type, boolean partner) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GameContract.Task.COLUMN_NAME_DESCRIPTION, description);
        values.put(GameContract.Task.COLUMN_NAME_TYPE, type);
        values.put(GameContract.Task.COLUMN_NAME_PARTNER, partner);

        // iterpia eilute ir grazina jos id
        long newRowId = db.insert(GameContract.Task.TABLE_NAME, null, values);
        return newRowId;
    }

    public long addDrink(String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GameContract.Drink.COLUMN_NAME_DESCRIPTION, description);

        // iterpia eilute ir grazina jos id
        long newRowId = db.insert(GameContract.Drink.TABLE_NAME, null, values);
        return newRowId;
    }

    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GameContract.Task.TABLE_NAME, new String[] { GameContract.Task._ID,
                        GameContract.Task.COLUMN_NAME_DESCRIPTION, GameContract.Task.COLUMN_NAME_TYPE,
                        GameContract.Task.COLUMN_NAME_PARTNER }, GameContract.Task._ID + "=?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String partner = cursor.getString(3);
        boolean isPartner;
        if (Integer.parseInt(partner) == 1) {
            isPartner = true;
        } else {
            isPartner = false;
        }
        Task task = new Task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), isPartner);

        // return contact
        return task;
    }

    public String getDrink(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GameContract.Drink.TABLE_NAME, new String[] { GameContract.Drink._ID,
                        GameContract.Drink.COLUMN_NAME_DESCRIPTION}, GameContract.Drink._ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String descrip = cursor.getString(1);

        return descrip;
    }

    public ArrayList<String> getAllDrinks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + GameContract.Drink.TABLE_NAME, null);
        ArrayList<String> drinks = new ArrayList<String>();
        String drink;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                drink = cursor.getString(1);
                drinks.add(drink);
            }
        }
        cursor.close();
        db.close();
        return drinks;
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

    public void deletePlayer(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GameContract.Player.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = { name };
        db.delete(GameContract.Player.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteAllPlayers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GameContract.Player.TABLE_NAME, null,null);
    }

    public void deleteAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GameContract.Task.TABLE_NAME, null,null);
    }

    public void deleteAllDrinks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GameContract.Drink.TABLE_NAME, null,null);
    }

    public int getTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + GameContract.Task.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int getDrinkCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + GameContract.Drink.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public void generateAllDefaultTasks() {
        addTask("Padainuoti visu balsu 1 min bet kokią dainą", "task0", false);
        addTask("Išriaugėti abėcėlę", "task0", false);
        addTask("Pašokti pagal mėgstamiausią dainą", "task0", false);
        addTask("Apeiti aplink namą 3 kartus", "task0", false);
        addTask("Nusifotgrafuoti su 2 blondinėmis", "task1", false);
        addTask("Nusifotografuoti su šuniu", "task1", false);
        addTask("Nusifotografuoti su 2 mamomis", "task1", false);
        addTask("Nusifotografuoti su 5 geriausiais draugais", "task1", false);
        addTask("Padaryti 10 pritūpimų", "task2", false);
        addTask("Padaryti 20 plačių pritūpimų", "task2", false);
        addTask("Padaryti 15 slidininko pritūpimų", "task2", false);
        addTask("Atlikti 20 atsilenkimų", "task3", false);
        addTask("Padaryti 10 atsilenkimų", "task3", false);
        addTask("Padaryti 15 atsilenkimų", "task3", false);
        taskSize = getTaskCount();
    }

    public void generateAllDefaultDrinks() {
        addDrink("Geria gimę lyginę dieną");
        addDrink("Geria tie, kas turi bent vieną brolį");
        addDrink("Geria besimokantys Vilniaus universitete");
        addDrink("Visi paragauja kaimyno iš dešinės gėrimo");
        addDrink("Geria visi");
        addDrink("Geria vienišiai");
        addDrink("Laikas išgerti kairiarankiams");
        addDrink("Jei tavo varde yra raidė S, reiškia turi išgerti");
        addDrink("Kas nori, tas išgeria");
        addDrink("Vyrai geria iki dugno");
        addDrink("Moterys geria 10 sekundžių");
    }
}
