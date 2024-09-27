package com.rajdeep.agastipharmaceuticals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "agastiDB";
    private static final String TABLE_DB = "tablelogin";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_CID = "cid";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE_NO = "contactno";
    private static final String KEY_ADDRESS = "addresss";




    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DB + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CID + " TEXT," + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PHONE_NO + " TEXT," + KEY_ADDRESS + " TEXT" + ")");

    }

    // For inserting the query...
    public void addDetails(String cid, String name, String email, String contactno, String address) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CID, cid);
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_PHONE_NO, contactno);
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_ADDRESS, address);

        database.insert(TABLE_DB, null, contentValues);
    }


    // For displaying (selecting) the data row of customer details...
    public String[] fetchData() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DB + " LIMIT 1", null);    // Meaning of star * is all columns of a row. We can give there the name of a row to select it and can give multiple names by giving comma, in between them.

        String[] stringArray = new String[5];

        while (cursor.moveToNext()) {
            stringArray[0] = cursor.getString(2);   // name
            stringArray[1] = cursor.getString(3);   // email
            stringArray[2] = cursor.getString(4);   // contact
            stringArray[3] = cursor.getString(5);   // address
            stringArray[4] = cursor.getString(1);   // cid (index starts from 0 (from id))
        }

        return stringArray;
    }

    public String getCid(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DB + " LIMIT 1", null);
        String cid = "";

        while(cursor.moveToNext()) {
            cid  = cursor.getString(1);
            System.out.println("FROM DATABASE CID: " + cid);
        }
        return cid;
    }

    public void deleteEverythingFromTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DB, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DB);    // This will remove the old table
        onCreate(sqLiteDatabase);      // This method is used to make a newer version of a table.

    }
}
