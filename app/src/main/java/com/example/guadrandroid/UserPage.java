/**
 * This program is a proof of concept app for
 * an android version of the GUADR web application.
 *
 * Anyone is free to take and expand upon this code with credit
 * @author Jackson Paris
 * @version v1.0 4/26/20
 */
package com.example.guadrandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public class UserPage extends AppCompatActivity {
    static final String TAG = "SQLiteFunTag";
    int list_position;
    SimpleCursorAdapter cursorAdapter;
    Cursor cursor;
    ItemOpenHelper openHelper;
    openSellerHelper sellerOpenHelper;

    boolean isNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayout gridLayout = new GridLayout(this);//layout for  activity
        setContentView(gridLayout);
        gridLayout.setColumnCount(1);
        final ListView listView = createListView(gridLayout);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//listener for the item list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sellerOpenHelper = new openSellerHelper(UserPage.this);
                cursor=sellerOpenHelper.getSelectAllSellersCursor();
                cursor.moveToPosition(position);
                String sellerName = cursor.getString(cursor.getColumnIndex("sellerName"));
                String sellerDescription = cursor.getString(cursor.getColumnIndex("sellerDescription"));
                list_position = position;
                Intent intent = new Intent(UserPage.this, UserMenu.class);
                intent.putExtra("sellerName", sellerName);
                intent.putExtra("sellerDescription",sellerDescription);
                intent.putExtra("position", list_position);
                startActivityForResult(intent, 0);//starting item activity
            }
        });


        openSellerHelper openHelper = new openSellerHelper(this);
        cursorAdapter = new SimpleCursorAdapter( // shows all seller items in the listview
                this,
                android.R.layout.simple_list_item_1,
                openHelper.getSelectAllSellersCursor(),
                // parallel arrays... first is names of columns to get data FROM
                new String[] {openSellerHelper.SELLER_NAME},
                // ids of textviews to put data IN
                new int[] {android.R.id.text1},
                0
        );
        listView.setAdapter(cursorAdapter);
    }


    /**
     * This function sets the layout parameters for the listview and adds it to the view
     * @param gridLayout the layout of userpage activity
     * @return the set up list View
     */
    private ListView createListView(GridLayout gridLayout) {
        GridLayout.LayoutParams listParams = new GridLayout.LayoutParams();
        listParams.width = 0;
        listParams.height = 0;
        listParams.rowSpec = GridLayout.spec(1,1,5f);
        listParams.columnSpec=GridLayout.spec(0,1,1f);
        ListView listView = new ListView(this);
        listView.setLayoutParams(listParams);
        gridLayout.addView(listView);
        return listView;
    }
}