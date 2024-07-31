package com.example.yttest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity {
    Button login, signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

// ez
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd");
        String currentDate = dateFormat.format(calendar.getTime());

        // Get SharedPreferences
        SharedPreferences prefs = getApplication().getSharedPreferences("Pref", Context.MODE_PRIVATE);

        // Check if the current date is different from the saved date
        String savedDate = prefs.getString("currentDay", "");
        if (!currentDate.equals(savedDate)) {
            // Save current date to SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("currentDay", currentDate);
            editor.remove("arraylist");
            editor.apply();


        }
        // ez

        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);

            }
        });

        signup = findViewById(R.id.btnSignUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Signup.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        // Optionally, you can provide a message or perform some other action
        ;
        if(false){
            super.onBackPressed();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setMessage("Are you sure you want to exit the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       Home.super.onBackPressed();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "No," do nothing
                    }
                });
        // Create and show the AlertDialog
        builder.create().show();
    }
}