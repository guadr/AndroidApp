/**
 * This program is a proof of concept app for
 * an android version of the GUADR web application.
 *
 * Anyone is free to take and expand upon this code with credit
 * @author Jackson Paris
 * @version v1.0 4/26/20
 */

package com.example.guadrandroid;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    EditText _passwordText;
    EditText _sellerName;
    Button _loginButton;
    TextView _userSignupLink;
    TextView _vendorSignupLink;
    Boolean isUser = true;
    openSellerHelper _openSellerHelper;
    openUserHelper _openUserHelper;
    Boolean loginStatus;
    Boolean hasSelectedStatus = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _sellerName = (EditText) findViewById(R.id.input_seller_name) ;
        _loginButton = (Button) findViewById(R.id.btn_login);
        _userSignupLink = (TextView) findViewById(R.id.link_user_signup);
        _vendorSignupLink = (TextView) findViewById(R.id.link_seller_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        _userSignupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserSignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
        _vendorSignupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VendorSignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }


    /**
     * Handler for the user selecting the normal user or vendor radio buttons
     * @param view the radio button
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_user:
                if (checked)
                    isUser = true;
                    hasSelectedStatus = true;
                    break;
            case R.id.radio_vendor:
                if (checked)
                    hasSelectedStatus = true;
                    isUser = false;
                    break;
        }
    }

    /**
     * this function calls the validation with entered information and gives a progress dialog
     * that the app is validating the login, stores the entered information
     */
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        String email = _emailText.getText().toString();
                        String password = _passwordText.getText().toString();
                        if(isUser){
                            _openUserHelper = new openUserHelper(getApplicationContext());
                            loginStatus = _openUserHelper.validateUser(password,email);
                            if (loginStatus == true){
                                Toast.makeText(getBaseContext(), "User Login succeeded", Toast.LENGTH_LONG).show();
                                onUserLoginSuccess();
                            }
                            else{
                                Toast.makeText(getBaseContext(), "User Login failed", Toast.LENGTH_LONG).show();
                                onLoginFailed();
                            }
                        }
                        else{
                            _openSellerHelper = new openSellerHelper(getApplicationContext());
                            loginStatus = _openSellerHelper.validateSeller(password,email);
                            if (loginStatus == true){
                                onSellerLoginSuccess();
                            }
                            else{
                                onLoginFailed();
                            }
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        _emailText.setText("");
        _passwordText.setText("");
        _sellerName.setText("");
        _loginButton.setText("");
    }

    @Override
    /**
     * handles if the user presses the back button
     */
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    /**
     * starts user activity with correct login
     */
    public void onUserLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(LoginActivity.this,UserPage.class);
        startActivity(intent);
    }

    /**
     * starts seller activity with correct login
     */
    public void onSellerLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(LoginActivity.this,SellerPage.class);
        intent.putExtra("sellerName",_sellerName.getText().toString());
        startActivity(intent);
    }

    /**
     * Tells the user that login failed
     */
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    /**
     * this function validates the entered text fields to see if the user used correct input
     * @return true if correct entry data false if not
     */
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String sellerName = _sellerName.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        if (hasSelectedStatus == false){
            Toast.makeText(getBaseContext(), "Please select user or vendor", Toast.LENGTH_LONG).show();
        }

        if(!isUser && sellerName.isEmpty()){
            _passwordText.setError("enter a seller name please");
        }
        else {
            _passwordText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("please enter a password greater than 4 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
