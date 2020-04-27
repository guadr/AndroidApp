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
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ViewItemPage extends AppCompatActivity {
    boolean isNewItem;//boolean to see whether the note is a new or already created note
    int itemPosition;//the position of the note in the list


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayout gridLayout = new GridLayout(this);
        setContentView(gridLayout);
        gridLayout.setColumnCount(2);
        setContentView(gridLayout);
        final TextView textView = createTextView(gridLayout);//the edit text for name
        final TextView textView2 = createTextView2(gridLayout);//the edit text for description
        final  TextView textView3 = createTextView3(gridLayout);//the edit text for price


        Intent intent = getIntent();
        if(intent!=null) {
                itemPosition = intent.getIntExtra("position", 0);
                String itemName = intent.getStringExtra("itemName");
                String itemPrice = intent.getStringExtra("itemPrice");
                String itemDescription = intent.getStringExtra("itemDescription");
                textView.setText(itemName);
                textView2.setText(itemDescription);
                textView3.setText(itemPrice);
        }

        Button button = createButton(gridLayout);//adding the done button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
        });
    }


    @Override
    /**
     * handling if user presses the android back button
     */
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED,intent);
        ViewItemPage.this.finish();
    }

    /**
     * This function sets the layout parameters for the edit text and adds it to the view
     * @param gridLayout the layout of note activity
     * @return the set up edit text for the title
     */
    private TextView createTextView(GridLayout gridLayout){
        TextView textView = new TextView(this);
        GridLayout.LayoutParams textParams= new GridLayout.LayoutParams();
        textParams.width = 0;
        textParams.height = 0;
        textParams.rowSpec = GridLayout.spec(0,1,.5f);
        textParams.columnSpec=GridLayout.spec(0,1,1f);
        textView.setTextSize(30);
        textView.setLayoutParams(textParams);
        gridLayout.addView(textView);
        return textView;
    }

    /**
     * This function sets the layout parameters for the edit text and adds it to the view
     * @param gridLayout the layout of itemPage activity
     * @return the set up edit text for content
     */
    private TextView createTextView2(GridLayout gridLayout){
        TextView textView2 = new TextView(this);
        GridLayout.LayoutParams textParams= new GridLayout.LayoutParams();
        textParams.width = 0;
        textParams.height = 0;
        textParams.rowSpec = GridLayout.spec(2,1,2);
        textParams.columnSpec=GridLayout.spec(0,2,1);
        textView2.setTextSize(30);
        textView2.setGravity(Gravity.TOP);
        textView2.setLayoutParams(textParams);
        gridLayout.addView(textView2);
        return textView2;
    }
    /**
     * This function sets the layout parameters for the edit text and adds it to the view
     * @param gridLayout the layout of itemPage activity
     * @return the set up edit text for price
     */
    private TextView createTextView3(GridLayout gridLayout){
        TextView textView3 = new TextView(this);
        GridLayout.LayoutParams textParams= new GridLayout.LayoutParams();
        textParams.width = 0;
        textParams.height = 0;
        textParams.rowSpec = GridLayout.spec(1,1,.5f);
        textParams.columnSpec=GridLayout.spec(0,2,1);
        textView3.setTextSize(30);
        textView3.setGravity(Gravity.TOP);
        textView3.setLayoutParams(textParams);
        gridLayout.addView(textView3);
        return textView3;
    }


    /**
     * This function sets the layout parameters for the create button and adds it to the view
     * @param gridLayout the layout of note activity
     * @return the set up create button
     */
    private Button createButton(GridLayout gridLayout){
        Button button = new Button(this);
        GridLayout.LayoutParams buttonParams = new GridLayout.LayoutParams();
        buttonParams.width = 0;
        buttonParams.height = 0;
        buttonParams.rowSpec = GridLayout.spec(3,1,.5f);
        buttonParams.columnSpec=GridLayout.spec(0,2,2);
        button.setText(R.string.done_button);
        button.setLayoutParams(buttonParams);
        gridLayout.addView(button);
        return button;
    }
}