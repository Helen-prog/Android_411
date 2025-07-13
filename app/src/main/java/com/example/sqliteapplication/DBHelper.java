package com.example.sqliteapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String USER = "user";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String AGE = "age";

    public DBHelper(@Nullable Context context) {
        super(context, "example.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + SURNAME + " TEXT, " + AGE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS example");
    }

    public void addOne(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, data.name);
        cv.put(SURNAME, data.surname);
        cv.put(AGE, data.age);

        db.insert(USER, null, cv);

        db.close();
    }

    public LinkedList<Data> getAll() {
        LinkedList<Data> list = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(USER, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id_n = cursor.getColumnIndex(NAME);
                int id_s = cursor.getColumnIndex(SURNAME);
                int id_y = cursor.getColumnIndex(AGE);

                Data data = new Data(cursor.getString(id_n), cursor.getString(id_s), cursor.getInt(id_y));
                list.add(data);

            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER, null, null);
        db.close();
    }
}
