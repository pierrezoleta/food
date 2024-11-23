package com.example.yttest;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.Html;


import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;


public class NutritionFragment extends Fragment implements QuantityFragment.QuantityDialogListener{

    String calories, protein, carbs, fats, recognizedFood;
    TextView serving, textCalories, textCarbs, textProtein, textFats;

    public NutritionFragment(String recognizedFood){
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.recognizedFood = recognizedFood;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);
         textCalories = view.findViewById(R.id.textCalories);
         textCarbs = view.findViewById(R.id.textCarbs);
         textProtein = view.findViewById(R.id.textProtein);
         textFats = view.findViewById(R.id.textFats);

        calories = "null";
        protein = "null";
        carbs = "null";
        fats = "null";

        serving = view.findViewById(R.id.servingSize);
        // Get the string resource with HTML formatting
        String formattedText = getString(R.string.serving_size_text);
        // Set the text with HTML formatting
        String combinedText = formattedText + recognizedFood;
        serving.setText(Html.fromHtml(combinedText));

        int quantity = Integer.parseInt(String.valueOf(serving.getText().toString().charAt(0)));



        DecimalFormat decimalFormat = new DecimalFormat("0");
        switch (recognizedFood) {
            case "Adobo":

                calories = decimalFormat.format(Math.ceil(607.0 * quantity));
                protein = decimalFormat.format(Math.ceil(44.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(4.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(44.0 * quantity))+ "g";
                break;
            case "Dinuguan":
                calories = decimalFormat.format(Math.ceil(424 * quantity));
                protein = decimalFormat.format(Math.ceil(30.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(7.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(4.0 * quantity))+ "g";
                break;
            case "Laing":
                calories = decimalFormat.format(Math.ceil(618.0 * quantity));
                protein = decimalFormat.format(Math.ceil(18.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(12.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(59.0 * quantity))+ "g";
                break;
            case "Arroz Caldo":
                calories = decimalFormat.format(Math.ceil(585.0 * quantity));
                protein = decimalFormat.format(Math.ceil(29.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(43.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(32.0 * quantity))+ "g";
                break;
            case "Bicol Express":
                calories = decimalFormat.format(Math.ceil(1240.0 * quantity));
                protein = decimalFormat.format(Math.ceil(27.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(10.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(124.0 * quantity))+ "g";
                break;
            case "Champorado":
                calories = decimalFormat.format(Math.ceil(210.0 * quantity));
                protein = decimalFormat.format(Math.ceil(2.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(44.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(2.0 * quantity))+ "g";
                break;
            case "Kare Kare":
                calories = decimalFormat.format(Math.ceil(934.0 * quantity));
                protein = decimalFormat.format(Math.ceil(91.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(24.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(53.0 * quantity))+ "g";
                break;
            case "Pinakbet":
                calories = decimalFormat.format(Math.ceil(1243.0 * quantity));
                protein = decimalFormat.format(Math.ceil(5.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(28.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(124.0 * quantity))+ "g";
                break;
            case "Sinigang":
                calories = decimalFormat.format(Math.ceil(1538.0 * quantity));
                protein = decimalFormat.format(Math.ceil(29.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(91.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(121.0 * quantity))+ "g";
                break;
            case "Tinola":
                calories = decimalFormat.format(Math.ceil(530.0 * quantity));
                protein = decimalFormat.format(Math.ceil(38.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(10.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(36.0 * quantity))+ "g";
                break;


            // Add more cases for additional fragments
            default:
                calories = "0";
                protein = "0g";
                carbs = "0g";
                fats = "0g";
        }


        // Create or get the SharedPreferences object
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Pref", getContext().MODE_PRIVATE);

        // Get the SharedPreferences editor to write data
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Put the string value in the SharedPreferences
        editor.putString("foodName", recognizedFood);



        // Apply the changes asynchronously


        // Commit the changes
        editor.apply();

        textCalories.setText(calories);
        textCarbs.setText(carbs);
        textProtein.setText(protein);
        textFats.setText(fats);





        // Put the string value in the SharedPreferences
        editor.putString("foodToLog", recognizedFood);

        editor.putInt("caloriesToLog", Integer.parseInt(calories));


        // Commit the changes
        editor.apply();



        serving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showQuantityDialog();

            }
        });

        return view;


        //return inflater.inflate(R.layout.fragment_nutrition, container, false);



    }

    private void showQuantityDialog() {
        QuantityFragment dialogFragment = new QuantityFragment();
        dialogFragment.setQuantityDialogListener(this);
        dialogFragment.show(requireFragmentManager(), "quantity_dialog");
    }

    @Override
    public void onQuantityConfirmed(String quantityString) {
        // Update your TextView with the confirmed quantity
        Double quantity;
        // serving.setText(quantityString + " Serving of " + recognizedFood);

        // Creating the SpannableString
        String fullText = quantityString + " - Serving of " + recognizedFood;
        SpannableString spannableString = new SpannableString(fullText);

        // Setting the color for the quantityString portion
        int start = 0;
        int end = quantityString.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0099FF")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Setting the text to the TextView
        serving.setText(spannableString);

        //quantity = Integer.parseInt(String.valueOf(serving.getText().toString().charAt(0)));
        quantity = Double.parseDouble(String.valueOf(quantityString));



        DecimalFormat decimalFormat = new DecimalFormat("0");
        switch (recognizedFood) {
            case "Adobo":

                calories = decimalFormat.format(Math.ceil(607.0 * quantity));
                protein = decimalFormat.format(Math.ceil(44.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(4.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(44.0 * quantity))+ "g";
                break;
            case "Dinuguan":
                calories = decimalFormat.format(Math.ceil(424 * quantity));
                protein = decimalFormat.format(Math.ceil(30.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(7.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(4.0 * quantity))+ "g";
                break;
            case "Laing":
                calories = decimalFormat.format(Math.ceil(618.0 * quantity));
                protein = decimalFormat.format(Math.ceil(18.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(12.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(59.0 * quantity))+ "g";
                break;
            case "Arroz Caldo":
                calories = decimalFormat.format(Math.ceil(585.0 * quantity));
                protein = decimalFormat.format(Math.ceil(29.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(43.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(32.0 * quantity))+ "g";
                break;
            case "Bicol Express":
                calories = decimalFormat.format(Math.ceil(1240.0 * quantity));
                protein = decimalFormat.format(Math.ceil(27.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(10.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(124.0 * quantity))+ "g";
                break;
            case "Champorado":
                calories = decimalFormat.format(Math.ceil(210.0 * quantity));
                protein = decimalFormat.format(Math.ceil(2.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(44.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(2.0 * quantity))+ "g";
                break;
            case "Kare Kare":
                calories = decimalFormat.format(Math.ceil(934.0 * quantity));
                protein = decimalFormat.format(Math.ceil(91.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(24.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(53.0 * quantity))+ "g";
                break;
            case "Pinakbet":
                calories = decimalFormat.format(Math.ceil(1243.0 * quantity));
                protein = decimalFormat.format(Math.ceil(5.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(28.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(124.0 * quantity))+ "g";
                break;
            case "Sinigang":
                calories = decimalFormat.format(Math.ceil(1538.0 * quantity));
                protein = decimalFormat.format(Math.ceil(29.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(91.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(121.0 * quantity))+ "g";
                break;
            case "Tinola":
                calories = decimalFormat.format(Math.ceil(530.0 * quantity));
                protein = decimalFormat.format(Math.ceil(38.0 * quantity))+ "g";
                carbs = decimalFormat.format(Math.ceil(10.0 * quantity))+ "g";
                fats = decimalFormat.format(Math.ceil(36.0 * quantity))+ "g";
                break;


            // Add more cases for additional fragments
            default:
                calories = "0g";
                protein = "0g";
                carbs = "0g";
                fats = "0g";
        }

        textCalories.setText(calories);
        textCarbs.setText(carbs);
        textProtein.setText(protein);
        textFats.setText(fats);


        // Create or get the SharedPreferences object
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Pref", getContext().MODE_PRIVATE);

        // Get the SharedPreferences editor to write data
                SharedPreferences.Editor editor = sharedPreferences.edit();

        // Put the string value in the SharedPreferences
                editor.putString("foodName", recognizedFood);

        // Commit the changes
                editor.apply();

        // Put the string value in the SharedPreferences
        editor.putString("foodToLog", recognizedFood);
        editor.putInt("caloriesToLog", Integer.parseInt(calories));

        // Commit the changes
        editor.apply();

    }
}