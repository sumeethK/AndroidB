package com.example.daljit.androidb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daljit on 12-Jun-16.
 */
public class MyNotesDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mynotes.db";
    public String TABLE_NAME = "mynotes";
    public String COLUMN_ID = "_id";
    public String COLUMN_NOTES = "_notes";


    public MyNotesDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + "( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + "," +
                COLUMN_NOTES + " TEXT" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        onCreate(db);
    }

    public void addNotes(MyNotes notes){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTES,notes.getNotes());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();

    }

    public void deleteNotes(String id){
        String queery = "DELETE FROM " +TABLE_NAME + " WHERE " +COLUMN_ID + "=\"" + id +"\";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(queery);
    }

    public String printAllNotes(){
        String notes ="";
        SQLiteDatabase db = getWritableDatabase();
        String query ="SELECT * FROM "+TABLE_NAME +";";

        Cursor c  = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()){

            if(c.getString(c.getColumnIndex(COLUMN_NOTES))!=null){

                notes += c.getString(c.getColumnIndex(COLUMN_NOTES));
                notes +="\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return notes;


    }
}
