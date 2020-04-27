
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

public class openUserHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteFunTag";

    private static final String DATABASE_NAME = "userDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "tableUsers";
    private static final String NAME = "name";
    private static final String USER_ID = "_id";
    static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final Boolean IS_SELLER = false;


    /**
     * Constructor for the Seller open Helper class
     * @param context
     */
    public openUserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creating the table for users in the database

        String sqlCreate = "CREATE TABLE " + TABLE_USERS + "(" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                EMAIL + " EMAIL, " +
                PASSWORD + " TEXT)";
        Log.d(TAG, "onCreate: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Contains a select statement returning all users stored in the database
     * @return a cursor referencing all users
     */
    public Cursor getSelectAllUsersCursor() {
        // SELECT * FROM tableContacts
        String sqlSelect = "SELECT * FROM " + TABLE_USERS;
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
     * Contains the sql to insert a user into the database
     * @param user the new user to be inserted
     */
    public void insertUser(User user) {
        String sqlInsert = "INSERT INTO " + TABLE_USERS + " VALUES(null, '" +
                user.getName() + "', '" +
                user.getEmail() + "', '" +
                user.getPassword() + "'"+ ")";
        // get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        Log.d(TAG, "insertSeller: " + sqlInsert);
        db.execSQL(sqlInsert);
        // always close the writeable database
        db.close();
    }

    /**
     *validating user credentials
     * @param enteredPassword the password the user entered
     * @param enteredEmail the email the user entered
     * @return true if matching in database false if not
     */
    public Boolean validateUser(String enteredPassword, String enteredEmail){
        String sqlSelect = "SELECT * FROM " + TABLE_USERS
                + " WHERE " + EMAIL + "=" + "'" + enteredEmail + "'";

        SQLiteDatabase db = getReadableDatabase();
        Log.d(TAG, "insertSeller: " + sqlSelect);
        Cursor cursor = db.rawQuery(sqlSelect, null);
        if(cursor.moveToFirst()) {
            String dbPassword = cursor.getString(3);
            if(dbPassword.equals(enteredPassword)){
                return true;
            }
            else{
                return false;
            }

            }
        // always close the database
        return false;
    }

}
