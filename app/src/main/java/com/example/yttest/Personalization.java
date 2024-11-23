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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class Personalization extends AppCompatActivity {

    Button continuenext;

    RadioGroup rg;

    String gender;

    int age;

    float height, weight;

    TextInputEditText agee, heightt, weightt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalization);



        continuenext = findViewById(R.id.btnContinue);
        rg = findViewById(R.id.radioGroup);
        agee = findViewById(R.id.editTextAge);
        heightt = findViewById(R.id.editTextHeight);
        weightt = findViewById(R.id.editTextWeight);

       gender = "Male";


        rg.check(rg.getChildAt(0).getId());

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                gender = radioButton.getText().toString();
            }
        });


        continuenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean validAge, validHeight, validWeight;
                validAge = false;
                validHeight = false;
                validWeight = false;

                try{
                    age = Integer.parseInt(agee.getText().toString());
                    if(age >= 18 && age <= 100){
                        validAge = true;
                    }else{
                        validAge = false;
                        agee.setError("Please enter a number from 18 - 100");
                    }
                }catch (NumberFormatException e){
                    agee.setError("Please enter a number from 18 - 100");
                }

                try{
                    height  = Float.parseFloat(heightt.getText().toString());
                    if(height >= 140 && age <= 220){
                        validHeight = true;
                    }else{
                        validHeight = false;
                        heightt.setError("Please enter a number from 140 - 220");
                    }
                }catch (NumberFormatException e){
                    heightt.setError("Please enter a number from 140 - 220");
                }

                try{
                    weight  = Float.parseFloat(weightt.getText().toString());
                    if(weight >= 30 && weight <= 300){
                        validWeight = true;
                    }else{
                        validWeight = false;
                        weightt.setError("Please enter a number from 30 - 300");
                    }
                }catch (NumberFormatException e) {
                    weightt.setError("Please enter a number from 30 - 300");
                }


                if(validWeight && validAge && validHeight){
                    age = Integer.parseInt(agee.getText().toString());
                    height  = Float.parseFloat(heightt.getText().toString());
                    weight  = Float.parseFloat(weightt.getText().toString());
                    Intent intent = new Intent(Personalization.this, Foodlog.class);




                    SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("gender", gender);
                    editor.putInt("age", age);
                    editor.putFloat("height", height);
                    editor.putFloat("weight", weight);

                    Float caloryCompute = 0f;

                    if(gender.equals("Male")){
                        caloryCompute = 66.4730f + 13.751f * weight + 5.0033f * height - 6.7550f * age;
                    } else if (gender.equals("Female")) {
                        caloryCompute = 655.0955f + 9.5643f * weight + 1.8496f * height - 4.6756f * age;
                    }

                    int roundedCalories = Math.round(caloryCompute);

                    //Float caloryCompute   = (13 * weight) +700;
//                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
//                    String roundedValue = decimalFormat.format(caloryCompute);


                    editor.putFloat("caloriescompute", roundedCalories);


                    editor.apply();

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }

            }
        });

    }
    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        // Optionally, you can provide a message or perform some other action
        ;
        SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("backable", false)){
            super.onBackPressed();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(Personalization.this);
            builder.setMessage("You must finish your personalization.")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    })

                    ;
            // Create and show the AlertDialog
            builder.create().show();
        }
    }
}