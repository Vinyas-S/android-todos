package com.example.todo_firebase;

import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todos.db";
    public static final String TABLE_NAME = "users_data";
    public static final String COL1 = "ID";
    public static final String COL2= "DESCRIPTION";

    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable= "CREATE TABLE " + TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," + "DESCRIPTION TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addData(String desc){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2,desc);

        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateuserdata(String desc) {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, desc);

        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME+" where DESCRIPTION = ?", new String[] {desc});
        if (cursor.getCount() > 0) {

            long result = db.update(TABLE_NAME, contentValues, "DESCRIPTION=?", new String[] {desc});

            if (result == -1) {
                return false;
            } else {
                return true;
            }}
        else{
            return false;
        }

    }

    public boolean deleteuserdata(User desc){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("Select * from "+TABLE_NAME+" where DESCRIPTION = ?",new String[] {String.valueOf(desc)});
        if(cursor.getCount()>0){

            long result=db.delete(TABLE_NAME,"DESCRIPTION=?", new String[] {String.valueOf(desc)});

            if(result==-1)
            {
                return false;
            }
            else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return data;
    }
}

