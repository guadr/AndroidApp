/**
 * This program is a proof of concept app for
 * an android version of the GUADR web application.
 *
 * Anyone is free to take and expand upon this code with credit
 * @author Jackson Paris
 * @version v1.0 4/26/20
 */

package com.example.guadrandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class openSellerHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteFunTag";

    private static final String DATABASE_NAME = "sellerDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SELLERS = "tableSellers";
    static final String SELLER_NAME = "sellerName";
    private static final String SELLER_ID = "_id";
    static final String SELLER_EMAIL = "sellerEmail";
    private static final String SELLER_DESCRIPTION = "sellerDescription";
    private static final String SELLER_PASSWORD = "password";


    /**
     * Constructor for the Seller open Helper class
     * @param context
     */
    public openSellerHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    //creating the table for seller tables
        String sqlCreate = "CREATE TABLE " + TABLE_SELLERS + "(" +
                SELLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SELLER_NAME + " TEXT, " +
                SELLER_EMAIL + " EMAIL, " +
                SELLER_DESCRIPTION + " TEXT, " +
                SELLER_PASSWORD + " TEXT)";
        Log.d(TAG, "onCreate: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Contains a select statement returning all sellers stored in the database
     * @return a cursor referencing all sellers
     */
    public Cursor getSelectAllSellersCursor() {
        // SELECT * FROM tableContacts
        String sqlSelect = "SELECT * FROM " + TABLE_SELLERS;
        // get a reference to a database for reading
        SQLiteDatabase db = getReadableDatabase();
        // use rawQuery() to execute the select "query"
        // queries return a Cursor reference
        // walk through the query result set using the Cursor
        Cursor cursor = db.rawQuery(sqlSelect, null);
        // don't close the database!! the cursor needs it open
        return cursor;
    }

    /**
     * Contains the sql to insert a seller into the database
     * @param seller the new seller to be inserted
     */
    public void insertSeller(Seller seller) {
        String sqlInsert = "INSERT INTO " + TABLE_SELLERS + " VALUES(null, '" +
                seller.getSellerName() + "', '" +
                seller.getSellerEmail() + "', '" +
                seller.getSellerDescription() + "', '" +
                seller.getSellerPassword() + "'" + ")";
        // get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        Log.d(TAG, "insertSeller: " + sqlInsert);
        db.execSQL(sqlInsert);
        // always close the writeable database
        db.close();
    }

    /**
     * validating the entered email and password for the current seller login
     * @param enteredPassword the user entered email
     * @param email the user entered password
     * @return true if valid false if not
     */
    public Boolean validateSeller(String enteredPassword, String email){
        String sqlSelect = "SELECT * FROM " + TABLE_SELLERS
                + " WHERE " + SELLER_EMAIL + "=" + "'" + email + "'";

        SQLiteDatabase db = getReadableDatabase();
        Log.d(TAG, "insertSeller: " + sqlSelect);
        Cursor cursor = db.rawQuery(sqlSelect, null);
        if(cursor.moveToFirst()) {
            String dbPassword = cursor.getString(4);
            if(dbPassword.equals(enteredPassword)){
                return true;
            }
            else{
                return false;
            }
        }
        // always close the database
        db.close();
        return false;
    }
}