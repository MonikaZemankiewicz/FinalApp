package com.example.finalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.finalapp.ExerciseContract.*;

import static android.icu.text.MessagePattern.ArgType.SELECT;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tabata.db";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " +
                ExerciseEntry.TABLE_NAME + " (" +
                ExerciseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ExerciseEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ExerciseEntry.COLUMN_GROUP + " INTEGER NOT NULL, " +
                ExerciseEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        

        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExerciseEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, String group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ExerciseContract.ExerciseEntry.COLUMN_NAME, name);
        cv.put(ExerciseContract.ExerciseEntry.COLUMN_GROUP, group);

        long result = db.insert(ExerciseEntry.TABLE_NAME, null, cv);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ExerciseEntry.TABLE_NAME, null );
        return data;
    }

    public boolean deleteTitle(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ExerciseEntry.TABLE_NAME, ExerciseEntry.COLUMN_NAME + "=" + name, null) > 0;
    }





}