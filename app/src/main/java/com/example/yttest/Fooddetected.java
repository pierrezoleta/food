package com.example.yttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.processing.SurfaceProcessorNode;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Fooddetected extends AppCompatActivity {

    Button backButton;
    String textResult;

    Button logFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detected);


        logFood = findViewById(R.id.btnLogFood);
        backButton = findViewById(R.id.btnBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        logFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Gson gson = new Gson();

                SharedPreferences prefs = getApplication().getSharedPreferences("Pref", Context.MODE_PRIVATE);
                ArrayList<FoodClass> fl = new ArrayList<>();
                String defaultJs = gson.toJson(fl);
                String json = prefs.getString("arraylist", defaultJs);

                int calorieToBeLogged = prefs.getInt("caloriesToLog", 999);
                Float calorieAllowance = (prefs.getFloat("caloriesRemaining", 0));

//                if(calorieAllowance < calorieToBeLogged){
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Fooddetected.this);
//                    builder.setMessage("Adding this food item is over your calorie budget. Are you sure you want to log?")
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    Type type = new TypeToken<ArrayList<FoodClass>>() {}.getType();
//                                    ArrayList<FoodClass> foodList =  gson.fromJson(json, type);
//
//
//                                    String foodName = prefs.getString("foodToLog", "AWTS ERROR");
//                                    int foodQuantity = prefs.getInt("caloriesToLog", 999);
//
//                                    Calendar calendar = Calendar.getInstance();
//                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());
//
//                                    // Format the date and time
//                                    String dateTime = dateFormat.format(calendar.getTime());
//
//                                    foodList.add(new FoodClass(foodName, foodQuantity, dateTime, dateTime));
//
//
//                                    SharedPreferences.Editor editor = prefs.edit();
//
//
//                                    String json1 = gson.toJson(foodList);
//                                    editor.putString("arraylist", json1);
//                                    editor.apply();
//
//
//                                    Intent intent = new Intent(Fooddetected.this, Foodlog.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // User clicked "No," do nothing
//                                }
//                            });
//                    // Create and show the AlertDialog
//                    builder.create().show();


                if (calorieAllowance < calorieToBeLogged) {
                    // Create custom dialog
                    Dialog dialog = new Dialog(Fooddetected.this);
                    dialog.setContentView(R.layout.exceed_calorie_dialog);
                    dialog.setCancelable(false); // Prevent dismissing on outside touch

                    // Get dialog elements
                    TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                    Button positiveButton = dialog.findViewById(R.id.dialog_positive);
                    Button negativeButton = dialog.findViewById(R.id.dialog_negative);

                    // Set up click listeners
                    positiveButton.setOnClickListener(v -> {
                        Type type = new TypeToken<ArrayList<FoodClass>>() {}.getType();
                        ArrayList<FoodClass> foodList = gson.fromJson(json, type);

                        String foodName = prefs.getString("foodToLog", "AWTS ERROR");
                        int foodQuantity = prefs.getInt("caloriesToLog", 999);

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());
                        String dateTime = dateFormat.format(calendar.getTime());

                        foodList.add(new FoodClass(foodName, foodQuantity, dateTime, dateTime));

                        SharedPreferences.Editor editor = prefs.edit();
                        String json1 = gson.toJson(foodList);
                        editor.putString("arraylist", json1);
                        editor.apply();

                        Intent intent = new Intent(Fooddetected.this, Foodlog.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        dialog.dismiss(); // Close the dialog
                    });

                    negativeButton.setOnClickListener(v -> {
                        // Just dismiss the dialog
                        dialog.dismiss();
                    });

                    // Show the dialog
                    int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.show();


                }else{
                    Type type = new TypeToken<ArrayList<FoodClass>>() {}.getType();
                    ArrayList<FoodClass> foodList =  gson.fromJson(json, type);


                    String foodName = prefs.getString("foodToLog", "AWTS ERROR");
                    int foodQuantity = prefs.getInt("caloriesToLog", 999);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());

                    // Format the date and time
                    String dateTime = dateFormat.format(calendar.getTime());


                    foodList.add(new FoodClass(foodName, foodQuantity, dateTime, dateTime));





                    SharedPreferences.Editor editor = prefs.edit();


                    String json1 = gson.toJson(foodList);
                    editor.putString("arraylist", json1);
                    editor.apply();

                    Intent intent = new Intent(Fooddetected.this, Foodlog.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }




            }
        });

        Intent intent = getIntent();


        // CHANGES THE NAME OF THE FOOD IN THE FOOD DETECTED CLASS {It looks like ...}
        if (intent.hasExtra("textResult")) {

            textResult = intent.getStringExtra("textResult");


            TextView textView = findViewById(R.id.textView28);
            textView.setText(textResult);

            // Check if the text is "Uncertain" and disable the button
            if ("Uncertain".equals(textResult)) {
                logFood.setEnabled(false);  // Disable the button
                logFood.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.button_background_color));
                logFood.setTextColor(ContextCompat.getColorStateList(this, R.color.button_text_color));

            } else {
                logFood.setEnabled(true);   // Enable the button
                logFood.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
                logFood.setTextColor(ContextCompat.getColorStateList(this, R.color.button_text_color));

            }
        }

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), MyPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, textResult, 0.5);
        viewPager.setAdapter(adapter);



        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Nutritional Content");
        tabLayout.getTabAt(1).setText("Ingredients");





        if (intent.hasExtra("imageResult")) {


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888; // or Bitmap.Config.RGB_565
            options.inSampleSize = 1; // Set to a higher value for downsampling

            byte[] byteArray = intent.getByteArrayExtra("imageResult");
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);

            ImageHolder singleton = ImageHolder.getInstance();
            Bitmap testtest = singleton.getBitmap();


            ImageView img = findViewById(R.id.imageView10);

            img.setImageBitmap(testtest);
        }



    }



}