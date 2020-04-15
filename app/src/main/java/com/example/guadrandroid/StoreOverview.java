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

public class StoreOverview extends AppCompatActivity {

    SimpleCursorAdapter cursorAdapter;
    Cursor cursor;
    StorePageOpenHelper openHelper;
    int list_position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_overview);
        GridLayout gridLayout = new GridLayout(this);//layout for main activity
        setContentView(gridLayout);
        gridLayout.setColumnCount(1);
        final ListView listView = createListView(gridLayout);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//listener for the seller list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openHelper = new StorePageOpenHelper(StoreOverview.this);
                cursor=openHelper.getSelectAllNotesCursor();
                cursor.moveToPosition(position);
                String sellerName = cursor.getString(cursor.getColumnIndex("sellerName"));
                String sellerDescription = cursor.getString(cursor.getColumnIndex("sellerDescription"));

                list_position = position;
                Intent intent = new Intent(StoreOverview.this,SellerPage.class);
                intent.putExtra("sellerName", sellerName);//passing the already created content
                intent.putExtra("sellerDescription", sellerDescription);
                intent.putExtra("noteCreated",false);
                intent.putExtra("position", list_position);
                startActivityForResult(intent, 0);//starting item activity
            }
        });
    }

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
