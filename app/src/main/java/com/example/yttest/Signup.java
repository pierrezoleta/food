package com.example.yttest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    CheckBox privacyPolicyCheckbox;
    TextView linkPrivacyPolicy;
    Button register;
    TextInputEditText username, password,email;

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        register = findViewById(R.id.btnRegister);

        privacyPolicyCheckbox = findViewById(R.id.checkBox);
        linkPrivacyPolicy = findViewById(R.id.linkPrivacyPolicy);
        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        email=findViewById(R.id.textEmail);

        back = findViewById(R.id.backSignup);


        linkPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrivacyPolicyDialog();
            }
        });

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
                Boolean validCheckBox = true;
                Boolean validEmail= true;



                if(!(username.getText().toString().length() >= 4 && username.getText().toString().length() <= 20)){
                    validUsername = false;
                    username.setError("Username must be between 4-20 characters");
                }
                if(!(password.getText().toString().length() >= 4 && password.getText().toString().length() <= 20)){
                    validPassword = false;
                    password.setError("Password must be between 4-20 characters");
                }
                if (!(privacyPolicyCheckbox.isChecked())){
                    Toast.makeText(Signup.this, "Please agree to the Privacy Policy to continue with the signup process.", Toast.LENGTH_SHORT).show();

                }
//                if(!(matcher2.matches() || matcher.matches())){
//                    validEmail=false;
//                    email.setError("Email must end with @yahoo.com or @gmail.com");
//                }

                if(validUsername && validPassword&&validEmail){
                    Intent intent = new Intent(Signup.this, Personalization.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.putString("email", email.getText().toString());
                    editor.apply();

                    // Reset dialog display date when a new user registers

                    resetDialogForNewUser();


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

    private void resetDialogForNewUser() {
        SharedPreferences prefs = getSharedPreferences("DialogPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("lastShownDate");  // Clear the stored date, so the dialog will be shown again
        editor.apply();
    }

    private void showPrivacyPolicyDialog() {
        String privacyPolicyContent = getString(R.string.privacy_policy_content);  // You can store the policy in strings.xml

        // Create a ScrollView for long content
        TextView textView = new TextView(this);
        textView.setText(privacyPolicyContent);
        textView.setPadding(30, 50, 30, 50);
        textView.setTextSize(16);
        textView.setTextColor(Color.parseColor("#FF8595BF"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            textView.setLineHeight(75);
        }

        textView.setMovementMethod(new ScrollingMovementMethod());  // Enable scrolling

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(textView);

        // Create the AlertDialog with the scrollable TextView
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Privacy Policy")
                .setView(scrollView)  // Set the ScrollView as the content view
                .setCancelable(true)
                .setPositiveButton("I have finished reading", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0F1422")));
    }
}