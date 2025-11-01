package com.example.lab5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBHelper extends SQLiteOpenHelper {
    private static String databasename = "movieDatabase";
    SQLiteDatabase movieDatabase;
    private static int version = 1;
    public MovieDBHelper(Context context){
        super(context, databasename, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table movie(id integer primary key,name text not null,description text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists movie");
        onCreate(db);
    }
    public Cursor fetchAllData() {
        movieDatabase = getReadableDatabase();
        String[] rowDetails = {"name", "description","id"};
        Cursor cursor = movieDatabase.query("movie", rowDetails, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        movieDatabase.close();
        return cursor;
    }
    public void insertMovie(String name, String description){
        ContentValues row = new ContentValues();
        row.put("name", name);
        row.put("description", description);
        movieDatabase = getWritableDatabase();
        movieDatabase.insert("movie", null, row);
        movieDatabase.close();
    }
    public void getAllData(){
        movieDatabase = getWritableDatabase();
        Cursor cursor = movieDatabase.rawQuery("select * from movie", null);
        if(cursor !=null){
            cursor.moveToFirst();
        }
        movieDatabase.close();
    }
    public void deleteMovie(String name) {
        movieDatabase = getWritableDatabase();
        movieDatabase.delete("movie", "name=?", new String[]{name});
        movieDatabase.close();

    }
    //HANDS ON FUNCTION
    public String getMovieDesc(String name) {
        movieDatabase = getReadableDatabase();
        String[] arg = {name};
        Cursor cursor = movieDatabase.rawQuery("SELECT description from movie where name like ?",arg);
        if(cursor !=null){
            cursor.moveToFirst();
        }
        movieDatabase.close();
        return cursor.getString(0);

    }
}
