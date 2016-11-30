package com.egco428.a23260;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private MapsDataSource datasource;

    EditText userEditTxt, passEditTxt;

    String getUsername, getPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        datasource = new MapsDataSource(this);
        datasource.read();

    }

    public void clearText(View view) {
        userEditTxt = (EditText) findViewById(R.id.username_edittext);
        passEditTxt = (EditText) findViewById(R.id.password_edittext);
        userEditTxt.setText("");
        passEditTxt.setText("");
    }

    public void toSignupActivity(View view) {
        Intent signupintent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(signupintent);
    }

    public void signIntoMain(View view) {
        userEditTxt = (EditText) findViewById(R.id.username_edittext);
        passEditTxt = (EditText) findViewById(R.id.password_edittext);
        getUsername = userEditTxt.getText().toString();
        getPassword = passEditTxt.getText().toString();


        String storedPassword = datasource.checkLogin(getUsername);
        System.out.println("Password is :" + storedPassword);
        if(getUsername.equals("") && getPassword.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter username and password",
                    Toast.LENGTH_SHORT).show();
        }

        if(storedPassword.equals(getPassword) && storedPassword != (String.valueOf(0))){
            Toast.makeText(getApplicationContext(), "Login success",
                    Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(LoginActivity.this, MainPageActivity.class);
            startActivity(mainIntent);
        }

        else{
            Toast.makeText(getApplicationContext(), "Login fail",
                    Toast.LENGTH_SHORT).show();
        }
        datasource.close();
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Close The Database
//        datasource.close();
//    }
}

