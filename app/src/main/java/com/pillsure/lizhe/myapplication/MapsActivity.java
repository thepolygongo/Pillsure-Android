package com.pillsure.lizhe.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback , View.OnClickListener{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.buttonHistory).setOnClickListener(this);
        findViewById(R.id.buttonPolice).setOnClickListener(this);
        findViewById(R.id.buttonHistory).setOnClickListener(this);
        findViewById(R.id.buttonSchedule).setOnClickListener(this);
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
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
                Toast.makeText(this, "Sync now!", Toast.LENGTH_SHORT)
                        .show();
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
        if(v.getId() == R.id.buttonPolice){

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
