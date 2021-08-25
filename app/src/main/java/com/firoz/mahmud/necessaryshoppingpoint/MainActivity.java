package com.firoz.mahmud.necessaryshoppingpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase fb=FirebaseDatabase.getInstance();
        try {
            fb.setPersistenceCacheSizeBytes(1024 * 1024 * 100);
            fb.setPersistenceEnabled(true);
        }catch (Exception e){}



        findViewById(R.id.codespare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://codesphear.com/")));
            }
        });


        //starting activity
        Thread th=new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(4000);
                }catch (Exception e){}
                try {
                    if (getSharedPreferences(KeyF.sharedprefarence.databasename, MODE_PRIVATE).getBoolean(KeyF.sharedprefarence.isbuyingkey, false)) {
                        startActivity(new Intent(MainActivity.this, UserDetails.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, Home.class));
                    }
                }catch (Exception e){}

            }
        };
        th.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}