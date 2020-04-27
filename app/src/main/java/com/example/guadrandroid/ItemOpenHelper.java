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

public class ItemOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteFunTag";

    private static final String DATABASE_NAME = "itemDatabase";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_ITEMS = "tableItems";
    private static final String ITEM_ID = "_id";
    static final String ITEM_NAME = "itemName";
    private static final String ITEM_DESCRIPTION = "itemDescription";
    private static final String ITEM_SELLER = "itemSeller";
    private static final String ITEM_PRICE = "itemPrice";

    /**
     * Constructor for the Seller open Helper class
     * @param context
     */
    public ItemOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // this is where we create our database tables
        // this method is only called once... called after
        // first call to getWriteableDatabase()
        // we construct Strings to represent SQL (structured query language)
        // commands/statements

        String sqlCreate = "CREATE TABLE " + TABLE_ITEMS + "(" +
                ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM_NAME + " TEXT, " +
                ITEM_SELLER + " TEXT, " +
                ITEM_PRICE + " TEXT, " +
                ITEM_DESCRIPTION + " TEXT)";
        Log.d(TAG, "onCreate: " + sqlCreate);

        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Contains a select statement returning all items stored in the database
     * for the current seller
     * @return a cursor referencing all items
     */
    public Cursor getSelectAllItemsCursor(String currentSeller) {
        String sqlSelect = "SELECT * FROM " + TABLE_ITEMS;

        // get a reference to a database for reading
        SQLiteDatabase db = getReadableDatabase();
        // use rawQuery() to execute the select "query"
        // queries return a Cursor reference
        // walk through the query result set using the Cursor
        Cursor cursor = db.rawQuery(sqlSelect, null);
        // don't close the database!! the cursor needs it open
        return cursor;
    }
public Cursor getItemsFromSellerCursor (String currentSeller){
    String sqlSelect = "SELECT * FROM " + TABLE_ITEMS
            + " WHERE " + ITEM_SELLER + "=" + "'" + currentSeller + "'";

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
     * @param item the new item to be inserted
     */
    public void insertItem(Item item) {
        String sqlInsert = "INSERT INTO " + TABLE_ITEMS + " VALUES(null, '" +
                item.getItemName() + "', '"
                + item.getItemSeller() + "', '"
                + item.getItemPrice() + "', '"
                + item.getItemDescription() + "'"+ ")";
        // get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        Log.d(TAG, "insertSeller: " + sqlInsert);
        db.execSQL(sqlInsert);
        // always close the writeable database
        db.close();
    }


    /**
     * contains the sql for the note to be updated in the database
     * @param id the id of the seller to be updated
     * @param newItem the new contents of the seller
     */
    public void updateItemById(int id, Item newItem) {
        // UPDATE tableContacts SET name='SPIKE', phoneNumber='208-208-2082' WHERE _id=1
        String sqlUpdate =
                "UPDATE " + TABLE_ITEMS + " SET " + ITEM_NAME + "='" +
                        newItem.getItemName() + "', " + ITEM_SELLER + "='" +
                        newItem.getItemSeller() + "', " + ITEM_DESCRIPTION + "='" +
                        newItem.getItemDescription() + "', " + ITEM_PRICE + "='" +
                        newItem.getItemPrice() + "' WHERE " + ITEM_ID + "=" + id;
        Log.d(TAG, "updateSellerById: " + sqlUpdate);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    /**
     * A function to delete all items in the database
     */
    public void deleteAllItems(String currentSeller) {
        // DELETE FROM tableContacts
        String sqlDelete = "DELETE FROM " + TABLE_ITEMS + " WHERE " + ITEM_SELLER
                + "=" + "'" + currentSeller + "'";
        Log.d(TAG, "deleteAllNotes: " + sqlDelete);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }


    /**
     * function to delete a specific item
     * @param id the item to be deleted
     */
    public void deleteItemByID(int id){
        String sqlDelete = "DELETE FROM " + TABLE_ITEMS
                + " WHERE " + ITEM_ID + "=" + id;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }


}