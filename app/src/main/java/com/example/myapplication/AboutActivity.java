package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chinodev.androidneomorphframelayout.NeomorphFrameLayout;

public class AboutActivity extends AppCompatActivity {

    ImageView imageBack;
    NeomorphFrameLayout bhargav, sahil, kishan, anil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        imageBack = findViewById(R.id.image_back);
        imageBack.setOnClickListener(v -> onBackPressed());

        bhargav = findViewById(R.id.bhargav);

        bhargav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AboutBhargav.class));
            }
        });

        sahil = findViewById(R.id.sahil);

        sahil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AboutSahil.class));
            }
        });


        kishan = findViewById(R.id.kishan);

        kishan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AboutKishan.class));
            }
        });

        anil = findViewById(R.id.anil);
        anil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AboutAnil.class));
            }
        });
    }
}