/**
 * This program is a note taking app
 * CPSC 312-01, Fall 2019
 * Programming Assignment #7
 * Icons made by Freepik from www.flaticon.com
 *
 * @author Jackson Paris
 * @version v1.0 11/19/19
 */

package com.example.guadrandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StorePageOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteFunTag";

    private static final String DATABASE_NAME = "sellerDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SELLERS = "tableSellers";
    private static final String SELLER_ID = "_id";
    private static final String SELLER_NAME = "sellerName";
    private static final String SELLER_DESCRIPTION = "sellerDescription";

    /**
     * Constructor for the Note open Helper class
     * @param context
     */
    public StorePageOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // this is where we create our database tables
        // this method is only called once... called after
        // first call to getWriteableDatabase()
        // we construct Strings to represent SQL (structured query language)
        // commands/statements

        String sqlCreate = "CREATE TABLE " + TABLE_SELLERS + "(" +
                SELLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SELLER_NAME + " TEXT, " +
                SELLER_DESCRIPTION + " TEXT)";
        Log.d(TAG, "onCreate: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Contains a select statement returning all notes stored in the database
     * @return a cursor referencing all notes
     */
    public Cursor getSelectAllNotesCursor() {
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
     * Contains the sql to insert a note into the database
     * @param seller the new seller to be inserted
     */
    public void insertSeller(Seller seller) {
        String sqlInsert = "INSERT INTO " + TABLE_SELLERS + " VALUES(null, '" +
                seller.getSellerName() + "', '"
                + seller.getSellerDescription() + ")";
        // get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        Log.d(TAG, "insertSeller: " + sqlInsert);
        db.execSQL(sqlInsert);
        // always close the writeable database
        db.close();

    }


    /**
     * contains the sql for the note to be updated in the database
     * @param id the id of the note to be updated
     * @param newSeller the new contents of the note
     */
    public void updateSellerById(int id, Seller newSeller) {
        // UPDATE tableContacts SET name='SPIKE', phoneNumber='208-208-2082' WHERE _id=1
        String sqlUpdate =
                "UPDATE " + TABLE_SELLERS + " SET " + SELLER_NAME + "='" +
                        newSeller.getSellerName() + "', "
                        + SELLER_DESCRIPTION + "='" + newSeller.getSellerDescription()
                        + "' WHERE " + SELLER_ID + "=" + id;
        Log.d(TAG, "updateSellerById: " + sqlUpdate);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    /**
     * A function to delete all notes in the database
     */
    public void deleteAllSellers() {
        // DELETE FROM tableSellers
        String sqlDelete = "DELETE FROM " + TABLE_SELLERS;
        Log.d(TAG, "deleteAllSellers: " + sqlDelete);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }

    /**
     * function to delete a specific note
     * @param id the note to be deleted
     */
    public void deleteSellerByID(int id){
        String sqlDelete = "DELETE FROM " + TABLE_SELLERS
                + " WHERE " + SELLER_ID + "=" + id;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }


}