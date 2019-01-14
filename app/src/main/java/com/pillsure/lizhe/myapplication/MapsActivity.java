package com.pillsure.lizhe.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pillsure.lizhe.myapplication.utills.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback , View.OnClickListener{

    private GoogleMap mMap;

    private TextView textTemperature;
    private TextView textBattery;
    private TextView textLongitude;
    private TextView textLatitude;
//    private TextView textUpcoming;
//    private TextView textLast;
    private Marker marker;

    private SharedPreferences sharedPreferences;
    private String mPillsureId;
    private String mBattery;
    private String mLatitude;
    private String mLongitude;
    private String mTemperature;
//    private String mUpcoming;
//    private String mLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        textBattery = findViewById(R.id.textBattery);
        textLongitude = findViewById(R.id.textLongitude);
        textLatitude = findViewById(R.id.textLatitude);
        textTemperature = findViewById(R.id.textTemperature);
//        textUpcoming = findViewById(R.id.textUpcoming);
//        textLast = findViewById(R.id.textLast);

        findViewById(R.id.buttonHistory).setOnClickListener(this);
        findViewById(R.id.buttonParent).setOnClickListener(this);
        findViewById(R.id.buttonHistory).setOnClickListener(this);
        findViewById(R.id.buttonSchedule).setOnClickListener(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("pillsure", MODE_PRIVATE);

        refresh();
    }

    private void setPillsureId(String str){

        String username = sharedPreferences.getString("username", "");
        String url = Constants.serverURL + "set_pillsure";

        if(str.length() < 1){
            showToast("Please input a valid phonenumber");
            return;
        }

        mPillsureId = str;
        AndroidNetworking.post(url)
                .addBodyParameter("username", username)
                .addBodyParameter("pillsure", str)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            if(response.getBoolean("success")){
                                showToast("success");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("pillsureID", mPillsureId);
                                editor.apply();
                                refresh();
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

    private void checkPillsure(){
        mPillsureId = sharedPreferences.getString("pillsureID", "");
        if(mPillsureId.length() < 1){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setTitle("Please input a pillsure ID");
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Put actions for OK button here
                    setPillsureId(input.getText().toString());
                }
            });
            alert.show();
        }
    }

    private void update_pillsure(){
        String str = String.format("Temperature: %s \u2103", mTemperature);
        textTemperature.setText(str);
        str = String.format("Battery: %s %%", mBattery);
        textBattery.setText(str);
        str = String.format("Longitude: %s", mLongitude);
        textLongitude.setText(str);
        str = String.format("Latitude: %s", mLatitude);
        textLatitude.setText(str);

        int v = Integer.parseInt(mLatitude);
        int v1 = Integer.parseInt(mLongitude);
//        LatLng toPosition = new LatLng(v, v1);
//        marker.setPosition(toPosition);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(toPosition));


        LatLng sydney = new LatLng(v, v1);
        marker.setPosition(sydney);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera( CameraUpdateFactory.zoomTo(10) );
    }

    private void refresh(){
        checkPillsure();


        String pillsure = sharedPreferences.getString("pillsureID", "");
        String url = Constants.serverURL + "get_pillsure";

        if(pillsure.length() < 1){
            showToast("Please input a valid phonenumber");
            return;
        }

        AndroidNetworking.post(url)
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
                                showToast("download data");
                                JSONObject data = response.getJSONObject("data");
                                mBattery = data.getString("battery");
                                mLatitude = data.getString("latitude");
                                mLongitude = data.getString("longitude");
                                mTemperature = data.getString("temperature");
                                update_pillsure();
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Pillsure"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );
    }

    private void gotoSettingPage(){
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
    }

    private void gotoHistoryPage(){
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    private void signout(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                refresh();
                break;
            case R.id.action_signout:
                signout();
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonParent){

        }
        else if(v.getId() == R.id.buttonHospital){

        }
        else if(v.getId() == R.id.buttonHistory){
            gotoHistoryPage();
        }
        else if(v.getId() == R.id.buttonSchedule){
            Intent i = new Intent(this, ScheduleActivity.class);
            startActivity(i);
        }
    }
}
