package com.example.yttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;


public class Foodlog extends AppCompatActivity implements FoodAdapter.FoodItemClickListener {
    private static final String DIALOG_PREFS = "DialogPrefs";
    Button testButton;
//    private KonfettiView konfettiView = null;
//    private Shape.DrawableShape drawableShape = null;

    LinearLayout buttonProfile;
    CircularProgressIndicator progressBar;
    FloatingActionButton btnScan;
    TextView calories, caloriesSmall, consumedSmall, remainingSmall, KcalText;

    Float calorieAllowance;

    RecyclerView recyclerView; // the recycler view itself

    ConstraintLayout buttonPersonalize;

    ImageView emptyView;

    private ArrayList<FoodClass> foodItemList = new ArrayList<>(); // the arraylist for recycler view


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_log);

        progressBar = findViewById(R.id.calorie_progress_bar);
        buttonPersonalize = findViewById(R.id.buttonPersonalize);
        KcalText = findViewById(R.id.txtCalorieRemaining);
        TextView dateTextView = findViewById(R.id.dateTextView);
        buttonProfile = findViewById(R.id.buttonProfile);
        emptyView = findViewById(R.id.imageNoFood);


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

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to ProfileActivity
                Intent intent = new Intent(Foodlog.this, Profile.class);
                startActivity(intent);
                overridePendingTransition(1, 1);

            }
        });


        caloriesSmall = findViewById(R.id.caloriesSmall);
        consumedSmall = findViewById(R.id.consumedSmall);
        remainingSmall = findViewById(R.id.remainingSmall);


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


        // hide show recycler view

        if (foodAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
        }

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
        Type type = new TypeToken<ArrayList<FoodClass>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void getCaloriesConsumed() {
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


//        // Confetti
//        final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.party);
//        if (drawable == null) {
//            Log.e("KonfettiTest", "Drawable is null");
//        }
//        drawableShape = new Shape.DrawableShape(drawable, true, true);
//        konfettiView = findViewById(R.id.konfettiView);


        int progress;
        if (caloriesAllowed <=0) {

//            explode();
            progressBar.setIndicatorColor(ContextCompat.getColor(this, R.color.progress_bar_exceeded));
            progress = 100;
            KcalText.setTextColor(ContextCompat.getColor(this, R.color.progress_bar_exceeded));



            checkIfDialogShouldBeShown();

        // Create custom dialog
            Dialog dialog = new Dialog(Foodlog.this);
            dialog.setContentView(R.layout.reach_calorie_dialog);
            dialog.setCancelable(false); // Prevent dismissing on outside touch

            Button negativeButton = dialog.findViewById(R.id.dialog_negative);
            negativeButton.setOnClickListener(v -> dialog.dismiss());



//        } else if (caloriesAllowed <= 0) {
//
//            explode();
//            progressBar.setIndicatorColor(ContextCompat.getColor(this, R.color.progress_bar_completed));
//            progress = 100;
//            KcalText.setTextColor(ContextCompat.getColor(this, R.color.progress_bar_completed));


        } else {
            // Calculate progress based on calories consumed
            float caloriesConsumed = calorieAllowance - caloriesAllowed;
            progress = (int) ((caloriesConsumed / calorieAllowance) * 100);
            progress = Math.max(0, Math.min(progress, 100)); // Ensure progress is within 0-100 range
            progressBar.setIndicatorColor(ContextCompat.getColor(this, R.color.progress_bar_default));
            KcalText.setTextColor(ContextCompat.getColor(this, R.color.progress_bar_default));
        }

        if (progressBar.getProgress() != progress) {
            animateProgressBar(progress);
        }
    }

    private void animateProgressBar(int toProgress) {
        if (progressBar == null) {
            Log.e("Foodlog", "progressBar is null in animateProgressBar!");
            return;
        }

        // Ensure toProgress is within valid range
        toProgress = Math.max(0, Math.min(toProgress, 100));

        // Get the current progress
        int currentProgress = progressBar.getProgress();

        // Animate progress bar to the new progress value
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, toProgress);
        progressAnimator.setDuration(1000); // Animation duration in milliseconds
        progressAnimator.setInterpolator(new DecelerateInterpolator());
        progressAnimator.start();
    }


    // The method that triggers the confetti explosion
    public void explode(KonfettiView konfettiView) {
        Log.d("KonfettiTest", "Confetti explosion triggered");

        EmitterConfig emitterConfig = new Emitter(110L, TimeUnit.MILLISECONDS).max(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .spread(360)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 30f)
                        .position(new Position.Relative(0.5, 0.3))
                        .build()
        );
    }

    private void showDialog() {
        // Assuming you are calling the dialog as before
            // Create custom dialog
            Dialog dialog = new Dialog(Foodlog.this);
            dialog.setContentView(R.layout.reach_calorie_dialog);
            dialog.setCancelable(true); // Prevent dismissing on outside touch


        ConstraintLayout reminder = dialog.findViewById(R.id.reminder);

        reminder.setVisibility(View.GONE);

        // Find the negative button to dismiss the dialog
        Button negativeButton1 = dialog.findViewById(R.id.dialog_negative);
        negativeButton1.setOnClickListener(v -> {
            dialog.dismiss();
        });


        // Show the dialog
            int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.90);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.show();

        startPopAnimationForTextView(dialog);

            // Trigger confetti if KonfettiView is found
            KonfettiView konfettiView = dialog.findViewById(R.id.konfettiView);
            if (konfettiView != null) {
                konfettiView.setZ(1000);  // Ensure the Konfetti view is above other views
                konfettiView.bringToFront();
                konfettiView.post(() -> explode(konfettiView));  // Call explode method for confetti
            } else {
                Log.e("KonfettiTest", "KonfettiView is not found in the dialog layout.");
            }
        }

    private void startPopAnimationForTextView(Dialog dialog) {
        // Ensure the reminder is visible before starting the animation
        dialog.setContentView(R.layout.reach_calorie_dialog);
        ConstraintLayout reminder = dialog.findViewById(R.id.reminder);


        reminder.setVisibility(View.VISIBLE);

        // Create animations for the pop effect (scale, alpha, and rotation)
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(reminder, "scaleX", 0f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(reminder, "scaleY", 0f, 1.2f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(reminder, "alpha", 0f, 1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(reminder, "rotation", -15f, 0f);

        // Group all animations together
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alpha, rotation);
        animatorSet.setDuration(1500);  // Duration for the pop effect (400ms)

        // Start the animation
        animatorSet.start();
    }

    private void checkIfDialogShouldBeShown() {
        SharedPreferences prefs = getSharedPreferences(DIALOG_PREFS, MODE_PRIVATE);
        String lastShownDate = prefs.getString("lastShownDate", "");

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (lastShownDate.isEmpty() || !lastShownDate.equals(currentDate)) {
            // Show the dialog if it's the first time today or the dialog hasn't been shown today
            showDialog();

            // Store the current date to prevent showing the dialog again today
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("lastShownDate", currentDate);
            editor.apply();
        }
    }

//    private boolean isNextDay(Calendar lastDate, Calendar currentDate) {
//        lastDate.add(Calendar.DATE, 1);
//        return lastDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
//                lastDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR);
//    }
//
//    private boolean isSameDay(Calendar lastDate, Calendar currentDate) {
//        return lastDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
//                lastDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR);
//    }

    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        // Optionally, you can provide a message or perform some other action
        ;
        if (false) {
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
