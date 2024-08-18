package com.example.yttest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Profile extends AppCompatActivity {

    TextView weightText, heightText, usernameText, emailText, profileLetter;
    LinearLayout buttonHome;
    ConstraintLayout buttonLogout, buttonPersonalize;
    FloatingActionButton btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        weightText = findViewById(R.id.textWeight);
        heightText = findViewById(R.id.textHeight);
        usernameText = findViewById(R.id.textUsername);
        emailText = findViewById(R.id.textEmail);
        profileLetter = findViewById(R.id.profileLetter);

        SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        SharedPreferences sharedMyPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        float weight = sharedPreferences.getFloat("weight", 0);
        float height = sharedPreferences.getFloat("height", 0);
        String username = sharedMyPrefs.getString("username","N/A");
        String capitalizedUsername = capitalizeFirstLetter(username);
        String firstLetter = getFirstLetter(username);
        String email = sharedMyPrefs.getString("email","N/A");


        weightText.setText(String.valueOf(weight));
        heightText.setText(String.valueOf(height));
        usernameText.setText(capitalizedUsername);
        profileLetter.setText(firstLetter);
        emailText.setText(email);


        buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to ProfileActivity
                Intent intent = new Intent(Profile.this, Foodlog.class);
                startActivity(intent);
                overridePendingTransition(1, 1);
            }
        });

        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonLogout = findViewById(R.id.logoutButton);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Profile.this, Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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
        });

        buttonPersonalize = findViewById(R.id.buttonPersonalize);

        buttonPersonalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Personalization.class);
                startActivity(intent);
                SharedPreferences sharedPreferencess = getSharedPreferences("Pref", Context.MODE_PRIVATE);

                // Create an editor to modify SharedPreferences
                SharedPreferences.Editor editorr = sharedPreferencess.edit();

                // Put the food name into SharedPreferences
                editorr.putBoolean("backable", true);

                // Apply the changes
                editorr.apply();
            }
        });
    }

    public String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public String getFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return input.substring(0, 1).toUpperCase(); // Capitalize the first letter
    }


}