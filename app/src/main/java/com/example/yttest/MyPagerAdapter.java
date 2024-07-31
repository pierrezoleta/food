package com.example.yttest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.text.DecimalFormat;

public class MyPagerAdapter extends FragmentPagerAdapter {

    String recognizedFood;
    Double quantity;
    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior, String recognizedFood, Double quantity) {

        super(fm, behavior);
        this.recognizedFood = recognizedFood;
        this.quantity = quantity;
    }

    String ingredientsList[];
    String calories, protein, carbs, fats;




    @NonNull
    @Override
    public Fragment getItem(int position) {

        if(recognizedFood.equals("Adobo")){
            ingredientsList = new String[]{"Chicken", "Bay Leaves", "Soy Sauce", "White Vinegar", "Garlic",
                    "Water", "Cooking Oil", "Sugar, Salt, & Peppercorn"};
        }else if (recognizedFood.equals("Dinuguan")){
            ingredientsList = new String[]{"Pork", "Pork Blood", "Pork Cube", "Long Pepper", "Onion", "Garlic", "Water",
            "White Vinegar", "Bay Leaves", "Cooking Oil", "Sugar & Pepper"};
        }else if (recognizedFood.equals("Laing")){
            ingredientsList = new String[]{"Taro Leaves", "Coconut Milk", "Coconut Cream", "Shrimp Paste", "Pork Shoulder",
                    "Red Chili", "Onion", "Ginger", "Garlic"};
        }else if (recognizedFood.equals("Arroz Caldo")){
            ingredientsList = new String[]{
                    "Chicken", "Rice", "Water", "Fish Sauce", "Garlic", "Black Pepper", "Onion", "Eggs", "Scallions", "Ginger", "Safflower", "Chicken Cube", "Calamansi", "Cooking Oil"};
        }else if (recognizedFood.equals("Bicol Express")){
            ingredientsList = new String[]{
                    "Pork Belly", "Coconut Milk", "Coconut Cream", "Shrimp Paste", "Garlic", "Thai Chili Pepper", "Ginger", "Onion", "Serrano Pepper", "Water"};
        }else if (recognizedFood.equals("Champorado")){
            ingredientsList = new String[]{
                    "Glutinous Rice", "Tablea Chocolate", "Sugar", "Evaporated Milk", "Water"};
        }else if (recognizedFood.equals("Sinigang")){
            ingredientsList = new String[]{
                    "Pork Belly", "Tamarind", "Water Spinach", "String Beans", "Eggplants", "Daikon Radish", "Okra", "Tomato", "Long Green Pepper", "Onion", "Water", "Fish Sauce", "Black Pepper"};
        }else if (recognizedFood.equals("Kare Kare")){
            ingredientsList = new String[]{
                    "Oxtail", "Banana Flower Bud", "Pechay", "String Bean", "Eggplant", "Peanut", "Peanut Butter", "Shrimp Paste", "Water", "Annatto Seed", "Toasted Ground Rice", "Garlic", "Onion", "Salt", "Pepper"};
        }else if (recognizedFood.equals("Pinakbet")){
            ingredientsList = new String[]{
                    "Lechon Kawali", "Knorr Shrimp Cube", "Sitaw", "Kalabasa", "Okra", "Eggplant", "Ampalaya", "Kamote", "Tomato", "Ginger", "Onion", "Garlic", "Bagoong Alamang", "Water", "Cooking Oil", "Black Pepper"};
        }
        else if (recognizedFood.equals("Tinola")){
            ingredientsList = new String[]{
                    "Chicken", "Malunggay Leaves", "Pepper Leaves", "Ground Black Pepper", "Unripe Papaya", "Water", "Knorr Chicken Cube", "Onion", "Garlic", "Ginger", "Fish Sauce", "Vegetable Oil"};
        }else{
            ingredientsList = new String[]{"Error w/ name"};
        }







        switch (position) {
            case 0:
                return new NutritionFragment(recognizedFood);
            case 1:
                return new IngredientFragment(ingredientsList);
            // Add more cases for additional fragments
            default:
                return null;
        }
    }



    public int getCount() {
        return 2; // Change this to the number of tabs
    }



}
