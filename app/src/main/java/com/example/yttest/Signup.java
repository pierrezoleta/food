package com.example.yttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    Button register;
    TextInputEditText username, password,email;

    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        register = findViewById(R.id.btnRegister);

        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        email=findViewById(R.id.textEmail);

        back = findViewById(R.id.backSignup);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
                String yahooRegex = "^[a-zA-Z0-9._%+-]+@yahoo\\.com$";
                Pattern pattern = Pattern.compile(gmailRegex);
                Matcher matcher = pattern.matcher(email.getText().toString());

                Pattern pattern2 = Pattern.compile(yahooRegex);
                Matcher matcher2 = pattern2.matcher(email.getText().toString());

                Boolean validUsername = true;
                Boolean validPassword = true;
                Boolean validEmail= true;
                if(!(username.getText().toString().length() >= 4 && username.getText().toString().length() <= 20)){
                    validUsername = false;
                    username.setError("Username must be between 4-20 characters");
                }
                if(!(password.getText().toString().length() >= 4 && password.getText().toString().length() <= 20)){
                    validPassword = false;
                    password.setError("Password must be between 4-20 characters");
                }
                if(!(matcher2.matches() || matcher.matches())){
                    validEmail=false;
                    email.setError("Email must end with @yahoo.com or @gmail.com");
                }

                if(validUsername && validPassword&&validEmail){
                    Intent intent = new Intent(Signup.this, Personalization.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.apply();


                    SharedPreferences sharedPreferencess = getSharedPreferences("Pref", Context.MODE_PRIVATE);

                    // Create an editor to modify SharedPreferences
                    SharedPreferences.Editor editorr = sharedPreferencess.edit();

                    // Put the food name into SharedPreferences
                    editorr.putBoolean("backable", false);

                    // Apply the changes
                    editorr.apply();




                    editorr.remove("arraylist");
                    editorr.apply();

                    startActivity(intent);


                }

            }
        });

    }
}