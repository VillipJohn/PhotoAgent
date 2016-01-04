package com.example.villip.photoagent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_NEW_ADD = 1;
    private static final String TAG = "MainActivity";

    private ListView list;
    private ArrayList<Ad> items;
    private AddsListAdapter adapter;
    private MySQLiteHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new MySQLiteHelper(this);

        items = db.getAllAds();
        adapter = new AddsListAdapter(this, items);

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();

                Intent i = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(i, REQUEST_CODE_NEW_ADD);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED)
            if (requestCode == REQUEST_CODE_NEW_ADD && resultCode == RESULT_OK) {
                String place = data.getStringExtra("place");
                String date = data.getStringExtra("date");
                String time = data.getStringExtra("time");
                Bitmap bmp = null;
                try {
                    bmp = data.getParcelableExtra("image");
                } catch (NullPointerException e) {
                    Log.e(TAG, "bytearray is null", e);
                }
                Ad ad = new Ad(place, time, date, bmp);
                db.addAdvertising(ad);
                items.add(ad);

                adapter.notifyDataSetChanged();
            }
    }
}
