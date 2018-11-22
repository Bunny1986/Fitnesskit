package com.kenyrim.fitnesskit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper {

    private static final String DATABASE_NAME = "rasp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Raspisanie";

    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PLACE = "place";
    private static final String TEACHER = "teacher";
    private static final String ST_TIME = "st_time";
    private static final String END_TIME = "end_time";
    private static final String WEEK = "week";

    private static final int NUM_ID = 0;
    private static final int NUM_NAME = 1;
    private static final int NUM_DESCRIPTION = 2;
    private static final int NUM_PLACE = 3;
    private static final int NUM_TEACHER = 4;
    private static final int NUM_ST_TIME = 5;
    private static final int NUM_END_TIME = 6;
    private static final int NUM_WEEK = 7;

    private SQLiteDatabase dataBase;
    OpenHelper mOpenHelper;

    Context context;

    public DBHelper(Context context) {
        this.context = context;
        mOpenHelper = new OpenHelper(context);
        dataBase = mOpenHelper.getWritableDatabase();
    }

    public void close() {
        dataBase.close();
    }


    public long insert(Raspisanie raspisanie){
        ContentValues cv = new ContentValues();
        cv.put(NAME, raspisanie.getName());
        cv.put(DESCRIPTION, raspisanie.getDescription());
        cv.put(PLACE, raspisanie.getPlace());
        cv.put(TEACHER, raspisanie.getTeacher());
        cv.put(ST_TIME, raspisanie.getStartTime());
        cv.put(END_TIME, raspisanie.getEndTime());
        cv.put(WEEK, raspisanie.getWeekDay());
        return dataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Raspisanie raspisanie){
        ContentValues cv = new ContentValues();
        cv.put(NAME, raspisanie.getName());
        cv.put(DESCRIPTION, raspisanie.getDescription());
        cv.put(PLACE, raspisanie.getPlace());
        cv.put(TEACHER, raspisanie.getTeacher());
        cv.put(ST_TIME, raspisanie.getStartTime());
        cv.put(END_TIME, raspisanie.getEndTime());
        cv.put(WEEK, raspisanie.getWeekDay());
        return dataBase.update(TABLE_NAME, cv, ID + " = ?", new String[]
                {String.valueOf(raspisanie.getId())});
    }

    public void deleteAll(){
        dataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id){
        dataBase.delete(TABLE_NAME, ID + " = ?", new String[]{ String.valueOf(id)});
    }

    public Raspisanie select(long id){
        Cursor cursor = dataBase.query(TABLE_NAME, null, ID + " = ?", new String[]
                {String.valueOf(id)}, null, null, NAME);
        cursor.moveToFirst();
        String name = cursor.getString(NUM_NAME);
        String desc = cursor.getString(NUM_DESCRIPTION);
        String place = cursor.getString(NUM_PLACE);
        String teacher = cursor.getString(NUM_TEACHER);
        String st_time = cursor.getString(NUM_ST_TIME);
        String end_time = cursor.getString(NUM_END_TIME);
        String week = cursor.getString(NUM_WEEK);
        return new Raspisanie(id, name, desc, place, teacher, st_time, end_time, week);
    }

    public ArrayList<Raspisanie> selectAll(){

        dataBase = mOpenHelper.getReadableDatabase();
        Cursor cursor = dataBase.query(TABLE_NAME, null, null, null, null, null, ID);

        ArrayList<Raspisanie> arr = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            do {
                long id = cursor.getLong(NUM_ID);
                String name = cursor.getString(NUM_NAME);
                String desc = cursor.getString(NUM_DESCRIPTION);
                String place = cursor.getString(NUM_PLACE);
                String teacher = cursor.getString(NUM_TEACHER);
                String st_time = cursor.getString(NUM_ST_TIME);
                String end_time = cursor.getString(NUM_END_TIME);
                String week = cursor.getString(NUM_WEEK);
                arr.add(new Raspisanie(id, name, desc, place, teacher, st_time, end_time, week));
            } while (cursor.moveToNext());
        }
        cursor.close();
        dataBase.close();
        return arr;
    }


    private class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "create table Raspisanie ("
                    + "_id integer primary key autoincrement,"
                    + "name text,"
                    + "description text,"
                    + "place text,"
                    + "teacher text,"
                    + "st_time text,"
                    + "end_time text,"
                    + "week text" + ");";

            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


}
