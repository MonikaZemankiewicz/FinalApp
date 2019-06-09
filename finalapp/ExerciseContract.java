package com.example.finalapp;


import android.provider.BaseColumns;

public class ExerciseContract {

    private ExerciseContract() {
    }

    public static final class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "exerciseList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_GROUP = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}