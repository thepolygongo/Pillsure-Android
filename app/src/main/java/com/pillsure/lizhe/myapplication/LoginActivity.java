package com.pillsure.lizhe.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editPassword;

    private SharedPreferences sharedPreferences;
    private String mUsername;
    private String mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.buttonSignup).setOnClickListener(this);
        findViewById(R.id.buttonReset).setOnClickListener(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("pillsure", MODE_PRIVATE);
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = sharedPreferences.getString("username", "");
        mPassword = sharedPreferences.getString("password", "");
        editName.setText(mUsername);
        editPassword.setText(mPassword);
    }

    private void loginSuccess(){
        sharedPreferences.edit().putString("login", "ok");
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
        finish();
    }

    private void clickLogin(){
        String url = Constants.serverURL + "login";

        AndroidNetworking.post(url)
                .addBodyParameter("username", mUsername)
                .addBodyParameter("password", mPassword)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            if(response.getBoolean("success")){
                                showToast("Login Success");
                                loginSuccess();
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

    private void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonLogin){

            mUsername = editName.getText().toString();
            mPassword = editPassword.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", mUsername);
            editor.putString("password", mPassword);
            editor.apply();
            editor.commit(); // commit changes
            clickLogin();
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
