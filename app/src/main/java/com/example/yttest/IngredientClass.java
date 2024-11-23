package com.example.yttest;

import android.graphics.Bitmap;

public class IngredientClass {
    // Instance variables
    private String dishName;
    private double[] quantities;
    private String[] ingredients;

    // Constructor
    public IngredientClass(String dishName, double[] quantities, String[] ingredients) {
        this.dishName = dishName;
        this.quantities = quantities;
        this.ingredients = ingredients;
    }

    // Getters


    public double[] getQuantities() {
        return quantities;
    }

    public String[] getIngredients() {
        return ingredients;
    }


}