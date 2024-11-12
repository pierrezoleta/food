package com.example.yttest;

import android.graphics.Bitmap;

public class FoodClass {
    private String name, date, time;
    private int quantity;
//    private Bitmap image;
    public FoodClass(String name, int quantity, String date, String time){
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.time = time;

//        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

//    public void setImage(Bitmap image) {this.image = image;}
//    public Bitmap getImage() { // Add a getter for the image
//        return image;
//    }
}
