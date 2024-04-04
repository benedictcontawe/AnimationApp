package com.example.animationapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Terrain extends GameObject {
    public Bitmap bitmap;
    public Terrain(Resources resources, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.background);
        bitmap = Bitmap.createScaledBitmap(bitmap, screenX, screenY, false);
    }
}