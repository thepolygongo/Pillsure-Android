package com.pillsure.lizhe.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.pillsure.lizhe.myapplication.utills.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editNumber;
    private EditText editVerify;
    private EditText editPillsure;
    private EditText editPassword;
    private EditText editPasswordConfirm;
    private Button buttonSend;
    private Button buttonVerify;

    private String mVerifyCode;
    private String mPhonenumber;
    private boolean verified;


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
        editPillsure = findViewById(R.id.editPillsure);

        buttonSend = findViewById(R.id.buttonSend);
        buttonVerify = findViewById(R.id.buttonVerify);

        findViewById(R.id.buttonSend).setOnClickListener(this);
        findViewById(R.id.buttonSignup).setOnClickListener(this);
        findViewById(R.id.buttonVerify).setOnClickListener(this);

        mVerifyCode = "";
        mPhonenumber = "";
        verified = false;
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

    private void clickSignup(){
        String url = Constants.serverURL + "signup";

        String username = editName.getText().toString();
        String password = editPassword.getText().toString();
        String passwordConfirm = editPasswordConfirm.getText().toString();
        String pillsure = editPillsure.getText().toString();

        if(verified == false){
            showToast("Please verify your phonenumber");
        }
        if(username.length() < 1){
            showToast("Please input a username");
        }
        else if(pillsure.length() < 1){
            showToast("please input a pillsure id");
        }
        else if(password.length() < 4){
            showToast("please input a password, length must be more than 4");
        }
        else if(!password.equals(passwordConfirm)){
            showToast("password's error");
        }
        else{
            AndroidNetworking.post(url)
                    .addBodyParameter("username", username)
                    .addBodyParameter("password", password)
                    .addBodyParameter("number", mPhonenumber)
                    .addBodyParameter("pillsure", pillsure)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            try {
                                if(response.getBoolean("success")){
                                    showToast("Signup Success");
                                    finish();
                                }
                                else{
                                    showToast(response.getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            showToast(error.toString());
                        }
                    });
        }
    }

    private void sendPhonenumber(){

        String url = Constants.serverURL + "verify";
        final String str = editNumber.getText().toString();

        if(str.length() < 5){
            showToast("Please input a valid phonenumber");
            return;
        }

        AndroidNetworking.post(url)
                .addBodyParameter("number", str)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            if(response.getBoolean("success")){
                                showToast("Sent a verify code via SMS");
                                mPhonenumber = response.getString("number");
                                mVerifyCode = response.getString("code");
                            }
                            else{
                                showToast(response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        showToast(error.toString());
                    }
                });
    }

    private void veriyPhonenumber(){
        final String str = editVerify.getText().toString();

        if(str.length() < 3){
            showToast("Please input a valid verify code");
            return;
        }

        if(str.equals(mVerifyCode)){
            showToast("phone number verified");
            verified = true;
            editNumber.setEnabled(false);
            editVerify.setEnabled(false);
            buttonVerify.setEnabled(false);
            buttonSend.setEnabled(false);
        }
        else{
            showToast("incorrect!");
        }
    }

    @Override
    public void onClick(View v) {
        int n = v.getId();
        if(n == R.id.buttonSend){
            sendPhonenumber();
        }
        else if(n == R.id.buttonVerify){
            veriyPhonenumber();
        }
        else if(n == R.id.buttonSignup){
            clickSignup();
        }
    }
    private void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
