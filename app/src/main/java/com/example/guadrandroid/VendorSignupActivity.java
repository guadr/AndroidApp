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
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * this activity allows vendors to sign up
 */
public class VendorSignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    EditText _confirmPasswordText;
    EditText _descriptionText;
    Button _signupButton;
    TextView _loginLink;
    openSellerHelper _openSellerHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_signup);
        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.button_submit);
        _descriptionText = (EditText) findViewById(R.id.input_description) ;
        _loginLink = (TextView) findViewById(R.id.link_login);
        _confirmPasswordText= (EditText) findViewById(R.id.input_confirm_password);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//goes to user signup
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//goes to login page
                finish();
            }
        });

    }

    /**
     * This function calls the validation and contains a message to inform the user that signup is
     * in progress with a progress bar
     */
    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(VendorSignupActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        new android.os.Handler().postDelayed(

                new Runnable() {
                    public void run() {
                        String sellerName = _nameText.getText().toString();
                        String sellerDescription = _descriptionText.getText().toString();
                        String sellerEmail = _emailText.getText().toString();
                        String sellerPassword = _passwordText.getText().toString();
                        Seller currentSeller = new Seller(sellerEmail,sellerPassword,sellerName,sellerDescription);

                        _openSellerHelper = new openSellerHelper(getApplicationContext());
                        _openSellerHelper.insertSeller(currentSeller);// inserting current seller into database
                        onSignupSuccess();
                        //onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    /**
     * Finishes the signup activity and returns to login page
     */
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        Intent intent = new Intent(VendorSignupActivity.this, LoginActivity.class);
        setResult(RESULT_OK, intent);
        VendorSignupActivity.this.finish();
    }

    /**
     * Tells the user that signup failed
     */
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    @Override
    /**
     * handling if user presses the android back button
     */
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        VendorSignupActivity.this.finish();
    }

    /**
     * this function validates the entered text fields to see if the user used correct input
     * @return true if correct entry data false if not
     */
    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String description = _descriptionText.getText().toString();
        String confirmPassword = _confirmPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (description.isEmpty()) {
            _nameText.setError("please enter a description");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if(!password.equals(confirmPassword)){
            _confirmPasswordText.setError("passwords do not match");
        }
        else {
            _confirmPasswordText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("greater than four characters please");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }
}

