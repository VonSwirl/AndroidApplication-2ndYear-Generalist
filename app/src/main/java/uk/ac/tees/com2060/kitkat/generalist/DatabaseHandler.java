package uk.ac.tees.com2060.kitkat.generalist;

/**
 * Created by q5052694 on 07/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Lists.db";

    //Contacts table name
    private static final String TABLE_NAME = "lists";

    //contacts table columns name
    private static final String COL_ID = "_id"; //primary key must keep this name
    private static final String COL_ACTIVE = "active";
    private static final String COL_NAME = "name";
    private static final String COL_CONTENTS = "contents";
    private static final String COL_CAT = "category";
    private static final String COL_DATE = "date";
    private static final String COL_CHECKED = "checked";

    //default constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Generate Create SQL Statement
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE_NAME
                + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_CONTENTS + " TEXT,"
                + COL_CAT + " TEXT,"
                + COL_ACTIVE + " INTEGER,"
                + COL_DATE + " INTEGER,"
                + COL_CHECKED + " INTEGER"
                + ")";
        // Execute/run create SQL statement
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.d("Database", "Database Created.");
    }

    public void onUpgrade(SQLiteDatabase db, int oldNum, int newNum) {
        //Drop older table if exists and create a new
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addList(ListInfo list) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, list.getName());
        values.put(COL_CONTENTS, list.getContents());
        values.put(COL_CAT, list.getCategory());
        values.put(COL_ACTIVE, list.getActive());
        values.put(COL_DATE, list.getEpochDate());
        values.put(COL_CHECKED, list.getChecked());
        //Id not needed because auto inc

        //Add record to db and get id of new record( must be long )
        long id = db.insert(TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public List<ListInfo> getOne(int id) {


        List<ListInfo> item = new ArrayList<ListInfo>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "=" + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            //Get position of each of the column names
            int idIdx = cursor.getColumnIndex(COL_ID);
            int nameIdx = cursor.getColumnIndex(COL_NAME);
            int contentIdx = cursor.getColumnIndex(COL_CONTENTS);
            int categoryIdx = cursor.getColumnIndex(COL_CAT);
            int activeIdx = cursor.getColumnIndex(COL_ACTIVE);
            int dateIdx = cursor.getColumnIndex(COL_DATE);
            int checkedIdx = cursor.getColumnIndex(COL_CHECKED);

            do {
                // Create list object for current database record
                ListInfo usrList = new ListInfo(
                        cursor.getInt(idIdx),
                        cursor.getString(nameIdx),
                        cursor.getString(contentIdx),
                        cursor.getString(categoryIdx),
                        cursor.getInt(activeIdx),
                        cursor.getLong(dateIdx),
                        cursor.getInt(checkedIdx)
                );

                item.add(usrList);

            } while (cursor.moveToNext());
        }
        return item;
    }

    public List<ListInfo> getAll() {

        //Create empty list
        List<ListInfo> list = new ArrayList<ListInfo>();

        //Connect to db and read
        SQLiteDatabase db = this.getReadableDatabase();

        //Execute select statement
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            //Get position of each of the column names
            int idIdx = cursor.getColumnIndex(COL_ID);
            int nameIdx = cursor.getColumnIndex(COL_NAME);
            int contentIdx = cursor.getColumnIndex(COL_CONTENTS);
            int categoryIdx = cursor.getColumnIndex(COL_CAT);
            int activeIdx = cursor.getColumnIndex(COL_ACTIVE);
            int dateIdx = cursor.getColumnIndex(COL_DATE);
            int checkedIdx = cursor.getColumnIndex(COL_CHECKED);

            do {
                // Create lecturer object for current database record
                ListInfo usrList = new ListInfo(
                        cursor.getInt(idIdx),
                        cursor.getString(nameIdx),
                        cursor.getString(contentIdx),
                        cursor.getString(categoryIdx),
                        cursor.getInt(activeIdx),
                        cursor.getLong(dateIdx),
                        cursor.getInt(checkedIdx)
                );

                list.add(usrList);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public void updateByID(int id, String name, String content, String cat, long date) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_CONTENTS, content);
        values.put(COL_CAT, cat);
        values.put(COL_DATE, date);
        //values.put(COL_CHECKED, checked);
        db.update(TABLE_NAME, values, COL_ID + "=" + id, null);
    }

    //Simple method for updating the check boxes
    public void updateChecked(int id, int checked){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(COL_CHECKED, checked);
        db.update(TABLE_NAME, value, COL_ID + "=" + id, null);
    }

    public void removeAll() {

        //Connect to tables
        SQLiteDatabase db = this.getWritableDatabase();

        //Exe delete table SQL command
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //call create method to regen table
        onCreate(db);
    }

    public void deleteItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        int softDelete = 0;

        ContentValues values = new ContentValues();
        values.put(COL_ACTIVE, softDelete);
        db.update(TABLE_NAME, values, COL_ID + "=" + id, null);
    }
}