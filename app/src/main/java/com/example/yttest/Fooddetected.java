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
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Fooddetected extends AppCompatActivity {

    Button backButton;
    ImageView moreinfo;
    String textResult;

    Button logFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detected);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        editor.putInt("quantityhehe", 1);

        editor.apply();

// Array for Adobo
        double[] adoboQuantities = {
                2.0 / 4,    // Chicken
                3.0 / 4,    // Dried bay leaves
                4.0 / 4,    // Soy sauce
                6.0 / 4,    // White vinegar
                5.0 / 4,    // Garlic
                1.5 / 4,    // Water
                3.0 / 4,    // Cooking oil
                1.0 / 4,    // Sugar
                0.25 / 4,   // Salt
                1.0 / 4     // Whole peppercorn
        };
        String[] adoboIngredients = {
                "lbs Chicken",
                "pieces Dried bay leaves",
                "tablespoons Soy sauce",
                "tablespoons White vinegar",
                "cloves Garlic",
                "cups Water",
                "tablespoons Cooking oil",
                "teaspoon Sugar",
                "teaspoon Salt",
                "teaspoon Whole peppercorn"
        };
        IngredientClass adobo = new IngredientClass("Adobo", adoboQuantities, adoboIngredients);


// Array for Arroz Caldo
        double[] arrozCaldoQuantities = {
                1.5 / 6,    // Chicken
                1.5 / 6,    // Rice
                34.0 / 6,   // Water
                2.0 / 6,    // Fish sauce
                1.0 / 6,    // Garlic
                0.25 / 6,   // Ground black pepper
                1.0 / 6,    // Onion
                4.0 / 6,    // Eggs
                1.0 / 6,    // Scallions
                2.0 / 6,    // Ginger
                3.0 / 6,    // Safflower
                1.0 / 6,    // Chicken cube
                1.0 / 6,    // Lemon
                2.0 / 6     // Cooking oil
        };


// Array of ingredient names with measurements
        String[] arrozCaldoIngredients = {
                "lbs Chicken",
                "cups Rice",
                "ounces Water ",
                "Tablespoons Fish sauce",
                "teaspoon Garlic",
                "teaspoon Ground black pepper",
                "cup Onion, minced",
                "eggs Hard boiled",
                "cup Scallions (green onions)",
                "knobs Ginger",
                "Tablespoons Safflower (kasubha)",
                "Chicken cube Bouillon",
                "Lemon or pieces Calamansi",
                "Tablespoons Cooking oil"
        };
        IngredientClass arroz = new IngredientClass("Arroz Caldo", arrozCaldoQuantities, arrozCaldoIngredients );


// Array for Bicol Express
        double[] bicolExpressQuantities = {
                2.0 / 6,    // Pork belly
                2.0 / 6,    // Coconut milk
                2.0 / 6,    // Coconut cream
                0.25 / 6,   // Shrimp paste or salted krill
                5.0 / 6,    // Garlic
                5.0 / 6,    // Thai chili pepper
                2.0 / 6,    // Ginger
                1.0 / 6,    // Onion
                2.0 / 6,    // Serrano pepper
                1.0 / 6,    // Water
                8.0 / 6     // Maggi Magic Sarap
        };


// Array of ingredient names with measurements
        String[] bicolExpressIngredients = {
                "lbs Pork belly",
                "cups Coconut milk",
                "cups Coconut cream",
                "cups Shrimp paste or salted Krill",
                "cloves Garlic",
                "pieces Thai chili pepper",
                "thumbs Ginger",
                "piece Onion",
                "pieces Serrano pepper",
                "cup Water",
                "grams Maggi Magic Sarap"
        };
        IngredientClass bicol = new IngredientClass("Bicol Express", bicolExpressQuantities, bicolExpressIngredients );


// Array for champorado


        double[] champoradoQuantities = {
                5.0 / 6,    // Water
                1.0 / 6,    // Glutinous rice
                4.0 / 6,    // Tablea chocolate
                0.5 / 6,    // Sugar
                0.5 / 6     // Evaporated milk
        };


// Array of ingredient names with measurements
        String[] champoradoIngredients = {
                "cups Water",
                "cup Glutinous rice",
                "pieces Tablea chocolate",
                "cup Sugar",
                "cup Evaporated milk"
        };
        IngredientClass champorado = new IngredientClass("Champorado", champoradoQuantities, champoradoIngredients );


// Array for Dinuguan


        double[] dinuguanQuantities = {
                1.0 / 6,    // Pork shoulder
                20.0 / 6,   // Pork blood
                0.5 / 6,    // Pork ears
                1.0 / 6,    // White vinegar
                3.0 / 6,    // Beef broth
                3.0 / 6,    // Dried bay leaves
                1.0 / 6,    // Lemongrass
                3.0 / 6,    // Long green peppers
                1.0 / 6,    // Onion
                5.0 / 6,    // Garlic
                2.0 / 6,    // Sugar
                3.0 / 6     // Cooking oil
        };


// Array of ingredient names with measurements
        String[] dinuguanIngredients = {
                "lb Pork shoulder",
                "oz Pork blood",
                "lb Pork ears",
                "cup White vinegar",
                "cups Beef broth",
                "Dried bay leaves",
                "bunch Lemongrass",
                "Long green peppers",
                "Onion",
                "cloves Garlic",
                "teaspoons Sugar",
                "Tablespoons Cooking oil",
                "Salt and ground black pepper to taste"
        };


        IngredientClass dinuguan = new IngredientClass("Dinuguan", dinuguanQuantities, dinuguanIngredients );


// Array for karekare


        double[] karekareQuantities = {
                3.0 / 6,    // Oxtail
                1.0 / 6,    // Banana flower bud
                1.0 / 6,    // Pechay or bok choy
                1.0 / 6,    // String beans
                4.0 / 6,    // Eggplants
                1.0 / 6,    // Ground peanuts
                0.5 / 6,    // Peanut butter
                0.5 / 6,    // Shrimp paste
                34.0 / 6,   // Water
                0.5 / 6,    // Annatto seeds soaked in water
                0.5 / 6,    // Toasted ground rice
                1.0 / 6,    // Garlic
                1.0 / 6     // Onion
        };


// Array of ingredient names with measurements
        String[] karekareIngredients = {
                "lbs Oxtail",
                "piece Small banana flower bud, sliced",
                "bundle Pechay or bok choy",
                "bundle String beans",
                "pieces Eggplants",
                "cup Ground peanuts",
                "cup Peanut butter",
                "cup Shrimp paste",
                "ounces Water",
                "cup Annatto seeds",
                "cup Toasted ground rice",
                "tablespoon Garlic",
                "piece Onion",
                "Salt and pepper to taste"
        };


        IngredientClass karekare = new IngredientClass("Kare-kare", karekareQuantities, karekareIngredients );


//  Array for Laing


        double[] laingQuantities = {
                3.5 / 8,    // Taro leaves
                6.0 / 8,    // Coconut milk
                2.0 / 8,    // Coconut cream
                0.5 / 8,    // Shrimp paste
                0.5 / 8,    // Pork shoulder
                7.0 / 8,    // Red chilies
                1.0 / 8,    // Onion
                0.5 / 8,    // Ginger
                8.0 / 8     // Garlic
        };


// Array of ingredient names with measurements
        String[] laingIngredients = {
                "oz Taro leaves, dried",
                "cups Coconut milk",
                "cups Coconut cream",
                "cup Shrimp paste (bagoong or balaw)",
                "lb Pork shoulder",
                "pieces Red chilies",
                "piece Onion",
                "cup Ginger",
                "cloves Garlic"
        };
        IngredientClass laing = new IngredientClass("Laing", laingQuantities, laingIngredients );


// Pinakbet arrays
        double[] pinakbetQuantities = {
                1.0 / 4,    // Lechon kawali
                1.0 / 4,    // Knorr shrimp cube
                12.0 / 4,   // Sitaw
                0.5 / 4,    // Kalabasa
                12.0 / 4,   // Okra
                1.0 / 4,    // Chinese eggplant
                1.0 / 4,    // Ampalaya
                1.0 / 4,    // Kamote
                2.0 / 4,    // Tomato
                2.0 / 4,    // Ginger
                1.0 / 4,    // Onion
                4.0 / 4,    // Garlic
                2.0 / 4,    // Bagoong alamang
                2.5 / 4,    // Water
                3.0 / 4,    // Cooking oil
                0.25 / 4    // Ground black pepper
        };


// Array of ingredient names with measurements
        String[] pinakbetIngredients = {
                "lb Lechon kawali",
                "piece Knorr Shrimp Cube",
                "pieces Sitaw",
                "piece Kalabasa",
                "pieces Okra",
                "piece Chinese eggplant",
                "piece Ampalaya",
                "piece Kamote",
                "pieces Tomato",
                "thumbs Ginger",
                "piece Onion",
                "cloves Garlic",
                "teaspoons Bagoong alamang",
                "cups Water",
                "tablespoons Cooking oil",
                "teaspoon Ground black pepper"
        };
        IngredientClass pinakbet = new IngredientClass("Pinakbet", pinakbetQuantities, pinakbetIngredients );


// array for sinigang


        double[] sinigangQuantities = {
                2.0 / 4,    // Pork belly
                1.0 / 4,    // Young tamarind
                1.0 / 4,    // Water spinach
                8.0 / 4,    // String beans
                2.0 / 4,    // Eggplants
                1.0 / 4,    // Daikon radish
                8.0 / 4,    // Okras
                2.0 / 4,    // Tomatoes
                2.0 / 4,    // Long green pepper
                1.0 / 4,    // Onion
                2.0 / 4,    // Water (quarts)
        };


// Array of ingredient names with measurements
        String[] sinigangIngredients = {
                "lbs Pork belly",
                "lb Young tamarind",
                "bunch Water spinach",
                "pieces String beans",
                "pieces Eggplants",
                "piece Daikon radish",
                "pieces Okra",
                "pieces Tomatoes",
                "pieces Long green pepper",
                "piece Onion",
                "quarts Water",
                "Fish sauce and ground black pepper to taste"
        };
        IngredientClass sinigang = new IngredientClass("Sinigang", sinigangQuantities, sinigangIngredients );


// tinola array


        double[] tinolaQuantities = {
                2.0 / 5,    // Chicken
                1.0 / 5,    // Malunggay leaves
                1.0 / 5,    // Hot pepper leaves
                1.0 / 40,   // Ground black pepper (1/8 divided by 5)
                1.0 / 5,    // Unripe papaya
                6.0 / 5,    // Water
                1.0 / 5,    // Knorr chicken cube
                1.0 / 5,    // Onion
                4.0 / 5,    // Garlic
                3.0 / 5,    // Ginger
                2.0 / 5,    // Fish sauce
                3.0 / 5     // Vegetable oil
        };


// Array of ingredient names with measurements
        String[] tinolaIngredients = {
                "lbs Chicken",
                "cup Malunggay leaves",
                "cup Hot pepper leaves",
                "teaspoon Ground black pepper",
                "piece Unripe papaya",
                "cups Water",
                "piece Knorr chicken cube",
                "piece Onion",
                "cloves Garlic",
                "thumbs Ginger",
                "tablespoons Fish sauce (patis)",
                "tablespoons Vegetable oil"
        };
        IngredientClass tinola = new IngredientClass("Tinola", tinolaQuantities, tinolaIngredients );

        moreinfo = findViewById(R.id.moreInfo);

        moreinfo.setOnClickListener(view -> {
            if(textResult.equals("Dinuguan")){
                showPopup(dinuguan);
            } else if (textResult.equals("Tinola")) {
                showPopup(tinola);
            }
            else if (textResult.equals("Adobo")) {
                showPopup(adobo);
            }else if (textResult.equals("Arroz Caldo")) {
                showPopup(arroz);
            }else if (textResult.equals("Bicol Express")) {
                showPopup(bicol);
            }else if (textResult.equals("Champorado")) {
                showPopup(champorado);
            }else if (textResult.equals("Kare Kare")) {
                showPopup(karekare);
            }else if (textResult.equals("Laing")) {
                showPopup(laing);
            }else if (textResult.equals("Pinakbet")) {
                showPopup(pinakbet);
            }else if (textResult.equals("Sinigang")) {
                showPopup(sinigang);
            }
            else{
                showPopup(tinola);
            }

        });


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

    private void showPopup(IngredientClass ingredient) {

        SharedPreferences prefs = getApplication().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        int quantity = prefs.getInt("quantityhehe",1);

//        SharedPreferences prefsprefs = getApplication().getSharedPreferences("Pref", Context.MODE_PRIVATE);
//        int foodQuantity = prefsprefs.getInt("caloriesToLog", 999);

        // Inflate the moreinfo.xml layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.moreinfo, null);

        // Get the TextView from moreinfo.xml
        TextView ingredientsTextView = popupView.findViewById(R.id.moreinfotext);

        // Build the string to display in the TextView
        StringBuilder ingredientsText = new StringBuilder();
        double[] quantities = ingredient.getQuantities();
        String[] ingredientsArray = ingredient.getIngredients();

        DecimalFormat df = new DecimalFormat("#.00");

       ingredientsText.append(quantity).append("Servings of this food includes").append("\n");
        for (int i = 0; i < quantities.length; i++) {
            ingredientsText.append(df.format(quantities[i]*quantity)).append(" ").append(ingredientsArray[i]).append("\n");
        }
        ingredientsText.append("Source: Panlasang Pinoy").append("\n");

        // Set the TextView's text
        ingredientsTextView.setText(ingredientsText.toString());

        // Create an AlertDialog to show the popup
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView)
                .setCancelable(true)
                .setPositiveButton("Close", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }



}