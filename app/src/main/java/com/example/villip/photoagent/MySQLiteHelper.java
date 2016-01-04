package com.example.villip.photoagent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Lesson5db";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table
        String CREATE_AD_TABLE = "CREATE TABLE " + Ad.TABLE_NAME + " ( " +
                Ad.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Ad.KEY_PLACE + " TEXT, " +
                Ad.KEY_TIME + " TEXT, " +
                Ad.KEY_DATE + " TEXT, " +
                Ad.KEY_IMAGE + " BLOB )";

        // create table
        db.execSQL(CREATE_AD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS books");
        // create fresh table
        this.onCreate(db);
    }

    /**
     * Add Ad object to the table
     *
     * @param ad advertising to add in the table
     */
    public void addAdvertising(Ad ad) {
        Log.d("addAdvertising", ad.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(ad.KEY_PLACE, ad.getPlace());
        values.put(ad.KEY_TIME, ad.getTime());
        values.put(ad.KEY_DATE, ad.getDate());
        values.put(ad.KEY_IMAGE, Util.bitmapToByteArray(ad.getImage()));

        // 3. insert
        db.insert(ad.TABLE_NAME, null, values);

        // 4. close
        db.close();
    }

    /**
     * Return ad from DB
     *
     * @param id ID of the required Ad
     * @return ad object from DB
     */
    public Ad getAd(int id) {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(Ad.TABLE_NAME, // a. table
                Ad.COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by - how to group rows
                null, // f. having - which row groups to include (filter)
                null, // g. order by
                null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build ad object
        Ad ad = new Ad();
        ad.setId(Integer.parseInt(cursor.getString(0)));
        ad.setPlace(cursor.getString(1));
        ad.setTime(cursor.getString(2));
        ad.setDate(cursor.getString(3));
        ad.setImage(Util.byteArrayToBitmap(cursor.getBlob(4)));

        Log.d("getAd(" + id + ")", ad.toString());
        return ad;
    }

    /**
     * Return all ads
     *
     * @return list of all ads from DB
     */
    public ArrayList<Ad> getAllAds() {
        ArrayList<Ad> ads = new ArrayList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + Ad.TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build ad and add it to list
        Ad ad = null;
        if (cursor.moveToFirst()) {
            do {
                ad = new Ad();
                ad.setId(Integer.parseInt(cursor.getString(0)));
                ad.setPlace(cursor.getString(1));
                ad.setTime(cursor.getString(2));
                ad.setDate(cursor.getString(3));
                ad.setImage(Util.byteArrayToBitmap(cursor.getBlob(4)));
                ads.add(ad);
            } while (cursor.moveToNext());
        }

        Log.d("getAllAds()", ads.toString());
        return ads;
    }

    /**
     * Update ad with new one
     *
     * @param ad new ad
     * @return number of rows affected
     */
    public int updateAd(Ad ad) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(ad.KEY_PLACE, ad.getPlace());
        values.put(ad.KEY_TIME, ad.getTime());
        values.put(ad.KEY_DATE, ad.getDate());
        values.put(ad.KEY_IMAGE, Util.bitmapToByteArray(ad.getImage()));

        // 3. updating row
        int i = db.update(Ad.TABLE_NAME, values, Ad.KEY_ID + " = ?",
                new String[]{String.valueOf(ad.getId())});

        // 4. close
        db.close();

        Log.d("updateAd", ad.toString());
        return i;
    }

    /**
     * Delete Ad
     *
     * @param ad Ad to delete
     */
    public void deleteAd(Ad ad) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(Ad.TABLE_NAME, Ad.KEY_ID + " = ?", new String[]{String.valueOf(ad.getId())});

        // 3. close
        db.close();

        Log.d("deleteAd", ad.toString());
    }

}