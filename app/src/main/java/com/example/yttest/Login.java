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


public class Login extends AppCompatActivity {
    Button loginAccount;
    TextView back;
    TextInputEditText email;
    EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        email = findViewById(R.id.textEmailLogin);
        pass = findViewById(R.id.editTextTextPassword);
        back = findViewById(R.id.backLogin);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });




        loginAccount = findViewById(R.id.btnLoginAccount);
        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Foodlog.class);

                if(username.equals(email.getText().toString()) && password.equals(pass.getText().toString())){
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else{
                    email.setError("Incorrect email or password");
                    pass.setError("Incorrect email or password");
                }


            }
        });

    }
}