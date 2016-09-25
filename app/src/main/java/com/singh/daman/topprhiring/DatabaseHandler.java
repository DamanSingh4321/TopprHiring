package com.singh.daman.topprhiring;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by daman on 25/9/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "events";
    private static final String TABLE_EVENTS = "eventstable";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_FAV = "favourite";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_EXPERIENCE + " TEXT,"
                + KEY_FAV + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        // Create tables again
        onCreate(db);
    }

    public void update(String value, String id ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_FAV, value);

        db.update( TABLE_EVENTS, newValues, "id="+id, null);
    }

    public void addEvents(Events events){
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_ID, events.getId());
            values.put(KEY_NAME, events.getName());
            values.put(KEY_IMAGE, events.getImage());
            values.put(KEY_CATEGORY, events.getCategory());
            values.put(KEY_DESCRIPTION, events.getDescription());
            values.put(KEY_EXPERIENCE, events.getExperience());
            values.put(KEY_FAV, events.getFavourite());

            // Inserting Row
            db.insert(TABLE_EVENTS, null, values);
            //2nd argument is String containing nullColumnHack
            db.close(); // Closing database connection
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Events> getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Events> eventslist = null;
        try{
            eventslist = new ArrayList<Events>();
            String QUERY = "SELECT * FROM "+TABLE_EVENTS;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    Events events = new Events();
                    events.setId(cursor.getString(0));
                    events.setName(cursor.getString(1));
                    events.setImage(cursor.getString(2));
                    events.setCategory(cursor.getString(3));
                    events.setDescription(cursor.getString(4));
                    events.setExperience(cursor.getString(5));
                    events.setFavourite(cursor.getString(6));
                    eventslist.add(events);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return eventslist;
    }

    public int getEventsCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String QUERY = "SELECT * FROM "+TABLE_EVENTS;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            db.close();
            return num;
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return 0;
    }
}
