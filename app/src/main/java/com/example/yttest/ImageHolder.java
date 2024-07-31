package com.example.yttest;

import android.graphics.Bitmap;

public class ImageHolder {
    private Bitmap myImage;
    // Getter/setter
    private static ImageHolder instance;
    public static ImageHolder getInstance() {
        if (instance == null)
            instance = new ImageHolder();
        return instance;
    }
    private ImageHolder() {
        // Initialize the bitmap here

    }

    public Bitmap getBitmap() {
        return myImage;
    }

    // Setter method for the bitmap
    public void setBitmap(Bitmap myImage) {
        this.myImage = myImage;
    }
}