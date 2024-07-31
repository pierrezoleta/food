package com.example.yttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Foodlog extends AppCompatActivity implements FoodAdapter.FoodItemClickListener{

    Button btnScan;

    TextView calories, caloriesSmall, consumedSmall, remainingSmall;

    Float calorieAllowance;

    RecyclerView recyclerView; // the recycler view itself

    ImageView buttonPersonalize;

    ImageView buttonLogout;

    private ArrayList<FoodClass> foodItemList = new ArrayList<>(); // the arraylist for recycler view


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_log);

        buttonPersonalize = findViewById(R.id.buttonPersonalize);

        buttonPersonalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Foodlog.this, Personalization.class);
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

        caloriesSmall = findViewById(R.id.caloriesSmall);
        consumedSmall = findViewById(R.id.consumedSmall);
        remainingSmall  = findViewById(R.id.remainingSmall);




        calories = findViewById(R.id.textCaloriess);


        SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        calorieAllowance = (sharedPreferences.getFloat("caloriescompute", 0));



        calories.setText(String.valueOf(calorieAllowance));

        caloriesSmall.setText(String.valueOf(calorieAllowance));




        // setting up recycler view
        recyclerView = findViewById(R.id.recycylerViewFoodLog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // filling up recycler view


        foodItemList = loadArrayList(this);
        getCaloriesConsumed();

        FoodAdapter foodAdapter = new FoodAdapter(foodItemList, this);
        recyclerView.setAdapter(foodAdapter);



        buttonLogout = findViewById(R.id.logoutButton);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Foodlog.this);
                builder.setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                              Intent intent = new Intent(Foodlog.this, Home.class);
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

        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Foodlog.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onItemClick(int position) {
         //Remove the item from the list and update the adapter
        foodItemList.remove(position);
        recyclerView.getAdapter().notifyItemRemoved(position);

        getCaloriesConsumed();

        SharedPreferences prefs = this.getSharedPreferences("Pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(foodItemList);
        editor.putString("arraylist", json);
        editor.apply();




    }






    public static ArrayList<FoodClass> loadArrayList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Context.MODE_PRIVATE);
        String json = prefs.getString("arraylist", null);



        if (json == null) {
            return new ArrayList<>(); // Default value if the key is not present

        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<FoodClass>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void getCaloriesConsumed(){
        Float caloriesAllowed = null;
        caloriesAllowed = calorieAllowance;

        Double totalCalories = 0.0;

        for (FoodClass food : foodItemList) {
            int calories = food.getQuantity();
            totalCalories += calories;
            caloriesAllowed -= calories;

        }
        consumedSmall.setText(String.valueOf(totalCalories));
        remainingSmall.setText(String.valueOf(caloriesAllowed));
        calories.setText(String.valueOf(caloriesAllowed));

        SharedPreferences prefs = this.getSharedPreferences("Pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putFloat("caloriesRemaining", caloriesAllowed);
        editor.apply();

        if(caloriesAllowed < 0){
            calories.setTextColor(Color.rgb(255,100,100));
        }else{
            calories.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        // Optionally, you can provide a message or perform some other action
        ;
        if(false){
            super.onBackPressed();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Foodlog.this);
        builder.setMessage("Are you sure you want to exit the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Foodlog.super.onBackPressed();

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