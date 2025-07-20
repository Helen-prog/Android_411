package com.example.todolistapplication.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolistapplication.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TODO_DATABASE.db";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // метод для добаваления задачи
    public void insert(ToDoModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, model.getTask());
        contentValues.put(COL_3, 0);

        db.insert(TABLE_NAME, null, contentValues);
    }

    // обновление задачи
    public void updateTask(int id, String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, task);

        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, status);

        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(id)});
    }

    // удаление задачи
    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }


    @SuppressLint("Range")
    public List<ToDoModel> getAllTask(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        ToDoModel toDoModel = new ToDoModel();
                        toDoModel.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        toDoModel.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        toDoModel.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        modelList.add(toDoModel);
                    }while(cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
