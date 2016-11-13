package com.example.laurute.fitimitiapp.Database;

import android.provider.BaseColumns;

/**
 * Created by Kristaliukas on 13/11/2016.
 */

public class GameContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private GameContract() {}

    /* Inner class that defines the table contents */
    public static class Player implements BaseColumns {
        public static final String TABLE_NAME = "player";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static class Task implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_PARTNER = "partner";
    }

    public static class Drink implements BaseColumns {
        public static final String TABLE_NAME = "drink";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
}
