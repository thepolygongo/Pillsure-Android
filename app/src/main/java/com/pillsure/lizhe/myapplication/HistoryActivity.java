package com.pillsure.lizhe.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements MyAdapter.ItemClickListener{

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;

    MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("2018-Jan-34 3h34m");
        animalNames.add("2018-Jan-34 3h34m");
        animalNames.add("2018-Jan-34 3h34m");
        animalNames.add("2018-Jan-34 3h34m");
        animalNames.add("2018-Jan-34 3h34m");

        ArrayList<String> animalNames2 = new ArrayList<>();
        animalNames2.add("Delivered");
        animalNames2.add("NOT");
        animalNames2.add("Delivered");
        animalNames2.add("Delivered");
        animalNames2.add("Delivered");

        mRecyclerView = findViewById(R.id.my_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this, animalNames, animalNames2);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
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
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + mAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
