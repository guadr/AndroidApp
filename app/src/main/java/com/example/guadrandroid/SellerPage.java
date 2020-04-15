
package com.example.guadrandroid;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;


import androidx.appcompat.app.AppCompatActivity;



public class SellerPage extends AppCompatActivity {
    boolean isNewSeller;//boolean to see whether the note is a new or already created note
    int sellerPosition;//the position of the seller in the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridLayout gridLayout = new GridLayout(this);
        setContentView(gridLayout);
        gridLayout.setColumnCount(2);
        setContentView(gridLayout);

        Intent intent = getIntent();
        if(intent!=null) {
            isNewSeller = intent.getBooleanExtra("noteCreated", false);
            if (!isNewSeller) {//if note has already been created
                sellerPosition = intent.getIntExtra("position", 0);
                String sellerName = intent.getStringExtra("sellerName");//getting the already entered content
                String sellerDescription = intent.getStringExtra("sellerDescription");
            }
        }

        Button button = createButton(gridLayout);//adding the done button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SellerPage.this, StoreOverview.class);
                setResult(Activity.RESULT_OK, intent2);
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
        SellerPage.this.finish();
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
        buttonParams.rowSpec = GridLayout.spec(2,1,.5f);
        buttonParams.columnSpec=GridLayout.spec(0,2,2);
        button.setText(R.string.done_button);
        button.setLayoutParams(buttonParams);
        gridLayout.addView(button);
        return button;
    }
}