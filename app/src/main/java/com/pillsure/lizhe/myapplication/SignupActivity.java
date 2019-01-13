package com.pillsure.lizhe.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editNumber;
    private EditText editVerify;
    private EditText editPassword;
    private EditText editPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.editName);
        editNumber = findViewById(R.id.editPhone);
        editVerify = findViewById(R.id.editVerify);
        editPassword = findViewById(R.id.editPassword);
        editPasswordConfirm = findViewById(R.id.editPasswordConfirm);

        findViewById(R.id.buttonSend).setOnClickListener(this);
        findViewById(R.id.buttonSignup).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int n = v.getId();
        if(n == R.id.buttonSend){

        }
        else if(n == R.id.buttonSignup){

        }
    }
}
