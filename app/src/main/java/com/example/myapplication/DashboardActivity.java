package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.util.Constants;

public class DashboardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView imageMenu;
    TextView textUsername, textEmail;
    LinearLayout layoutLogin, layoutLogout, layout_about, layout_books;
    View viewLogin, viewLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        imageMenu = findViewById(R.id.image_menu);

        viewLogin = findViewById(R.id.view_login);
        viewLogout = findViewById(R.id.view_logout);
        layoutLogin = findViewById(R.id.layout_login);
        layoutLogout = findViewById(R.id.layout_logout);
        layout_about = findViewById(R.id.layout_about);
        layout_books = findViewById(R.id.layout_books);

        textUsername = findViewById(R.id.text_username);
        textEmail = findViewById(R.id.text_email);


        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(Constants.KEY_ISE_LOGGED_IN, false);

        if(!isLoggedIn) {
            textUsername.setText(R.string.welcome_guest);
            textUsername.setVisibility(View.VISIBLE);
            layoutLogin.setVisibility(View.VISIBLE);
            viewLogin.setVisibility(View.VISIBLE);
            layoutLogout.setVisibility(View.GONE);
            viewLogout.setVisibility(View.GONE);
        } else {
            textUsername.setText(preferences.getString(Constants.KEY_USERNAME, "N/A"));
            textEmail.setText(preferences.getString(Constants.KEY_EMAIL, "N/A"));
            textUsername.setVisibility(View.VISIBLE);
            textEmail.setVisibility(View.VISIBLE);
            layoutLogin.setVisibility(View.GONE);
            viewLogin.setVisibility(View.GONE);
            layoutLogout.setVisibility(View.VISIBLE);
            viewLogout.setVisibility(View.VISIBLE);

        }

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        findViewById(R.id.card_books).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
            }
        });
        findViewById(R.id.card_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
        findViewById(R.id.card_basket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            }
        });
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Do you want to logout?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Toast.makeText(DashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });

        layoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        layout_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));

            }
        });

        layout_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
            }
        });
    }
}