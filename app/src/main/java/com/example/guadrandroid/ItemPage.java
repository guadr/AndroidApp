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


public class ItemPage extends AppCompatActivity {
    boolean isNewItem;//boolean to see whether the note is a new or already created note
    int itemPosition;//the position of the note in the list
    String itemSeller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayout gridLayout = new GridLayout(this);
        setContentView(gridLayout);
        gridLayout.setColumnCount(2);
        setContentView(gridLayout);
        final EditText editText = createEditText(gridLayout);//the edit text for name
        final EditText editText2 = createEditText2(gridLayout);//the edit text for description
        final EditText editText3 = createEditText3(gridLayout);//the edit text for price

        Intent intent = getIntent();
        if(intent!=null) {
            isNewItem = intent.getBooleanExtra("itemIsNew", false);
            itemSeller = intent.getStringExtra("itemSeller");//getting the already entered content
            if (!isNewItem) {//if item has already been created
                itemPosition = intent.getIntExtra("position", 0);
                String itemName = intent.getStringExtra("itemName");
                String itemPrice = intent.getStringExtra("itemPrice");
                String itemDescription = intent.getStringExtra("itemDescription");
                editText.setText(itemName);
                editText2.setText(itemDescription);
                editText3.setText(itemPrice);
            }
        }

        Button button = createButton(gridLayout);//adding the done button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemName = editText.getText().toString();//getting the user entered content
                String itemDescription = editText2.getText().toString();
                String itemPrice = editText3.getText().toString();

                //checking for empty fields
                if (itemName.isEmpty() || itemDescription.isEmpty()|| itemPrice.isEmpty()){
                    Toast.makeText(ItemPage.this, "please make " +
                            "sure all fields are filled",Toast.LENGTH_LONG).show();
                }
                else {
                    //passing item description to next activity
                    Intent intent2 = new Intent(ItemPage.this, SellerPage.class);
                    intent2.putExtra("itemName", itemName);
                    intent2.putExtra("itemSeller",itemSeller);
                    intent2.putExtra("itemDescription", itemDescription);
                    intent2.putExtra("itemPrice", itemPrice);
                    intent2.putExtra("position", itemPosition);
                    intent2.putExtra("itemIsNew",isNewItem);
                    setResult(Activity.RESULT_OK, intent2);
                    finish();
                }
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
        setResult(Activity.RESULT_CANCELED,intent);//canceled not result okay
        ItemPage.this.finish();
    }

    /**
     * This function sets the layout parameters for the edit text and adds it to the view
     * @param gridLayout the layout of note activity
     * @return the set up edit text for the title
     */
    private EditText createEditText(GridLayout gridLayout){
        EditText editText = new EditText(this);
        GridLayout.LayoutParams textParams= new GridLayout.LayoutParams();
        textParams.width = 0;
        textParams.height = 0;
        editText.setHint("item name");
        textParams.rowSpec = GridLayout.spec(0,1,.5f);
        textParams.columnSpec=GridLayout.spec(0,1,1f);
        editText.setLayoutParams(textParams);
        gridLayout.addView(editText);
        return editText;
    }

    /**
     * This function sets the layout parameters for the edit text and adds it to the view
     * @param gridLayout the layout of itemPage activity
     * @return the set up edit text for content
     */
    private EditText createEditText2(GridLayout gridLayout){
        EditText editText2 = new EditText(this);
        GridLayout.LayoutParams textParams= new GridLayout.LayoutParams();
        textParams.width = 0;
        textParams.height = 0;
        textParams.rowSpec = GridLayout.spec(2,1,2);
        textParams.columnSpec=GridLayout.spec(0,2,1);
        editText2.setGravity(Gravity.TOP);
        editText2.setHint("item description");
        editText2.setLayoutParams(textParams);
        gridLayout.addView(editText2);
        return editText2;
    }
    /**
     * This function sets the layout parameters for the edit text and adds it to the view
     * @param gridLayout the layout of itemPage activity
     * @return the set up edit text for price
     */
    private EditText createEditText3(GridLayout gridLayout){
        EditText editText3 = new EditText(this);
        GridLayout.LayoutParams textParams= new GridLayout.LayoutParams();
        textParams.width = 0;
        textParams.height = 0;
        textParams.rowSpec = GridLayout.spec(1,1,.5f);
        textParams.columnSpec=GridLayout.spec(0,2,1);
        editText3.setGravity(Gravity.TOP);
        editText3.setHint("item price");
        editText3.setLayoutParams(textParams);
        gridLayout.addView(editText3);
        return editText3;
    }


    /**
     * This function sets the layout parameters for the create button and adds it to the view
     * @param gridLayout the layout of ItemPage activity
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