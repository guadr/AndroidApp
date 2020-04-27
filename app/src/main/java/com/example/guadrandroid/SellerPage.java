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
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class SellerPage extends AppCompatActivity {
    static final String TAG = "SQLiteFunTag";
    int list_position;//posisiton in the listview
    SimpleCursorAdapter cursorAdapter;//adapter for list
    Cursor cursor;
    ItemOpenHelper openHelper;
    String itemSeller;

    boolean isNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayout gridLayout = new GridLayout(this);//layout for seller page activity
        setContentView(gridLayout);
        gridLayout.setColumnCount(1);
        Intent intent = getIntent();
        if(intent != null) {
            itemSeller = intent.getStringExtra("sellerName");//getting name from login
        }
        final ListView listView = createListView(gridLayout);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//listener for the item list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openHelper = new ItemOpenHelper(SellerPage.this);
                cursor=openHelper.getSelectAllItemsCursor(itemSeller);
                cursor.moveToPosition(position);//moving cursor to clicked position
                //getting data from user clicked on item
                String itemName = cursor.getString(cursor.getColumnIndex("itemName"));
                String itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
                String itemDescription = cursor.getString(cursor.getColumnIndex("itemDescription"));
                list_position = position;
                Intent intent = new Intent(SellerPage.this, ItemPage.class);
                intent.putExtra("itemName", itemName);//passing the already created content
                intent.putExtra("itemSeller", itemSeller);
                intent.putExtra("itemPrice", itemPrice);
                intent.putExtra("itemDescription", itemDescription);
                intent.putExtra("itemCreated",false);
                intent.putExtra("position", list_position);
                startActivityForResult(intent, 0);//starting item activity
            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // set the multi choice listener
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                int numChecked = listView.getCheckedItemCount();//getting number of items selected
                actionMode.setTitle(numChecked + " selected");//showing selected items
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();//inflating cam menu to screen
                menuInflater.inflate(R.menu.cam_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.deleteItem) {
                    SparseBooleanArray actionArray = listView.getCheckedItemPositions();
                    //above array is sparsely populated with items that have been selected
                    for (int i = 0; i <= actionArray.size(); i++) {//loop to delete all selected items
                        if (actionArray.get(i)) {
                            cursor.moveToPosition(i);
                            openHelper.deleteItemByID(cursor.getInt(0));
                        }
                    }
                    Log.d(TAG, "delete cam items");
                    cursor = openHelper.getSelectAllItemsCursor(itemSeller);
                    cursorAdapter.changeCursor(cursor);//deleting selected items
                    actionMode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                // don't need this
            }
        });

        ItemOpenHelper openHelper = new ItemOpenHelper(this);
        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                openHelper.getItemsFromSellerCursor(itemSeller),
                // parallel arrays... first is names of columns to get data FROM
                new String[] {ItemOpenHelper.ITEM_NAME},
                // ids of textviews to put data IN
                new int[] {android.R.id.text1},
                0
        );
        listView.setAdapter(cursorAdapter);
    }


    @Override
    /**
     * Used to inflate the options menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // get a reference to the MenuInflater
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    /**
     * called when user selects an item in the options menu
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addMenuItem:
                Intent intent = new Intent(SellerPage.this, ItemPage.class);
                intent.putExtra("itemIsNew", true);//this is a new item
                intent.putExtra("position", -1);
                startActivityForResult(intent, 0);
                return true; // we have consumed/handled this click event
            case R.id.deleteMenuItem:
                AlertDialog.Builder alertBuilder =
                        new AlertDialog.Builder(SellerPage.this);//alert if the user wants to delete all items
                alertBuilder.setTitle("Delete Items");
                alertBuilder.setMessage("Are you sure you want to delete all items")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openHelper = new ItemOpenHelper(SellerPage.this);
                                openHelper.deleteAllItems(itemSeller);
                                cursor = openHelper.getSelectAllItemsCursor(itemSeller);
                                cursorAdapter.swapCursor(cursor);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();//user said no
                            }
                        });
                alertBuilder.show();
                return true;
        }
        return false;
    }
    @Override
    /**
     * handles the return from note Activity and places item in list view based
     * on whether a new item is being added or an item is being updated
     */
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//the data returned by note activity

        if(resultCode== Activity.RESULT_OK) {
            list_position = data.getIntExtra("position",list_position);
            if (data != null) {
                String itemName = data.getStringExtra("itemName");
                String itemSeller = data.getStringExtra("itemSeller");
                String itemPrice = data.getStringExtra("itemPrice");
                String itemDescription = data.getStringExtra("itemDescription");

                isNew = data.getBooleanExtra("itemIsNew", false);

                Item currentItem = new Item(itemPrice,itemName,itemDescription,itemSeller);
                openHelper = new ItemOpenHelper(this);
                if(isNew) {//item is a newly created item
                    openHelper.insertItem(currentItem);// inserting new item into database
                    cursor = openHelper.getSelectAllItemsCursor(itemSeller);
                    cursorAdapter.changeCursor(cursor);
                }
                else {//item has already been created
                    cursor = openHelper.getSelectAllItemsCursor(itemSeller);
                    cursor.moveToPosition(list_position);
                    openHelper.updateItemById(cursor.getInt(0),currentItem);
                    //updating selected item
                }
                cursor = openHelper.getSelectAllItemsCursor(itemSeller);
                cursorAdapter.changeCursor(cursor);
            }
        }
    }


    /**
     * This function sets the layout parameters for the listview and adds it to the view
     * @param gridLayout the layout of SellerPage activity
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
