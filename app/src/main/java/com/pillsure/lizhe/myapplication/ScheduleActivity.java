package com.pillsure.lizhe.myapplication;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {
    Button editText1;
    Button editText2;
    Button editText3;
    Button editText4;
    Button editText5;
    Button buttonClear;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText1 = findViewById(R.id.editTime1);
        editText2 = findViewById(R.id.editTime2);
        editText3 = findViewById(R.id.editTime3);
        editText4 = findViewById(R.id.editTime4);
        editText5 = findViewById(R.id.editTime5);
        buttonSave = findViewById(R.id.buttonSave);
        buttonClear = findViewById(R.id.buttonClear);

        editText1.setOnClickListener(this);
        editText2.setOnClickListener(this);
        editText3.setOnClickListener(this);
        editText4.setOnClickListener(this);
        editText5.setOnClickListener(this);

        buttonClear.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
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

    private void showToast(String s){
        Toast.makeText(ScheduleActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        showToast("click");
        if(id == R.id.buttonClear){

        }
        else if(id == R.id.buttonSave){

        }
        else if(id == R.id.editTime4 || id == R.id.editTime5 || id == R.id.editTime1 || id == R.id.editTime2 || id == R.id.editTime3){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    if(id == R.id.editTime1){
                        editText1.setText(selectedHour + ":" + selectedMinute);
                    }
                    else if(id == R.id.editTime2){
                        editText2.setText(selectedHour + ":" + selectedMinute);
                    }
                    else if(id == R.id.editTime3){
                        editText3.setText(selectedHour + ":" + selectedMinute);
                    }
                    else if(id == R.id.editTime4){
                        editText4.setText(selectedHour + ":" + selectedMinute);
                    }
                    else if(id == R.id.editTime5){
                        editText5.setText(selectedHour + ":" + selectedMinute);
                    }
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
    }
}
