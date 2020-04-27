/**
 * This program is a proof of concept app for
 * an android version of the GUADR web application.
 *
 * Anyone is free to take and expand upon this code with credit
 * @author Jackson Paris
 * @version v1.0 4/26/20
 */

package com.example.guadrandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class UserMenu extends AppCompatActivity {
    static final String TAG = "SQLiteFunTag";
    int list_position;
    SimpleCursorAdapter cursorAdapter;
    Cursor cursor;
    ItemOpenHelper openHelper;
    String itemSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayout gridLayout = new GridLayout(this);//layout for  activity
        setContentView(gridLayout);
        gridLayout.setColumnCount(1);
        final ListView listView = createListView(gridLayout);
        Intent intent = getIntent();

        if(intent != null) {
            itemSeller = intent.getStringExtra("sellerName");
            Toast.makeText(this,itemSeller,Toast.LENGTH_LONG).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//listener for the item list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openHelper = new ItemOpenHelper(UserMenu.this);
                cursor=openHelper.getSelectAllItemsCursor(itemSeller);
                cursor.moveToPosition(position);
                String itemName = cursor.getString(cursor.getColumnIndex("itemName"));
                String itemDescription = cursor.getString(cursor.getColumnIndex("itemDescription"));
                String itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
                list_position = position;
                Intent intent = new Intent(UserMenu.this, ViewItemPage.class);
                intent.putExtra("itemName",itemName);
                intent.putExtra("itemDescription",itemDescription);
                intent.putExtra("itemPrice",itemPrice);
                startActivityForResult(intent, 0);//starting item activity
            }
        });

        ItemOpenHelper openHelper = new ItemOpenHelper(this);
        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                openHelper.getSelectAllItemsCursor(itemSeller),
                // parallel arrays... first is names of columns to get data FROM
                new String[] {ItemOpenHelper.ITEM_NAME},
                // ids of textviews to put data IN
                new int[] {android.R.id.text1},
                0
        );
        listView.setAdapter(cursorAdapter);
    }


    /**
     * This function sets the layout parameters for the listview and adds it to the view
     * @param gridLayout the layout of main activity
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