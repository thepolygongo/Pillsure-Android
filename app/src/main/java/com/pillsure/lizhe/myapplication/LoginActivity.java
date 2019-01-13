package com.pillsure.lizhe.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.buttonSignup).setOnClickListener(this);
        findViewById(R.id.buttonReset).setOnClickListener(this);
    }

    private void login(){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonLogin){
            login();
        }
        else if(v.getId() == R.id.buttonSignup){
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.buttonReset){
            Intent i = new Intent(this, ForgotActivity.class);
            startActivity(i);
        }
    }
}
