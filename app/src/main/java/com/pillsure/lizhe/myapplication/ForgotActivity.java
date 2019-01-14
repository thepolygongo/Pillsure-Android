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

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSend;
    private Button buttonVerify;

    private EditText editNumber;
    private EditText editVerify;
    private EditText editPassword;
    private EditText editPasswordConfirm;

    private String mVerifyCode;
    private String mPhonenumber;
    private boolean verified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editNumber = findViewById(R.id.editPhone);
        editVerify = findViewById(R.id.editVerify);
        editPassword = findViewById(R.id.editPassword);
        editPasswordConfirm = findViewById(R.id.editPasswordConfirm);

        buttonSend = findViewById(R.id.buttonSend);
        buttonVerify = findViewById(R.id.buttonVerify);

        buttonVerify.setOnClickListener(this);
        buttonSend.setOnClickListener(this);
        findViewById(R.id.buttonReset).setOnClickListener(this);
    }

    private void reset(){
        String url = Constants.serverURL + "reset_password";

        String password = editPassword.getText().toString();
        String passwordConfirm = editPasswordConfirm.getText().toString();

        if(verified == false){
            showToast("Please verify your phonenumber");
        }
        else if(password.length() < 4){
            showToast("please input a password, length must be more than 4");
        }
        else if(!password.equals(passwordConfirm)){
            showToast("password's error");
        }
        else{
            AndroidNetworking.post(url)
                    .addBodyParameter("password", password)
                    .addBodyParameter("number", mPhonenumber)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            try {
                                if(response.getBoolean("success")){
                                    showToast("Reset Success");
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

        String url = Constants.serverURL + "verifyreset";
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
                                String str = response.getString("username");
                                showToast("Username: " + str);
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
        if(n == R.id.buttonVerify){
            veriyPhonenumber();
        }
        else if(n == R.id.buttonSend){
            sendPhonenumber();
        }
        else if(n == R.id.buttonReset){
            reset();
        }
    }

    private void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
