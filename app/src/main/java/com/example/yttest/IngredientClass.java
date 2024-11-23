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
        this.quantities = roundQuantitiesToTwoDecimalPlaces(quantities);
        this.ingredients = ingredients;
    }

    // Getters


    public double[] getQuantities() {
        return quantities;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    private double[] roundQuantitiesToTwoDecimalPlaces(double[] quantities) {
        double[] roundedQuantities = new double[quantities.length];
        for (int i = 0; i < quantities.length; i++) {
            roundedQuantities[i] = Math.round(quantities[i] * 100.0) / 100.0;  // Round to 2 decimal places
        }
        return roundedQuantities;
    }


}